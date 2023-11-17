package frc.robot.Devices;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Utils.AngleMath;

public class SwerveModule {
    private Translation2d modulePos;
    private Falcon turnMotor;
    private Falcon goMotor;
    private AbsoluteEncoder turnEncoder;

    public SwerveModule(double moduleX, double moduleY, double encoderZero, int encoderCanID, int goCanID,
            int turnCanID, boolean goReversed) {
        this.modulePos = new Translation2d(moduleX, moduleY);
        this.turnMotor = new Falcon(turnCanID, false);
        this.goMotor = new Falcon(goCanID, goReversed);
        this.turnEncoder = new AbsoluteEncoder(turnCanID, encoderZero, false);
        turnMotor.setCurrentLimit(35);
        goMotor.setCurrentLimit(52);
        turnMotor.resetEncoder();
        goMotor.resetEncoder();
    }

    public void setGoVoltage(double voltage) {
        goMotor.setVoltage(voltage);
    }

    public Translation2d getModulePos() {
        return this.modulePos;
    }

    /**
     * Sets the voltage for the turning motor.
     * 
     * @param voltage The voltage to be applied to the turning motor.
     */
    public void setTurnVoltage(double voltage) {
        turnMotor.setVoltage(voltage);
    }

    /**
     * Resets the encoder reading for the turning motor.
     */
    public void resetTurnReading() {
        turnMotor.resetEncoder();
    }

    /**
     * Gets the raw, unnormalized turn encoder reading.
     * 
     * @return The raw turn encoder value.
     */
    public double getUnconformedTurnReading() {
        return turnMotor.getDegrees() / 12.8; // Convert encoder ticks to degrees.
    }

    /**
     * Gets the normalized angle of the module's turn.
     * 
     * @return The turn angle normalized to the range (-180, 180].
     */
    public double getTurnReading() {
        // Normalize the angle to be within the range (-180, 180].
        return AngleMath.conformAngle(getUnconformedTurnReading());
    }

    /**
     * Resets the encoder reading for the driving motor.
     */
    public void resetGoReading() {
        goMotor.resetEncoder();
    }

    /**
     * Gets the distance the module has traveled in inches.
     * 
     * @return The distance traveled by the driving motor, adjusted for wheel
     *         rotation.
     */
    public double getGoReading() {
        // Calculate the compensation for wheel turns based on the turn angle.
        final double turnCompensation = 3.75 * (getUnconformedTurnReading() / 180.0);
        // Convert the encoder reading to inches traveled.
        return turnCompensation + (goMotor.getDegrees() / 360.0) / 6.75 * (3.82 * Math.PI);
        // Note: 6.75 motor rotations per 1 wheel rotation, wheel diameter is 3.82
        // inches.
    }

}
