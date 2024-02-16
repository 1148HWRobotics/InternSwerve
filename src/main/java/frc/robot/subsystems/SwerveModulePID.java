// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Devices.SwerveModule;
import frc.robot.Utils.AngleMath;

public class SwerveModulePID extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  double kP;
  double kI;
  double kD;
  SwerveModule module;
  PIDController controller;
  public double error;

  public SwerveModulePID(double kP, double kI, double kD, SwerveModule module) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.module = module;
    this.controller = new PIDController(kP, kI, kD);
    controller.enableContinuousInput(-180, 180);
  }

  Double turnTarget = null;

  public void setTurnTarget(Double degrees) {
    this.turnTarget = AngleMath.conformAngle(degrees);
  }

  double voltage = 0.0;
  boolean frontFacing = true; // Indicates if the module is facing the front.

  public void setGoVoltage(double volts) {
    voltage = volts;
    module.setGoVoltage(frontFacing ? volts : -volts);
  }

  public void stop() {
    module.stop();
  }

  public double getTurnAngle() {
    return module.getEncoderReading();
  }

  /**
   * Retrieves the current angle of the module in standard position.
   *
   * @return The angle of the module in standard position.
   */
  public double getAngle() {
    return AngleMath.toStandardPosAngle(getTurnAngle());
  }

  /**
   * Retrieves the distance the module has traveled.
   *
   * @return The distance traveled by the module.
   */
  public double getDist() {
    return module.getGoReading();
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase tick() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {

        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    if (turnTarget != null) {
      this.error = getTurnAngle() - turnTarget;
      // System.out.println("turn angle " + getTurnAngle() + " turn target " +
      // turnTarget);
      // System.out.println(getTurnAngle());
      boolean isFrontFacing = AngleMath.shouldReverseCorrect(getTurnAngle(), turnTarget);

      // If the orientation of the module has changed, update the driving direction.
      if (isFrontFacing != this.frontFacing) {
        this.frontFacing = isFrontFacing;
        module.setGoVoltage(frontFacing ? voltage : -voltage);
      }
      // Calculate the voltage correction using the PID controller.
      // if (error > 0.5)
      module.setTurnVoltage(error * 0.1);
      // else {
      // module.setTurnVoltage(0.0);
      // }
      // module.setTurnVoltage(error * 0.1);
      // System.out.println("Correction voltage: " + correctionVoltage + "Error: " +
      // (turnTarget - getTurnAngle()));
      // Apply the correction voltage to the swerve module.
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
