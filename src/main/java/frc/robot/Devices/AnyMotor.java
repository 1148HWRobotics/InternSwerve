package frc.robot.Devices;

public abstract class AnyMotor {
    Double maxSlew; // maximum rate of voltage change over time
    boolean isReversed; // Flag indicating whether motor's direction is reversed

    public abstract int getID();

    public AnyMotor(boolean isReversed, double maxSlew) {
        this.maxSlew = maxSlew;
        this.isReversed = isReversed;
    }

    public AnyMotor(boolean isReversed) {
        this.isReversed = isReversed;
        maxSlew = null;
    }

    double resetPos = 0; // The position of the encoder when last reset.
}
