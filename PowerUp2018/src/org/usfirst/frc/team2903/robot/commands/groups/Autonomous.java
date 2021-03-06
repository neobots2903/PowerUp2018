package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commoners.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

	private DriverStation gameData = Robot.gameData;
	private int switchLocation;
	private int stationLocation;
	
    public Autonomous() throws InterruptedException {
    	
    	if (gameData.getGameSpecificMessage().charAt(0) == 'L') {
    		 switchLocation = 1;
    	} else if (gameData.getGameSpecificMessage().charAt(0) == 'R') {
    		switchLocation = 3;
    	}
    	
		if (Robot.startPosition.equalsIgnoreCase("left")) {
			stationLocation = 1;
		} else if (Robot.startPosition.equalsIgnoreCase("middle")) {
			stationLocation = 2;
		} else if (Robot.startPosition.equalsIgnoreCase("right")) {
			stationLocation = 3;
		} else {
			stationLocation = gameData.getLocation();
		}
		
		Robot.driveSubsystem.changeToLowGear();
		Robot.armSubsystem.closeArms();
    	
        Thread.sleep(Robot.startDelay);
        
        if (Robot.doJustBaseline) { //if we're crossing the baseline...
    		doBaseline();
        } else {
        	doBaseline();
				
			if (Robot.doSwitch) {	//and if we're doing the switch
				addSequential(new SwitchAim(true));	//then aim to the switch
				Robot.armSubsystem.throwCube(0.8);
				Thread.sleep(500);
				Robot.armSubsystem.stopArms();
			}
        }
    }
    
    private void doBaseline() {
    	if (Robot.stayOnSide) {	//and we have to stay on our side
			//We have to stay on our side, so we will still drive straight.
			addSequential(new DriveForTime(2000, 0.7));	//Drive straight across line.
		} else {	//if we don't have to stay on our side...
			switch (stationLocation) {
			case 1:
				if (switchLocation == 1) {
					addSequential(new DriveForTime(2000, 0.7));	//Drive straight across line.
				} else if (switchLocation == 3) {
					addSequential(new GyroTurn(90));	//Turn to the right
					addSequential(new DriveForTime(2000, 0.7));	//Drive to right side
					addSequential(new GyroTurn(-90));	//Turn to the left (straightening back out)
					addSequential(new DriveForTime(2000, 0.7));	//Drive straight across the line
				}
				break;
			case 2:
				if (switchLocation == 1) {
					addSequential(new GyroTurn(-90));	//Turn to the left
					addSequential(new DriveForTime(1000, 0.7));	//Drive to left side
					addSequential(new GyroTurn(-90));	//Turn to the right (straightening back out)
					addSequential(new DriveForTime(2000, 0.7));	//Drive straight across the line
				} else if (switchLocation == 3) {
					addSequential(new GyroTurn(90));	//Turn to the right
					addSequential(new DriveForTime(1000, 0.7));	//Drive to right side
					addSequential(new GyroTurn(-90));	//Turn to the left (straightening back out)
					addSequential(new DriveForTime(2000, 0.7));	//Drive straight across the line
				}
				break;
			case 3:
				if (switchLocation == 1) {
					addSequential(new GyroTurn(-90));	//Turn to the left
					addSequential(new DriveForTime(2000, 0.7));	//Drive to left side
					addSequential(new GyroTurn(-90));	//Turn to the right (straightening back out)
					addSequential(new DriveForTime(2000, 0.7));	//Drive straight across the line
				} else if (switchLocation == 3) {
					addSequential(new DriveForTime(2000, 0.7));	//Drive straight across line.
				}
				break;
			}
		}
    }
}
