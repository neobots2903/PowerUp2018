package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climb2903 extends Subsystem {

	static WPI_TalonSRX ClimbLeft;
	static WPI_TalonSRX ClimbRight;

	public Climb2903() {
		ClimbLeft = new WPI_TalonSRX(RobotMap.ClimbLeft);
		ClimbRight = new WPI_TalonSRX(RobotMap.ClimbRight);
	}
	
	public void LiftOff() {
		ClimbLeft.set(1);
		ClimbRight.set(1);
	}
	
	public void Fall() {
		ClimbLeft.set(-1);
		ClimbRight.set(-1);
	}
	
	public void Stop() {
		ClimbLeft.set(0);
		ClimbRight.set(0);
	}

	public void initDefaultCommand() {
	}
}

