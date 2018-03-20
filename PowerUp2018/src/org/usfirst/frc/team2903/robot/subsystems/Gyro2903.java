package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.SPI;

/**
 *
 */
public class Gyro2903 extends Subsystem {

	public ADXRS450_Gyro spiro;
	public Accelerometer accel = new BuiltInAccelerometer(); 
	
	public double gyroAngle;
	
	public Gyro2903() {
		spiro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	}
	
	public double gyroPosition() {
		gyroAngle = spiro.getAngle();
		return gyroAngle;
	}
	
	public double getAccel() {
		return accel.getY();
	}
	
	public void calibrate() {
		spiro.calibrate();
	}
	
	public void reset() {
		spiro.reset();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

