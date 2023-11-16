package frc.robot.Utils;

import edu.wpi.first.wpilibj.Timer;

public class Time {
    public static double getTimeSincePower() {
        return Timer.getFPGATimestamp();
    }

    public static double getMatchTime() {
        return Timer.getMatchTime();
    }
}
