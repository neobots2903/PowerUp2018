/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2903.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2903.robot.commands.MinimalAutonomous;
import org.usfirst.frc.team2903.robot.commands.TeleOp;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;
import org.usfirst.frc.team2903.robot.subsystems.Vision2903;

import org.usfirst.frc.team2903.robot.subsystems.Pneumatics2903;
import org.usfirst.frc.team2903.robot.subsystems.Climber2903;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive2903 driveSubsystem;
	public static Gyro2903 gyroSubsystem;
	public static Pneumatics2903 pneumaticsSubsystem;
	public static Climber2903 climberSubsystem;
	public static Vision2903 camera;
	
	Command autonomousCommand;
	SendableChooser<Command> autoChooser;
	Command teleopCommand;

	public static Joystick joyOp = new Joystick(0);
	Button triggerKick = new JoystickButton(joyOp, 1);

	public static Joystick joy1 = new Joystick(1);

	public static Port lidarPort = I2C.Port.kOnboard;

	public static final int IMG_WIDTH = 640;
	public static final int IMG_HEIGHT = 480;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		pneumaticsSubsystem = new Pneumatics2903();

		driveSubsystem = new Drive2903();
		Gyro2903.GYRO_TYPE gyroType = Gyro2903.GYRO_TYPE.SPI;
		gyroSubsystem = new Gyro2903(gyroType);
		climberSubsystem = new Climber2903();

		camera = null;//new Vision2903("10.29.3.56"); 
		
		// SmartDashboard.putNumber("kP", minipidSubsystem.getP());
		// SmartDashboard.putNumber("kI", minipidSubsystem.getI());
		// SmartDashboard.putNumber("kD", minipidSubsystem.getD());

		// SmartDashboard.putNumber("LIDAR Distance From Object",
		// lidarSubsystem.getDistance());

		// initialize the gyro
		//gyroSubsystem.reset();
		//gyroSubsystem.Calibrate();

		autoChooser = new SendableChooser<Command>();
			//autoChooser.addObject("RightGear", new RightGear());
			//autoChooser.addDefault("MiddleGear", new MiddleGear());
			//autoChooser.addObject("LeftGear", new LeftGear());
			autoChooser.addDefault("MinimalAutonomous", new MinimalAutonomous());
			SmartDashboard.putData("AutoChooser", autoChooser);

		teleopCommand = new TeleOp();
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		teleopCommand.start();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	}

	public static void disable() {
	}

}