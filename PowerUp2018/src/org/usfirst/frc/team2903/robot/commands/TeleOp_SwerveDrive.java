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
public class TeleOp_SwerveDrive extends Command {
	
    public TeleOp_SwerveDrive() {
    	requires(Robot.driveSwerveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.driveSubsystem.setTeleopMode();
    	Robot.gyroSubsystem.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    ///////////////////////////Controller inputs/////////////////////////////
    	double Xforward = Robot.xboxJoy.getRawAxis(0); //left stick y-axis
    	//negative is forward, positive is backwards; we'd normally fix this, but it's too late now :P
    	
    	double Xside = Robot.xboxJoy.getRawAxis(0);	//left stick x-axis
    	double Xturn = Robot.xboxJoy.getRawAxis(0);	//right stick x-axis
    	
    //////////////////Turn inverts when driving forward//////////////////////
    	if (Xforward > 0) Xturn = -Xturn;
    	
    ///////////////////////////Main drive code///////////////////////////////
    	Robot.driveSwerveSubsystem.arcadeDrive(Xforward, Xside, Xturn);
    	
    //////////////////////SmartDashboard / Debugging/////////////////////////
    	SmartDashboard.putNumber("forward", -Xforward);
    	SmartDashboard.putNumber("turn", Xturn);
    	SmartDashboard.putNumber("Gyro", Robot.gyroSubsystem.gyroPosition());
    	SmartDashboard.putNumber("Acceleration ", Robot.gyroSubsystem.getAccel());
    	SmartDashboard.putNumber("POV", Robot.opJoy.getPOV());
    	
    	if (Robot.opJoy.getRawButton(3)) {
    		Robot.armSubsystem.throwCube(1);
    	} else if (Robot.opJoy.getRawButton(4)) {
    		Robot.armSubsystem.grabCube(1);
    	} 
//    	else {
//    		Robot.armSubsystem.stopArms();
//    	}
    	
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

    	Robot.liftSubsystem.MOVE((Robot.opJoy.getRawAxis(2) - Robot.opJoy.getRawAxis(3))*0.8);
    	
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
