package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake2903 extends Subsystem {

	private WPI_TalonSRX ArmWheelLeft;
	private WPI_TalonSRX ArmWheelRight;
	
	public Intake2903() {
		ArmWheelLeft = new WPI_TalonSRX(RobotMap.ArmWheelLeft);
		ArmWheelRight = new WPI_TalonSRX(RobotMap.ArmWheelRight);
	}
	
	//Flipped Left and Right talons
	public void grabCube(double speed) {	
		ArmWheelLeft.set(-speed);
		ArmWheelRight.set(speed);
	}
	
	public void throwCube(double speed) {	
		ArmWheelLeft.set(speed);
		ArmWheelRight.set(-speed);
	}
	
	public void stopArms() {	
		ArmWheelLeft.set(0);
		ArmWheelRight.set(0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

