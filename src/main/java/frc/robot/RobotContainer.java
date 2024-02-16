// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Devices.SwerveModule;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.SwerveModulePID;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveModule frontRight = new SwerveModule(0.26035, 0.26035, -40.25390625, 20, 3, 4, true);
  private final SwerveModule frontLeft = new SwerveModule(-0.26035, 0.26035, -5.09765625, 22, 5, 6, false);
  private final SwerveModule backRight = new SwerveModule(0.26035, -0.26035, 6.416015625, 23, 1, 2, true);
  private final SwerveModule backLeft = new SwerveModule(-0.26035, -0.26035, 68.203125, 21, 7, 8, false);

  private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontRight.getModulePos(),
      frontLeft.getModulePos(), backRight.getModulePos(), backLeft.getModulePos());

  private final SwerveModulePID frontRightPID = new SwerveModulePID(0.1, 0.1, 0.1, frontRight);
  public final SwerveModulePID frontLeftPID = new SwerveModulePID(0.1, 0.1, 0.1, frontLeft);
  private final SwerveModulePID backRightPID = new SwerveModulePID(0.1, 0.1, 0.1, backRight);
  private final SwerveModulePID backLeftPID = new SwerveModulePID(0.1, 0.1, 0.1, backLeft);

  public final DriveBase drive = new DriveBase(frontRightPID, frontLeftPID, backRightPID, backLeftPID, 0.5,
      kinematics);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  public final PS4Controller m_driverController = new PS4Controller(0);
  public final CommandJoystick m_operatorController = new CommandJoystick(1);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous;
    return null;
  }
}
