package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive2903 extends Subsystem {

	WPI_TalonSRX leftFrontMotor;
	WPI_TalonSRX leftRearMotor;
	WPI_TalonSRX rightFrontMotor;
	WPI_TalonSRX rightRearMotor;

	static final double PI = 3.14159;
	static final int CODES_PER_MOTOR_REV = 256; // eg: Grayhill 61R256
	static final double COUNTS_PER_MOTOR_REV = 1024; // quadrature encoder
	static final double DRIVE_GEAR_REDUCTION = (30.0/54.0); // This is < 1.0 if geared up
	static final double WHEEL_DIAMETER_INCHES = 4.0; // For figuring circumference
	
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);
	static final double CM_PER_INCH = 2.54;
	static final double COUNTS_PER_CM = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / ((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);

	static final double TARGET_SPEED = 144; // revolutions per minute
	static final double MIN_PER_SEC = 0.0167; // minute to second ratio
	static final double SEC_PER_TVE = 0.1; // Seconds per 10 velocity
											// measurement periods (100ms)
	static final double NATIVE_UNITS_PER_TVE = TARGET_SPEED * MIN_PER_SEC * SEC_PER_TVE * COUNTS_PER_MOTOR_REV;
	static final double FULL_FORWARD = 1023;
	
	static final int pidIdx = 0;
	static final int slotIdx = 0;
	static final int timeoutMs = 10;

	// TARGET_SPEED needs to be determined from the maximum speed from the self-test display
	// while running in teleop. We probably only want to use 80% of that speed to make sure
	// that we can achieve the value.
	// F-GAIN = FULL FORWARD / NATIVE_UNITS_PER_TVE
	// FULL FORWARD comes from the SELF-TEST for Motor Controller 1 or 3

	public DifferentialDrive robotDrive;
	private int lastRightRawCount;
	private int lastLeftRawCount;
	private boolean autoPositionBothSides;

	public Drive2903() {

		int absolutePosition;

		// instantiate the talons
		leftFrontMotor = new WPI_TalonSRX(RobotMap.LeftTopMotor);
		leftRearMotor = new WPI_TalonSRX(RobotMap.LeftBottomMotor);
		rightFrontMotor = new WPI_TalonSRX(RobotMap.RightTopMotor);
		rightRearMotor = new WPI_TalonSRX(RobotMap.RightBottomMotor);

		rightFrontMotor.setInverted(false);
		rightRearMotor.setInverted(false);
		leftFrontMotor.setInverted(false);
		leftRearMotor.setInverted(false);

		robotDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
		
		// talon position set up 
		absolutePosition = leftFrontMotor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
		leftFrontMotor.getSensorCollection().setQuadraturePosition(absolutePosition,10);
		absolutePosition = rightFrontMotor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
		rightFrontMotor.getSensorCollection().setQuadraturePosition(absolutePosition,10);
		
		
		// disable timeout safety on drives
		rightFrontMotor.setSafetyEnabled(false);
		rightFrontMotor.set(0);
		leftFrontMotor.setSafetyEnabled(false);
		leftFrontMotor.set(0);

		// configure the encoders
		rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);
		rightFrontMotor.setInverted(true);
		//rightFrontMotor.configEncoderCodesPerRev(256);
		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);
		leftFrontMotor.setInverted(false);
		//leftFrontMotor.configEncoderCodesPerRev(256);

		// configure the output
		rightFrontMotor.configNominalOutputForward(+0, -0);
		rightFrontMotor.configPeakOutputReverse(+12, -12);
		leftFrontMotor.configNominalOutputForward(+0, -0);
		leftFrontMotor.configPeakOutputReverse(+12, -12);

		// Initialize the raw counts
		lastRightRawCount = 0;
		lastLeftRawCount = 0;
	}

	/**
	 * Converts inches to number of encoder counts.
	 *
	 * @param inches
	 *            The value in inches to convert
	 */
	public double convertInchesToEncoderCount(int inches) {
		return inches * COUNTS_PER_INCH;
	}

	/**
	 * Converts centimeters to number of encoder counts.
	 *
	 * @param centimeters
	 *            The value in centimeters to convert
	 */
	public double convertCentimetersToEncoder(int centimeters) {
		return centimeters * COUNTS_PER_CM;
	}

	public double getDistanceTraveled() {
		robotDrive.arcadeDrive(0, 0);
		if (robotDrive.isAlive() == true) {
			return (leftFrontMotor.getSelectedSensorPosition(pidIdx) + rightFrontMotor.getSelectedSensorPosition(pidIdx)) / 2;
		} else {
			return 0;
		}
	}

	  /**
	   * Arcade drive implements single stick driving. This function lets you directly provide
	   * joystick values from any source.
	   *
	   * @param forward     The value to use for forwards/backwards
	   * @param turn        The value to use for the rotate right/left
	   */
	public void arcadeDrive(double forward, double turn) {
		robotDrive.arcadeDrive(forward, turn);
	}

	  /**
	   * Provide tank steering using the stored robot configuration. This function lets you directly
	   * provide joystick values from any source.
	   *
	   * @param leftSpeed     The value of the left stick.
	   * @param rightSpeed    The value of the right stick.
	   */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		SmartDashboard.putNumber("Left Encoder", leftGetRawCount());
		SmartDashboard.putNumber("Right Encoder", rightGetRawCount());
		
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setAutoMode()
	{
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.leftFrontMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.Follower,0);
		Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,0);	
		
		// have the motors follow rightFrontMotor
		rightFrontMotor.set(0);
		leftFrontMotor.set(0);
		leftRearMotor.set(leftFrontMotor.getDeviceID());
		rightRearMotor.set(rightFrontMotor.getDeviceID());
		
		//Reset the encoder to zero as its current position
		rightFrontMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		rightFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);
		leftFrontMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		leftFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);
	}

	/**
	 * This will set the primary motor controller on the right side to position
	 * mode. Optionally, it can put the left side into position mode as well. By
	 * default, it will leave the left side in follower mode
	 * 
	 * @param bothSides
	 *            if true, sets the left side into position mode if false, sets
	 *            the left side into follower mode
	 */
	public void setAutoPositionMode(boolean bothSides) {

		autoPositionBothSides = bothSides;

		// set the right side primary to position and the secondary to follower
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.Position, 0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.Follower, 0);

		// talon position set up
		int absolutePosition = rightFrontMotor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
		rightFrontMotor.getSensorCollection().setQuadraturePosition(absolutePosition, 10);

		// have the motors follow rightFrontMotor
		rightFrontMotor.set(0);
		rightRearMotor.set(rightFrontMotor.getDeviceID());

		// Enable PID control on the talon
		//rightFrontMotor.enableControl();

		// Reset the encoder to zero as its current position
		rightFrontMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		rightFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);

		/* set closed loop gains in slot0 */
		rightFrontMotor.configAllowableClosedloopError(slotIdx, 0, timeoutMs);
		rightFrontMotor.selectProfileSlot(0, pidIdx);
		// rightFrontMotor.setF(FULL_FORWARD / NATIVE_UNITS_PER_TVE); // this
		// needs to be FULL-FOWARD / NATIVE UNITS)
		rightFrontMotor.config_kF(slotIdx, 0, timeoutMs); // this needs to be FULL-FOWARD / NATIVE UNITS)
		rightFrontMotor.config_kP(slotIdx, 0.1, timeoutMs);
		rightFrontMotor.config_kI(slotIdx, 0, timeoutMs);
		rightFrontMotor.config_kD(slotIdx, 0, timeoutMs);

		// both sides are going to be monitoring position
		if (bothSides) {
			// put left primary into position mode and the secondary to follower
			Robot.driveSubsystem.leftFrontMotor.set(ControlMode.Position,0);
			Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,0);

			// talon position set up
			absolutePosition = leftFrontMotor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
			leftFrontMotor.getSensorCollection().setQuadraturePosition(absolutePosition, 10);

			// left side follows right front motor
			leftFrontMotor.set(0);
			leftRearMotor.set(leftFrontMotor.getDeviceID());

			// Enable PID control on the talon
			//leftFrontMotor.enableControl();

			// Reset the encoder to zero as its current position
			leftFrontMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			leftFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);

			/* set closed loop gains in slot0 */
			leftFrontMotor.selectProfileSlot(slotIdx, pidIdx);

			// leftFrontMotor.setF(FULL_FORWARD / NATIVE_UNITS_PER_TVE); // this
			// needs to be FULL-FOWARD / NATIVE UNITS
			leftFrontMotor.config_kF(slotIdx, 0, timeoutMs);
			leftFrontMotor.config_kP(slotIdx, 0, timeoutMs);
			leftFrontMotor.config_kI(slotIdx, 0, timeoutMs);
			leftFrontMotor.config_kD(slotIdx, 0, timeoutMs);
		}

		// only the right side is monitoring position, left side follows right
		// side
		else {
			// put left side into follower mode
			Robot.driveSubsystem.leftFrontMotor.set(ControlMode.Follower,0);
			Robot.driveSubsystem.leftRearMotor.set(ControlMode.Follower,0);

			// left side follows right front motor
			leftFrontMotor.set(rightFrontMotor.getDeviceID());
			leftRearMotor.set(rightFrontMotor.getDeviceID());
		}
	}

	public void setTeleopMode() {
		Robot.driveSubsystem.rightFrontMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.leftFrontMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.rightRearMotor.set(ControlMode.PercentOutput,0);
		Robot.driveSubsystem.leftRearMotor.set(ControlMode.PercentOutput,0);

		leftRearMotor.set(0);
		rightRearMotor.set(0);
		leftFrontMotor.set(0);
		rightFrontMotor.set(0);
	}

	public void setPosition(long distanceToDrive) {
		rightFrontMotor.set(ControlMode.Position, distanceToDrive);
		if (autoPositionBothSides) {
			leftFrontMotor.set(ControlMode.Position, distanceToDrive);
		}
	}

	public void setVelocity(double velocity) {
		rightFrontMotor.set(ControlMode.PercentOutput, velocity);
		leftFrontMotor.set(ControlMode.PercentOutput, velocity);
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
		// disable timeout safety on drives
		rightFrontMotor.setSafetyEnabled(false);
		leftFrontMotor.setSafetyEnabled(false);

		// configure the encoders
		rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);
		rightFrontMotor.setInverted(true);
		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);
		leftFrontMotor.setInverted(false);

		// configure the output
		rightFrontMotor.configNominalOutputForward(+0, -0);
		rightFrontMotor.configPeakOutputReverse(+12, -12);

		leftFrontMotor.configNominalOutputForward(+0, -0);
		leftFrontMotor.configPeakOutputReverse(+12, -12);

		// Initialize the raw counts
		lastRightRawCount = 0;
		lastLeftRawCount = 0;

		rightFrontMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		rightFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);
		leftFrontMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		leftFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);
	}

	public int rightGetCount() {
		return (int) rightFrontMotor.getSelectedSensorPosition(pidIdx);
	}

	public int rightGetRawCount() {
		return (int) -rightFrontMotor.getSensorCollection().getQuadraturePosition(); // - lastRightRawCount;
	}

	public int leftGetCount() {
		return (int) leftFrontMotor.getSelectedSensorPosition(pidIdx);
	}

	public int leftGetRawCount() {
		return (int) leftFrontMotor.getSensorCollection().getQuadraturePosition(); // -lastLeftRawCount;
	}

	public void setLastRightRaw(int lastRawCount) {
		lastRightRawCount = lastRawCount;
	}

	public void setLastLeftRaw(int lastRawCount) {
		lastLeftRawCount = lastRawCount;
	}

}