// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import frc.robot.Devices.*;
import frc.robot.Utils.AngleMath;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class DriveBase extends SubsystemBase {
  public SwerveModulePID frontRight;
  public SwerveModulePID frontLeft;
  public SwerveModulePID backRight;
  public SwerveModulePID backLeft;
  double alignmentThreshold;
  public SwerveDriveKinematics kinematics;
  SwerveModuleState[] moduleStates;

  /** Creates a new ExampleSubsystem. */
  public DriveBase(SwerveModulePID frontRight, SwerveModulePID frontLeft, SwerveModulePID backRight,
      SwerveModulePID backLeft,
      double alignmentThreshold, SwerveDriveKinematics kinematics) {
    this.frontRight = frontRight;
    this.frontLeft = frontLeft;
    this.backRight = backRight;
    this.backLeft = backLeft;
    if (alignmentThreshold > 1) {
      this.alignmentThreshold = 1;
    } else if (alignmentThreshold <= 0) {
      this.alignmentThreshold = 0.0001;
    } else {
      this.alignmentThreshold = alignmentThreshold;
    }
    this.kinematics = kinematics;
  }

  public void power(double voltageX, double voltageY, double turnVoltage, boolean errorOnLargeVoltage) {
    // Validation check for voltage limits.
    if (errorOnLargeVoltage) {
      // Shouldn't this check if sqrt(velocityX ^ 2 + velocityY ^ 2) is greater than
      // 12 as that will actually determine how much go voltage is used?
      if (Math.abs(voltageX + voltageY) / 2 > 12)
        throw new Error("Illegally large voltage - goVoltage");
      if (Math.abs(turnVoltage) > 12)
        throw new Error("Illegally large voltage - turnVoltage");
    }
    ChassisSpeeds chassis = new ChassisSpeeds(voltageX, voltageY, turnVoltage);
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(chassis);
    this.moduleStates = moduleStates;
    // Any particular reason for not just setting this.moduleStates =
    // kinematics.toSwerveModuleStates(chassis)

    // Here's an attempt at normalizing the voltages to be less than 12, not sure if
    // you wanted it here or somewhere else but you can use this as a skeleton
    // double largestVoltage = 0;
    // for (SwerveModuleState target : this.moduleStates) {
    // if (Math.abs(target.speedMetersPerSecond) > largestVoltage)
    // largestVoltage = Math.abs(target.speedMetersPerSecond); // not sure if
    // speedMetersPerSecond is quite voltage
    // }
    // if (largestVoltage > 12) {
    // final double scaleFactor = 12.0 / largestVoltage;
    // for (int module = 0; module < 4; module++) {
    // this.moduleStates[module].speedMetersPerSecond *= scaleFactor;
    // }
    // }
    // System.out.println("angle: " + moduleStates[0].angle.getDegrees());
    // System.out.println("speed: " + moduleStates[0].speedMetersPerSecond);
    frontRight.setTurnTarget(moduleStates[0].angle.getDegrees());
    frontLeft.setTurnTarget(moduleStates[1].angle.getDegrees());
    backRight.setTurnTarget(moduleStates[2].angle.getDegrees());
    backLeft.setTurnTarget(moduleStates[3].angle.getDegrees());
    frontRight.setGoVoltage(moduleStates[0].speedMetersPerSecond);
    frontLeft.setGoVoltage(moduleStates[1].speedMetersPerSecond);
    backRight.setGoVoltage(moduleStates[2].speedMetersPerSecond);
    backLeft.setGoVoltage(moduleStates[3].speedMetersPerSecond);

  }

  public void stopGoPower() {
    moduleStates = null;
    for (SwerveModulePID module : new SwerveModulePID[] { frontRight, frontLeft, backLeft, backRight }) {
      module.setGoVoltage(0);
    }
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleCommand() {
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
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
