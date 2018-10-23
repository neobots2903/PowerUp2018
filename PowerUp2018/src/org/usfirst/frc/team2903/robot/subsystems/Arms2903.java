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

	// motor
		private WPI_TalonSRX ArmWheelLeftB;
		private WPI_TalonSRX ArmWheelRightB;
		public Solenoid ArmOpen;
		public Solenoid ArmClose;
		private WPI_TalonSRX ArmPivot;
		//How long the arms spin on a single button press (in milliseconds)
		public int spinTime = 500;
		
		public Arms2903() {
			// instantiate the talon motor controllers
			ArmWheelLeftB = new WPI_TalonSRX(RobotMap.ArmWheelLeft);
			ArmWheelRightB = new WPI_TalonSRX(RobotMap.ArmWheelRight);
	
			ArmOpen = new Solenoid (RobotMap.ArmOpen);
			ArmClose = new Solenoid (RobotMap.ArmClose);
			
			ArmPivot = new WPI_TalonSRX(RobotMap.ArmPivot);
			
//			ArmWheelLeft.configPeakOutputForward(1, 0);
//			ArmWheelLeft.configPeakOutputReverse(-1, 0);
//			ArmWheelRight.configPeakOutputForward(1, 0);
//			ArmWheelRight.configPeakOutputReverse(-1, 0);
		}

		public void grabCube(double speed) {
			long localTime = System.currentTimeMillis();
			while (localTime + spinTime > System.currentTimeMillis()) {
				ArmWheelLeftB.set(-speed);
				ArmWheelRightB.set(speed);
			}
		}
		
		public void throwCube(double speed) {
			long localTime = System.currentTimeMillis();
			while (localTime + spinTime > System.currentTimeMillis()) {
				ArmWheelLeftB.set(speed);
				ArmWheelRightB.set(-speed);
			}
		}
		
		public void stopArms() {
			ArmWheelLeftB.set(0);
			ArmWheelRightB.set(0);
		}
		
		public void openArms() {
			ArmOpen.set(true);
			ArmClose.set(false);
		}
		
		public void closeArms() {
			ArmOpen.set(false);
			ArmClose.set(true);
		}
		
		public void movePivot(double speed) {
			ArmPivot.set(speed);
		}
		
		public void stopPivot() {
			ArmPivot.set(0);
		}
    public void initDefaultCommand() {
    }
}

