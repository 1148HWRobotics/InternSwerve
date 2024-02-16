package frc.robot.Utils;

public class AccelerationCalculator {
    public static double getAccelerationDistance(double maxSpeed, double maxAcceleration) {
        return (Math.pow(maxSpeed, 2) / (2 * maxAcceleration));
    }

}
