package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Lift2903 extends Subsystem {

	// motor
	static WPI_TalonSRX LiftMotor;

	public Lift2903() {
		// instantiate the talon motor controllers
		LiftMotor = new WPI_TalonSRX(RobotMap.LiftMotor);
		// enable the motors
		//LiftMotor.enable();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void MOVE(double speed) {
		// Pick balls up
		LiftMotor.set(speed);
	}

	public void StopLift() {
		LiftMotor.set(0);
	}
}