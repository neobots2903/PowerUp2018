package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pivot2903 extends Subsystem {

    private WPI_TalonSRX ArmPivot;

	public Pivot2903() {
    	ArmPivot = new WPI_TalonSRX(RobotMap.ArmPivot);
    }

	public void movePivot(double speed) {
		ArmPivot.set(speed);
	}
	
	public void stopPivot() {
		ArmPivot.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

