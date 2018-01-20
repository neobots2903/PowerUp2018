package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive2903 extends Subsystem {

	WPI_TalonSRX leftFrontMotor;
	WPI_TalonSRX leftRearMotor;
	WPI_TalonSRX rightFrontMotor;
	WPI_TalonSRX rightRearMotor;
	SpeedControllerGroup m_Left;
	SpeedControllerGroup m_Right;
	
	static final int pidIdx = 0;
	static final int slotIdx = 0;
	static final int timeoutMs = 10;

	public DifferentialDrive robotDrive;

	public Drive2903() {

		int absolutePosition;

		// instantiate the talons
		leftFrontMotor = new WPI_TalonSRX(RobotMap.LeftTopMotor);
		leftRearMotor = new WPI_TalonSRX(RobotMap.LeftBottomMotor);
		rightFrontMotor = new WPI_TalonSRX(RobotMap.RightTopMotor);
		rightRearMotor = new WPI_TalonSRX(RobotMap.RightBottomMotor);
		
//		// configure the output
		rightFrontMotor.configPeakOutputForward(1, 0);
		rightFrontMotor.configPeakOutputReverse(-1, 0);
		leftFrontMotor.configPeakOutputForward(1, 0);
		leftFrontMotor.configPeakOutputReverse(-1, 0);
		rightRearMotor.configPeakOutputForward(1, 0);
		rightRearMotor.configPeakOutputReverse(-1, 0);
		leftRearMotor.configPeakOutputForward(1, 0);
		leftRearMotor.configPeakOutputReverse(-1, 0);
		
		m_Left = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
		m_Right = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
		
		robotDrive = new DifferentialDrive(m_Left, m_Right);
	}

	public void arcadeDrive(double forward, double turn) {
		robotDrive.arcadeDrive(forward, -(turn));
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setTeleopMode() {
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.PercentOutput,1);
		Robot.driveSubsystem.leftFrontMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.Follower,RobotMap.RightTopMotor);
		Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,RobotMap.LeftTopMotor);
	}
	
	public void setAutoMode() {
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.Position,1);
		Robot.driveSubsystem.leftFrontMotor.set(ControlMode.Position,0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.Follower,RobotMap.RightTopMotor);
		Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,RobotMap.LeftTopMotor);
	}
		
	// Low to High gear
	public void changeToHighGear() {
		Robot.pneumaticsSubsystem.highGear();
	}

	// high to low gear
	public void changeToLowGear() {
		Robot.pneumaticsSubsystem.lowGear();
	}

	public void driveReset() {
	}

}