// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.PS4Controller;

/** An example command that uses an example subsystem. */
public class Teleop extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final DriveBase drive;
  private final PS4Controller con;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Teleop(DriveBase drive, PS4Controller controller) {
    this.drive = drive;
    this.con = controller;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.power(0, 0.001, 0, true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double OUTPUT_MAX = 11;
    double leftXInput = con.getLeftX() * OUTPUT_MAX;
    double leftYInput = con.getLeftY() * OUTPUT_MAX;
    double rightXInput = con.getRightX() * OUTPUT_MAX;
    // System.out.println("LeftX" + leftXInput + " LeftY " + leftYInput + " RightX "
    // + rightXInput);
    final double MINIMUMINPUT = 0.05;

    if (con.getLeftX() + con.getLeftY() + con.getRightX() > MINIMUMINPUT) {
      drive.power(leftXInput, leftYInput, rightXInput, true);
    } else {
      drive.power(0, 0, 0, false);
      drive.stopGoPower();
    }
  }

  // Called once the command ends or is interrupted.
  @Override

  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
