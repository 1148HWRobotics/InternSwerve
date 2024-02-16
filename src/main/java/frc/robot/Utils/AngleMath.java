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
        // Adjust the angle difference to be within the range (-180 + offset, 180 +
        // offset].
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

    public static double getDeltaReversable(double current, double target) {
        var frontFaceError = AngleMath.getDelta(current, target);
        var backFaceError = AngleMath.getDelta(current, target - 180);
        // Use the smallest magnitude error, considering reversibility.
        var error = Math.min(frontFaceError, backFaceError);

        return error;
    }

    public static boolean shouldReverseCorrect(double current, double target) {
        // Calculate the error when facing the target directly.
        var frontFaceError = AngleMath.getDelta(current, target);
        // Calculate the error when facing away from the target (reversed by 180
        // degrees).
        var backFaceError = AngleMath.getDelta(current, target - 180);
        // Choose the smallest magnitude error.
        var error = Math.min(frontFaceError, backFaceError);

        // If the front face error is the smaller one, we should not reverse.
        return error == frontFaceError;
    }
}
