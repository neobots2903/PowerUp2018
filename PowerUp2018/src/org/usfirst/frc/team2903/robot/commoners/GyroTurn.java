package org.usfirst.frc.team2903.robot.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurn extends Command {

	double goal;
	double startAngle;
	double targetAngle;
	double currentAngle;
	double percentComplete;
	double minSpeed = 0.45;
	double error = 0.5;
	double speedFactor = 1.0;
	
    public GyroTurn(double degrees) {
    	requires(Robot.driveSubsystem);
    	requires(Robot.gyroSubsystem);
    	
    	goal = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gyroSubsystem.reset();
    	startAngle = Robot.gyroSubsystem.gyroPosition();
    	targetAngle = startAngle + goal;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	currentAngle = Robot.gyroSubsystem.gyroPosition();
		percentComplete = Math.abs((targetAngle-currentAngle)/goal)*speedFactor;
		
		if (percentComplete < minSpeed) 
		{
			percentComplete = minSpeed;
		}

		
		if (targetAngle - error > currentAngle)
		{
			Robot.driveSubsystem.arcadeDrive(0, percentComplete);
		}
		else if (targetAngle + error < currentAngle)
		{
			Robot.driveSubsystem.arcadeDrive(0, -percentComplete);
		}
		else 
		{
			Robot.driveSubsystem.arcadeDrive(0, 0);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	currentAngle = Robot.gyroSubsystem.gyroPosition();
    	if (targetAngle - error > currentAngle || targetAngle + error < currentAngle) {
    		return false;
    	} else {
    		Robot.driveSubsystem.arcadeDrive(0, 0);
    		return true;
    	}
    }


    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
