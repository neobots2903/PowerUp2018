package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

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
        	
        	SmartDashboard.putNumber("forward", forward);
        	SmartDashboard.putNumber("turn", turn);
        	Robot.driveSubsystem.arcadeDrive(forward, turn);
        	
        	if (Robot.driveJoy.getRawButton(7)) {
        		Robot.armSubsystem.throwCube();
        	} 
        	else if (Robot.driveJoy.getRawButton(8)) {
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
