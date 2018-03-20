package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class DropCubeInSwitch extends Command {
	
	boolean dropArms;

    public DropCubeInSwitch(boolean _dropArms) {
        dropArms = _dropArms;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (dropArms) {
    		Robot.pivotSubsystem.movePivot(-0.7);
    		
    		try {
    			Thread.sleep(800);
    		} catch (InterruptedException e) {}
    		
    		Robot.pivotSubsystem.stopPivot();
    	}
    	
    	Robot.liftSubsystem.MOVE(1);
        
        try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {}
        
        Robot.liftSubsystem.StopLift();
        
        Robot.intakeSubsystem.throwCube(1);
        
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
        
        Robot.intakeSubsystem.stopArms();
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
