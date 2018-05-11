package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.DropCubeInScale;
import org.usfirst.frc.team2903.robot.commoners.DriveForTime;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStraightScaleLeft extends CommandGroup {

	DriverStation gameData = Robot.gameData;
	
    public AutoStraightScaleLeft() {
    	addParallel(new DriveForTime(2200, 1));
        addParallel(new DropCubeInScale(true, gameData, 'L'));
    }
}
