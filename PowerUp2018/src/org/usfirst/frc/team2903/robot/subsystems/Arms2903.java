package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.mindsensors.CANSD540;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Arms2903 extends Subsystem {

		public Solenoid ArmOpen;
		public Solenoid ArmClose;
		
		public Arms2903() {
			ArmOpen = new Solenoid (RobotMap.ArmOpen);
			ArmClose = new Solenoid (RobotMap.ArmClose);
		}

		public void openArms() {
			ArmOpen.set(true);
			ArmClose.set(false);
		}
		
		public void closeArms() {
			ArmOpen.set(false);
			ArmClose.set(true);
		}
		
    public void initDefaultCommand() {
    }
}

