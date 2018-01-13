package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics2903 extends Subsystem {
	public Solenoid highGearShift = new Solenoid (RobotMap.highGearShiftSol);
	public Solenoid lowGearShift = new Solenoid (RobotMap.lowGearShiftSol);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Pneumatics2903() {
		highGearShift.set(false);
		lowGearShift.set(true);
		}
	
	public void highGear() {
		if (lowGearShift.get()) {
			lowGearShift.set(false);
			highGearShift.set(true);
		}
	}

	public void lowGear() {
		if (highGearShift.get()) {
			highGearShift.set(false);
			lowGearShift.set(true);
		}
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

