package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commoners.GyroTurn;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveBaseline extends CommandGroup {

    public DriveBaseline() {
    	addSequential(new DriveForTime(1100, 1.0)); //750
    	
//    	Robot.driveSubsystem.arcadeDrive(0.6, 0);
//    	try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
//    	Robot.driveSubsystem.arcadeDrive(0, 0);
    	
//    	addSequential(new GyroTurn(45));//switch left or right 
//    	addSequential(new DriveForTime(5000, -0.8));
    	//Thread.sleep(10000);
    	
//        addSequential(new GyroTurn(90));
//        Thread.sleep(1000);
//        addSequential(new GyroTurn(-90));
//        Thread.sleep(1000);
//        addSequential(new GyroTurn(360));
//        Thread.sleep(1000);
    	
    	
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
