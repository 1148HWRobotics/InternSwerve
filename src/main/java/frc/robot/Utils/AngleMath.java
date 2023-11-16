package frc.robot.Utils;

public class AngleMath {
    public static double conformAngle(double angle) {
        angle %= 360;
        if (angle > 180) {
            angle -= 360;
        } else if (angle <= -180) {
            angle += 360;
        }
        return angle;
    }
    public static double getDelta(double current, double target) {
        double diff = target - current;
        // Adjust the angle difference to be within the range (-180, 180].
        return conformAngle(diff);
    }
    public static double getDelta(double current, double target, double offset) {
        double diff = target - current;
        // Adjust the angle difference to be within the range (-180, 180].
        diff = conformAngle(diff);
        // Adjust the angle difference to be within the range (-180 + offset, 180 + offset].
        diff = conformAngle(diff + offset) - offset;
        return diff;
    }
    public static double toTurnAngle(double standardPositionAngle) {
        // Adjust the angle to the robot's perspective.
        return AngleMath.conformAngle(90 - standardPositionAngle);
    }
    public static double toStandardPosAngle(double turnAngle) {
        // The conversion is symmetrical, so we can use the same method as toTurnAngle.
        return toTurnAngle(turnAngle);
    }
}
