package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.DropCubeInSwitch;
import org.usfirst.frc.team2903.robot.commoners.DriveForTime;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStraightCubeRight extends CommandGroup {

	DriverStation gameData = Robot.gameData;
	
    public AutoStraightCubeRight() {
addSequential(new DriveForTime(1200, 1));
    	
    	if (Robot.gameData.getGameSpecificMessage().charAt(0) == 'R') {
        addSequential(new DropCubeInSwitch(true));
    	}
    }
}
