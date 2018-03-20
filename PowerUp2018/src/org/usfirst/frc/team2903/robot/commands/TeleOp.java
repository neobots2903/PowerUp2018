package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commoners.GyroTurn;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleOp extends Command {

	public boolean isTANK = false;
	public boolean isTogglePressed = false;
	//PowerDistributionPanel pdp;
	
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
    	
    	double Xforward  = (Robot.xboxJoy.getRawAxis(3) - Robot.xboxJoy.getRawAxis(2))* -1;
    	double Xturn = Robot.xboxJoy.getRawAxis(0);
    	double tankLeft = Robot.xboxJoy.getRawAxis(1);
    	double tankRight = Robot.xboxJoy.getRawAxis(5);

    	if (Xforward <= 0) Xturn = -Xturn;
    	
    	SmartDashboard.putNumber("Current 12", Robot.PDP.getCurrent(12));
    	SmartDashboard.putNumber("Current 13", Robot.PDP.getCurrent(13));
    	SmartDashboard.putNumber("Current 14", Robot.PDP.getCurrent(14));
    	SmartDashboard.putNumber("Current 15", Robot.PDP.getCurrent(15));
    	SmartDashboard.putNumber("forward", Xforward);
    	SmartDashboard.putNumber("turn", Xturn);
    	SmartDashboard.putNumber("Gyro", Robot.gyroSubsystem.gyroPosition());
    	SmartDashboard.putNumber("Acceleration ", Robot.gyroSubsystem.getAccel());
    	
    	Robot.driveSubsystem.arcadeDrive(Xforward, Xforward <= 0 ? Xturn:-Xturn);
    	//Robot.driveSubsystem.tankDrive(tankLeft, tankRight);
    	
    	SmartDashboard.putNumber("POV", Robot.opJoy.getPOV());
    	
    	if (Robot.opJoy.getRawButton(6)) {
    		Robot.intakeSubsystem.throwCube(1);
    	} 
    	else if (Robot.opJoy.getRawButton(5)) {
    		Robot.intakeSubsystem.grabCube(1);
    	} 
    	else {
    		Robot.intakeSubsystem.stopArms();
    	}
    	
    	if (Robot.opJoy.getRawButton(1)) {
    		Robot.armSubsystem.openArms();
    	} 
    	else if (Robot.opJoy.getRawButton(2)) {
    		Robot.armSubsystem.closeArms();
    	}
    	
    	if (Robot.opJoy.getRawButton(7)) {
    		Robot.pivotSubsystem.movePivot(0.6);
    	} else if (Robot.opJoy.getRawButton(8)) {
    		Robot.pivotSubsystem.movePivot(-0.6);
    	} else {
    		Robot.pivotSubsystem.stopPivot();
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
