package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2903.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.mindsensors.CANSD540;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Arms2903 extends Subsystem {

	// motor
		static WPI_TalonSRX ArmWheelLeft;
		static WPI_TalonSRX ArmWheelRight;
		public Solenoid leftArmOpen;
		public Solenoid rightArmOpen;
		private WPI_TalonSRX ArmPivot;
		
		public Arms2903() {
			// instantiate the talon motor controllers
			ArmWheelLeft = new WPI_TalonSRX(RobotMap.ArmWheelLeft);
			ArmWheelRight = new WPI_TalonSRX(RobotMap.ArmWheelRight);
			
			leftArmOpen = new Solenoid (RobotMap.leftArmOpen);
			rightArmOpen = new Solenoid (RobotMap.rightArmOpen);
			
			ArmPivot = new WPI_TalonSRX(RobotMap.ArmPivot);
			
			ArmWheelLeft.configPeakOutputForward(1, 0);
			ArmWheelLeft.configPeakOutputReverse(-1, 0);
			ArmWheelRight.configPeakOutputForward(1, 0);
			ArmWheelRight.configPeakOutputReverse(-1, 0);
		}

		public void grabCube() {
			ArmWheelLeft.set(1);
			ArmWheelRight.set(1);
		}
		
		public void throwCube() {
			ArmWheelLeft.set(-1);
			ArmWheelRight.set(-1);
		}
		
		public void stopArms() {
			ArmWheelLeft.set(0);
			ArmWheelRight.set(0);
		}
		
		public void openArms() {
			leftArmOpen.set(false);
			rightArmOpen.set(false);
		}
		
		public void closeArms() {
			leftArmOpen.set(true);
			rightArmOpen.set(true);
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

