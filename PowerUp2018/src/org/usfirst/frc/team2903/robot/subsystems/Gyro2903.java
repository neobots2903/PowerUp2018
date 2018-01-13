package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SPI;

/**
 *
 */
public class Gyro2903 extends Subsystem {

	public ADXRS450_Gyro spi;
	public AnalogGyro gyro;
	
	public double gyroAngle;
	
	//Initializing ANALOG and SPI enumerators
	public enum GYRO_TYPE {
		ANALOG, SPI
	};
	
	
	public Gyro2903(GYRO_TYPE gyroType) {
		switch(gyroType) {
			case ANALOG:
				gyro = new AnalogGyro(RobotMap.AnalogGyro); //Sets gyro to the Hardware Map gyro
				break;
			
			case SPI:
				spi = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
				break;
		}
	}
	
	public double gyroPosition(GYRO_TYPE gyroType) {
		
		switch(gyroType) {
		case ANALOG:
			gyro.getAngle(); //Sets gyro to the Hardware Map gyro
			break;
		
		case SPI:
			spi.getAngle();
			break;
		}
		
		gyroAngle = gyroAngle % 360;
		return gyroAngle;
	}
	
	public void calibrate(GYRO_TYPE gyroType) {
		
		switch(gyroType) {
		case ANALOG:
			gyro.calibrate(); //Sets gyro to the Hardware Map gyro
			break;
		
		case SPI:
			spi.calibrate();
			break;
		}
		
	}
	
	public void reset(GYRO_TYPE gyroType) {
		
		switch(gyroType) {
		case ANALOG:
			gyro.reset(); //Sets gyro to the Hardware Map gyro
			break;
		
		case SPI:
			spi.reset();
			break;
		}
		
		
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

