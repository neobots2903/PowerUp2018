package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commoners.GyroTurn;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleOp extends Command {

	public boolean isTANK = false;
	public boolean isTogglePressed = false;
	
    public TeleOp() {
    	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.driveSubsystem.setTeleopMode();
    	Robot.gyroSubsystem.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (Robot.xboxJoy.getRawButton(5)) {
			Robot.driveSubsystem.changeToLowGear();
    	} 
    	else if (Robot.xboxJoy.getRawButton(6)) {
    		Robot.driveSubsystem.changeToHighGear();
    	}
    	
    	double Xforward  = Robot.xboxJoy.getRawAxis(2) - Robot.xboxJoy.getRawAxis(3);
    	double Xturn = Robot.xboxJoy.getRawAxis(0);
    	
    	/////////////////////////////////////////////////////////////////////////
    	/////////////////Turn inverts when driving forward///////////////////////
    	/////////////////////////////////////////////////////////////////////////
    	if (Xforward > 0) Xturn = -Xturn;
    	
    	SmartDashboard.putNumber("forward", -Xforward);
    	SmartDashboard.putNumber("turn", Xturn);
    	SmartDashboard.putNumber("Gyro", Robot.gyroSubsystem.gyroPosition());
    	SmartDashboard.putNumber("Acceleration ", Robot.gyroSubsystem.getAccel());
    	Robot.driveSubsystem.arcadeDrive(Xforward, Xturn);
    	SmartDashboard.putNumber("POV", Robot.opJoy.getPOV());
    	
    	if (Robot.opJoy.getRawButton(5)) {
    		Robot.armSubsystem.throwCube();
    	} 
    	else if (Robot.opJoy.getRawButton(6)) {
    		Robot.armSubsystem.grabCube();
    	} 
    	else {
    		Robot.armSubsystem.stopArms();
    	}
    	
    	if (Robot.opJoy.getRawButton(1)) {
    		Robot.armSubsystem.openArms();
    	} 
    	else if (Robot.opJoy.getRawButton(2)) {
    		Robot.armSubsystem.closeArms();
    	} 
    	else {
    		Robot.armSubsystem.stopArms();
    	}
    	
    	if (Robot.opJoy.getRawButton(7)) {
    		Robot.armSubsystem.movePivot(0.6);
    	} else if (Robot.opJoy.getRawButton(8)) {
    		Robot.armSubsystem.movePivot(-0.6);
    	} else {
    		Robot.armSubsystem.stopPivot();
    	}

    	Robot.liftSubsystem.MOVE(Robot.opJoy.getRawAxis(3) - Robot.opJoy.getRawAxis(2));
    	
    	if (Robot.opJoy.getPOV(0) == 0) {
    		Robot.climbSubsystem.LiftOff();
    	} else if (Robot.opJoy.getPOV(0) == 180) {
    		Robot.climbSubsystem.Fall();
    	} else {
    		Robot.climbSubsystem.Stop(); 
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//no
    }
}
