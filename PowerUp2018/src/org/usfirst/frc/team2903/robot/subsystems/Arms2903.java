package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2903.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.mindsensors.CANSD540;
import edu.wpi.first.wpilibj.Spark;

public class Arms2903 extends Subsystem {

	// motor
		static Spark ArmWheelLeft;
		static Spark ArmWheelRight;
		
		public Arms2903() {
			// instantiate the talon motor controllers
			//ArmWheelLeft = new WPI_TalonSRX(RobotMap.ArmWheelLeft);
			ArmWheelLeft = new Spark(RobotMap.ArmWheelLeft);
			ArmWheelRight = new Spark(RobotMap.ArmWheelRight);
			
			//ArmWheelLeft.configPeakOutputForward(1, 0);
			//ArmWheelLeft.configPeakOutputReverse(-1, 0);
			//ArmWheelRight.configPeakOutputForward(1, 0);
			//ArmWheelRight.configPeakOutputReverse(-1, 0);
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
		
    public void initDefaultCommand() {
    }
}

