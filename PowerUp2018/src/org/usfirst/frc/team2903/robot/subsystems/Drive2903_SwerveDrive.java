package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive2903_SwerveDrive extends Subsystem 
{
	public WPI_TalonSRX leftFrontDrive;
	public WPI_TalonSRX leftFrontSwerve;
	public WPI_TalonSRX leftRearDrive;
	public WPI_TalonSRX leftRearSwerve;
	public WPI_TalonSRX rightFrontDrive;
	public WPI_TalonSRX rightFrontSwerve;
	public WPI_TalonSRX rightRearDrive;
	public WPI_TalonSRX rightRearSwerve;
	public SpeedControllerGroup driveLeft;
	public SpeedControllerGroup driveRight;
	public DifferentialDrive robotDrive;
	
	static double maxOutput = 1.0;

	public Drive2903_SwerveDrive()
	{
		// instantiate the Talons (EIGHT MOTORS WHAT!?)
		leftFrontDrive = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		leftFrontSwerve = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		leftRearDrive = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		leftRearSwerve = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		rightFrontDrive = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		rightFrontSwerve = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		rightRearDrive = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		rightRearSwerve = new WPI_TalonSRX(RobotMap.PLACEHOLDER);
		
		// configure encoders
		leftFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftFrontSwerve.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftRearDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftRearSwerve.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightFrontSwerve.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightRearDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightRearSwerve.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
		// configure the output
		leftFrontDrive.configPeakOutputForward(maxOutput, 0);
		leftFrontSwerve.configPeakOutputForward(maxOutput, 0);
		leftRearDrive.configPeakOutputForward(maxOutput, 0);
		leftRearSwerve.configPeakOutputForward(maxOutput, 0);
		rightFrontDrive.configPeakOutputForward(maxOutput, 0);
		rightFrontSwerve.configPeakOutputForward(maxOutput, 0);
		rightRearDrive.configPeakOutputForward(maxOutput, 0);
		rightRearSwerve.configPeakOutputForward(maxOutput, 0);
		
		leftFrontDrive.configPeakOutputReverse(-maxOutput, 0);
		leftFrontSwerve.configPeakOutputReverse(-maxOutput, 0);
		leftRearDrive.configPeakOutputReverse(-maxOutput, 0);
		leftRearSwerve.configPeakOutputReverse(-maxOutput, 0);
		rightFrontDrive.configPeakOutputReverse(-maxOutput, 0);
		rightFrontSwerve.configPeakOutputReverse(-maxOutput, 0);
		rightRearDrive.configPeakOutputReverse(-maxOutput, 0);
		rightRearSwerve.configPeakOutputReverse(-maxOutput, 0);
		
		//rightFrontDrive.configPeakCurrentLimit(amps, timeoutMs)
		//if we want to limit current, we can use this example
		
		driveLeft = new SpeedControllerGroup(leftFrontDrive, leftRearDrive);
		driveRight = new SpeedControllerGroup(rightFrontDrive, rightRearDrive);
		
		robotDrive = new DifferentialDrive(driveLeft, driveRight);
		
		//all drive motors follow leftFrontDrive + rightFrontDrive, and all swerve motors follow LeftFrontSwerve
		//all PLACEHOLDER's should be replaced with leftFrontDrive and LeftFrontSwerve
//		Robot.driveSwerveSubsystem.leftFrontSwerve.set(ControlMode.Position,2);
//		Robot.driveSwerveSubsystem.leftRearDrive.set(ControlMode.Follower,RobotMap.PLACEHOLDER);
//		Robot.driveSwerveSubsystem.leftRearSwerve.set(ControlMode.Follower,RobotMap.PLACEHOLDER);
//		Robot.driveSwerveSubsystem.rightFrontSwerve.set(ControlMode.Follower,RobotMap.PLACEHOLDER);
//		Robot.driveSwerveSubsystem.rightRearDrive.set(ControlMode.Follower,RobotMap.PLACEHOLDER);
//		Robot.driveSwerveSubsystem.rightRearSwerve.set(ControlMode.Follower,RobotMap.PLACEHOLDER);
	}

	public void arcadeDrive(double forward, double side, double turn) 
	{
		robotDrive.arcadeDrive(forward, -turn);
		double rotation = Math.toDegrees(Math.atan2(forward,side))+90-Robot.gyroSubsystem.gyroPosition();
		leftFrontSwerve.set(ControlMode.Position, rotation);
	}

	@Override
	protected void initDefaultCommand() 
	{
	}

	public void setTeleopMode() 
	{
//		Robot.driveSwerveSubsystem.leftFrontDrive.set(ControlMode.PercentOutput,0);
//		Robot.driveSwerveSubsystem.rightFrontDrive.set(ControlMode.PercentOutput,1);
	}
	
	public void setAutoMode()
	{
//		Robot.driveSwerveSubsystem.leftFrontDrive.set(ControlMode.Position,0);
//		Robot.driveSwerveSubsystem.rightFrontDrive.set(ControlMode.Position,1);
	}

	public void driveReset()
	{
	}

}