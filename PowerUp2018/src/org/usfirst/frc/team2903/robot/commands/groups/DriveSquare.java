package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.commoners.GyroTurn;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveSquare extends CommandGroup {

    public DriveSquare() throws InterruptedException {
    	Thread.sleep(1000);
        addSequential(new GyroTurn(90));
        Thread.sleep(1000);
        addSequential(new GyroTurn(-90));
        Thread.sleep(1000);
        addSequential(new GyroTurn(360));
        Thread.sleep(1000);
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
