package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class EncoderTest extends CommandGroup {

	private int position;
	
    public EncoderTest() {
    	while (true) {
    		try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		position = Robot.driveSubsystem.leftFrontMotor.getSelectedSensorPosition(0);
    		SmartDashboard.putNumber("Encoder Value", position);
    	}
    }
}
