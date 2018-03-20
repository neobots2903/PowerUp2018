package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commoners.GyroTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveSquare extends CommandGroup {

    public DriveSquare() {
        addSequential(new DriveForTime(1000, 0.7));
        addSequential(new GyroTurn(90));

    }
}
