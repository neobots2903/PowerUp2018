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
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team2903.robot.commands.TeleOp;
import org.usfirst.frc.team2903.robot.commands.groups.DriveSquare;
import org.usfirst.frc.team2903.robot.commands.groups.VisionTest;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903_SwerveDrive;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;

import org.usfirst.frc.team2903.robot.subsystems.Pneumatics2903;
import org.usfirst.frc.team2903.robot.subsystems.Arms2903;
import org.usfirst.frc.team2903.robot.subsystems.Climb2903;
import org.usfirst.frc.team2903.robot.subsystems.Lift2903;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update Andrew's manifesto file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive2903 driveSubsystem;
	public static Drive2903_SwerveDrive driveSwerveSubsystem;
	public static Arms2903 armSubsystem;
	public static Gyro2903 gyroSubsystem;
	public static Pneumatics2903 pneumaticsSubsystem;
	public static Lift2903 liftSubsystem;
	public static Climb2903 climbSubsystem;
	
	Command autonomousCommand;
	SendableChooser<Command> autoChooser;
	Command teleopCommand;
	
	Preferences prefs;
	
	public static String startPosition;
	public static boolean stayOnSide;
	public static boolean doJustBaseline;
	public static boolean doSwitch;
	public static int startDelay;
	
	public static DriverStation gameData;
	
	public static final int IMG_WIDTH = 640;
	public static final int IMG_HEIGHT = 480;

	public static Joystick opJoy = new Joystick(0);
	Button triggerKick = new JoystickButton(opJoy, 1);
	
	public static XboxController xboxJoy = new XboxController(3);
	
	public static CameraServer cserver = CameraServer.getInstance();
	public static UsbCamera camera;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
//
		pneumaticsSubsystem = new Pneumatics2903();
		driveSubsystem = new Drive2903();
		driveSwerveSubsystem = new Drive2903_SwerveDrive();
    	gyroSubsystem = new Gyro2903();
		liftSubsystem = new Lift2903();
		armSubsystem = new Arms2903();
		climbSubsystem = new Climb2903();
		
		// SmartDashboard.putNumber("kP", minipidSubsystem.getP());
		// SmartDashboard.putNumber("kI", minipidSubsystem.getI());
		// SmartDashboard.putNumber("kD", minipidSubsystem.getD());

		camera = cserver.startAutomaticCapture();
		
		// initialize the gyro
		
		gyroSubsystem.calibrate();
		gyroSubsystem.reset();

		autoChooser = new SendableChooser<Command>();
		try {
			autoChooser.addDefault("DriveSquare", new DriveSquare());
		} catch (InterruptedException e) { e.printStackTrace(); }
		autoChooser.addObject("VisionTest", new VisionTest());
		SmartDashboard.putData("AutoChooser", autoChooser);
		
		prefs = Preferences.getInstance();
		updatePrefs();
		
      		teleopCommand = new TeleOp();
			//teleopCommand = null;
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void updatePrefs() {
		startPosition = prefs.getString("Start position", "middle");
		stayOnSide = prefs.getBoolean("Stay on side", true);
		doJustBaseline = prefs.getBoolean("Do just baseline", false);
		doSwitch = prefs.getBoolean("Do switch", false);
		startDelay = prefs.getInt("Start Delay", 1);
		gameData = DriverStation.getInstance();
	}

	public void autonomousInit() {
//		// schedule the autonomous command (example)
		autonomousCommand = (Command) autoChooser.getSelected();
		updatePrefs();
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
//		if (autonomousCommand != null)
//			autonomousCommand.cancel();
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