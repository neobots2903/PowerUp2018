package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class DropCubeInScale extends Command {
	
	int millisLift = 1200;
	double speedLift = -1;
	int millisPivot = 300;
	double speedPivot = -0.9;
	int millisWheels = 500;
	double speedWheels = 1;
	
	double startTime = 0;
	boolean dropArms;
	private char targetSide;
	private DriverStation gameData;

    public DropCubeInScale(boolean _dropArms, DriverStation _gameData, char _targetSide) {
        dropArms = _dropArms;
        gameData = _gameData;
        targetSide = _targetSide;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
    		if (gameData.getGameSpecificMessage().charAt(1) == targetSide) {

    			startTime = System.currentTimeMillis();

    			while (startTime + millisLift > System.currentTimeMillis()) {
    				Robot.liftSubsystem.MOVE(speedLift);

    				if (dropArms) {
    					if (startTime + millisPivot > System.currentTimeMillis()) {
    						Robot.pivotSubsystem.movePivot(speedPivot);
    					} else {
    						Robot.pivotSubsystem.stopPivot();
    					}
    				}
    			}
    			Robot.liftSubsystem.StopLift();

    			//startTime = System.currentTimeMillis();
    			//while (startTime + millisWheels > System.currentTimeMillis()) {
    			//	Robot.intakeSubsystem.throwCube(speedWheels);
    			//}
    			//Robot.intakeSubsystem.stopArms();
    		}
    	}catch (Exception ex) {}
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
