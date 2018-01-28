package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleOp extends Command {

	public boolean isTANK = false;
	public boolean isTogglePressed = false;
	
    public TeleOp() {
    	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.driveSubsystem.setTeleopMode();
    	Robot.gyroSubsystem.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
//    	if (Robot.driveJoy.getRawButton(9) || Robot.driveJoyExtra.getRawButton(9)) {
//    		if(isTogglePressed) return;
//			Robot.driveSubsystem.changeToHighGear();
//			isTogglePressed = true;
//    	} 
//    	else {
//    		isTogglePressed = false;
//    	}
    	
//    	if(isTANK) {
//    		double leftSpeed = Robot.driveJoy.getY();
//    		double rightSpeed = Robot.driveJoyExtra.getY();
//    		Robot.driveSubsystem.tankDrive(leftSpeed, rightSpeed);
//    	} 
//    	else {
        	double forward = Robot.driveJoy.getY();
        	double turn = Robot.driveJoy.getX();
        	
        	//double leftSide = Robot.opJoy.getY();
        	//double joyForward = Robot.opJoy.getRawAxis(5);
        	double joyForward = Robot.opJoy.getRawAxis(2) - Robot.opJoy.getRawAxis(3);
        	double joyTurn = Robot.opJoy.getRawAxis(0);
        	if (joyForward > 0) joyTurn = -joyTurn;
        	
        	SmartDashboard.putNumber("forward", -forward);
        	SmartDashboard.putNumber("turn", turn);
        	SmartDashboard.putNumber("Gyro", Robot.gyroSubsystem.gyroPosition());
        	Robot.driveSubsystem.arcadeDrive(joyForward, joyTurn);
        	//SmartDashboard.putNumber("left", leftSide);
        	//SmartDashboard.putNumber("right", rightSide);
        	//Robot.driveSubsystem.tankDrive(leftSide, rightSide);
        	
        	if (Robot.opJoy.getRawButton(5)) {
        		Robot.armSubsystem.throwCube();
        	} 
        	else if (Robot.opJoy.getRawButton(6)) {
        		Robot.armSubsystem.grabCube();
        	} 
        	else {
        		Robot.armSubsystem.stopArms();
        	}
    //	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//no
    }
}
