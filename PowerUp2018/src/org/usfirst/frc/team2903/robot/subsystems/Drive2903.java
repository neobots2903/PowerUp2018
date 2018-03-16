package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive2903 extends Subsystem 
{
	public WPI_TalonSRX leftFrontMotor;
	public WPI_TalonSRX leftRearMotor;
	public WPI_TalonSRX rightFrontMotor;
	public WPI_TalonSRX rightRearMotor;
	SpeedControllerGroup m_Left;
	SpeedControllerGroup m_Right;
	
	static double maxOutput = 1.0;

	public DifferentialDrive robotDrive;

	public Drive2903()
	{
		// instantiate the talons
		leftFrontMotor = new WPI_TalonSRX(RobotMap.LeftTopMotor);
		leftRearMotor = new WPI_TalonSRX(RobotMap.LeftBottomMotor);
		rightFrontMotor = new WPI_TalonSRX(RobotMap.RightTopMotor);
		rightRearMotor = new WPI_TalonSRX(RobotMap.RightBottomMotor);
		
		// configure encoders
		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
		// configure the output
		rightFrontMotor.configPeakOutputForward(maxOutput, 0);
		rightFrontMotor.configPeakOutputReverse(-maxOutput, 0);
		leftFrontMotor.configPeakOutputForward(maxOutput, 0);
		leftFrontMotor.configPeakOutputReverse(-maxOutput, 0);
		rightRearMotor.configPeakOutputForward(maxOutput, 0);
		
		rightRearMotor.configPeakOutputReverse(-maxOutput, 0);
		leftRearMotor.configPeakOutputForward(maxOutput, 0);
		leftRearMotor.configPeakOutputReverse(-maxOutput, 0);
		
		//rightFrontMotor.configPeakCurrentLimit(amps, timeoutMs)
		
		m_Left = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
		m_Right = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
		
		robotDrive = new DifferentialDrive(m_Left, m_Right);
	}

	public void arcadeDrive(double forward, double turn) 
	{
		robotDrive.arcadeDrive(forward, turn);
	}

	@Override
	protected void initDefaultCommand() 
	{
		
	}

	public void setTeleopMode() 
	{
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.PercentOutput,1);
		Robot.driveSubsystem.leftFrontMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.PercentOutput,1);
		Robot.driveSubsystem.leftRearMotor.set(ControlMode.PercentOutput,0);
//		Robot.driveSubsystem.rightRearMotor.set(ControlMode.Follower,RobotMap.RightTopMotor);
//		Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,RobotMap.LeftTopMotor);
	}
	
	public void setAutoMode()
	{
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.Position,1);
		Robot.driveSubsystem.leftFrontMotor.set(ControlMode.Position,0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.Follower,RobotMap.RightTopMotor);
		Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,RobotMap.LeftTopMotor);
	}
		
	// Low to High gear
	public void changeToHighGear() 
	{
		Robot.pneumaticsSubsystem.highGear();
	}

	// high to low gear
	public void changeToLowGear() 
	{
		Robot.pneumaticsSubsystem.lowGear();
	}

	public void driveReset()
	{
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

}