package org.usfirst.frc.team2903.robot.commoners;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.VisionPipeline2903;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class SwitchAim extends Command {

	private double centerX = 0.0;
	private double width = 0.0;
	public static final double maxWidth = 60.0;	//how close the robot is allowed to get to the target
	public static final int error = 5;	//error if we are not driving towards the target; just aiming
	public static final int maxError = 60;	//maximum allowed error before the robot is forced to re-aim
	public static final int errorMulti = 18;	//the smaller this number is, the more strict it is about aiming
	public static final double maxSpeed = 0.55;
	public static final double minSpeed = 0.44;
	public static final double minForwardSpeed = 0.5;
	public static final double maxForwardSpeed = 0.8;

	private Object imgLock = new Object();
	private VisionThread visionThread;
	private boolean drive;
	private NetworkTableInstance table;
	private NetworkTable datatable;

	public SwitchAim(boolean driveTowards) {
		requires(Robot.driveSubsystem);
		drive = driveTowards;
	}

	@Override
	protected void initialize() {
		/*
		UsbCamera camera = Robot.camera;//CameraServer.getInstance().addAxisCamera("10.29.3.56");
		
		camera.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);
	
		visionThread = new VisionThread(camera, new VisionPipeline2903(), pipeline -> {
				if (!pipeline.filterContoursOutput().isEmpty()) {
					Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
					synchronized (imgLock) {
						centerX = r.x + (r.width / 2);
						width = r.width;
					}
				}
		});
		visionThread.start();
		*/
		table = NetworkTableInstance.getDefault();
	    // Set your team number here
	    table.setServerTeam(2903);
	    datatable = table.getTable("datatable");
	}

	
	public double getCenterX() {
		double localCenterX;
		synchronized (imgLock) {
			localCenterX = this.centerX;
		}
		return localCenterX;
	}
	
	public double getWidth() {
		double localWidth;
		synchronized (imgLock) {
			localWidth = this.width;
		}
		return localWidth;
	}
	

	@Override
	protected void execute() {
		
		/*
		double localCenterX = getCenterX();
		double localWidth = getWidth();
		SmartDashboard.putNumber("CenterX ", localCenterX);
		SmartDashboard.putNumber("Width ", localWidth);
		*/
		
		SmartDashboard.putNumber("CenterX ", datatable.getEntry("centerX").getValue().getDouble());
		SmartDashboard.putNumber("Width ", datatable.getEntry("width").getValue().getDouble());
	}

	@Override
	protected boolean isFinished() {
		double localCenterX = datatable.getEntry("centerX").getValue().getDouble();
		double localWidth = datatable.getEntry("width").getValue().getDouble();
		double distanceFromCenter = (localCenterX - (Robot.IMG_WIDTH / 2));
		double turn = distanceFromCenter / (Robot.IMG_WIDTH / 2);
		if (Math.abs(turn) > maxSpeed) {
			if (turn < 0)
				turn = -maxSpeed;
			else 
				turn = maxSpeed;
		} else if (Math.abs(turn) < minSpeed) {
			if (turn < 0)
				turn = -minSpeed;
			else 
				turn = minSpeed;
		}
			
		SmartDashboard.putNumber("turn (for vision) ", turn);
		SmartDashboard.putNumber("Distance from center ", distanceFromCenter);
		if (drive) {
			double calcError = maxWidth*errorMulti/localWidth;
			if (calcError > maxError) calcError = maxError;
			SmartDashboard.putNumber("calcError ", calcError);
			
			if (Math.abs(distanceFromCenter) > calcError) {
				Robot.driveSubsystem.arcadeDrive(0, turn);
				return false;
			} else {
				if (localWidth >= maxWidth) {
					return true;
				} else {
					double forwardSpeed = maxWidth / localWidth;
					if (forwardSpeed < minForwardSpeed) { forwardSpeed = minForwardSpeed; }
					else if (forwardSpeed > maxForwardSpeed) { forwardSpeed = maxForwardSpeed; }
					SmartDashboard.putNumber("forwardSpeed ", forwardSpeed);
					Robot.driveSubsystem.arcadeDrive(-forwardSpeed, turn);
					return false;
				}
			}
		} else {
			if (Math.abs(distanceFromCenter) > error) {
				Robot.driveSubsystem.arcadeDrive(0, turn);
				return false;
			} else {	
				return true;
			}
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
