package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.DropCubeInSwitch;
import org.usfirst.frc.team2903.robot.commoners.DriveForTime;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStraightSwitchLeft extends CommandGroup {

	DriverStation gameData = Robot.gameData;
	
    public AutoStraightSwitchLeft() {
    	addParallel(new DriveForTime(1100, 1));
        addParallel(new DropCubeInSwitch(true, gameData, 'L'));
    }
}
