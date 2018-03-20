package org.usfirst.frc.team2903.robot.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForTime extends Command {
	
	private int time;
	private double speed;

    public DriveForTime(int millis, double _speed) {
    	requires(Robot.driveSubsystem);
    	time = millis;
    	speed = _speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	long localTime = System.currentTimeMillis();
    	while (localTime + time > System.currentTimeMillis()) {
    	Robot.driveSubsystem.arcadeDrive(-speed, 0);
    	}
    	Robot.driveSubsystem.arcadeDrive(0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
