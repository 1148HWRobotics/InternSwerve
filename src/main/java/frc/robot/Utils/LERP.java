package frc.robot.Utils;

/**
 * The LERP (Linear Interpolation) class provides a way to smoothly transition
 * between values over time.
 */
public class LERP implements Tickable {
    Double val; // The current value being interpolated.
    Double tar; // The target value to interpolate towards.
    double rate; // The rate at which to interpolate.

    // Constructor
    public LERP(double rate) {
        this.rate = rate;
    }

    public boolean isInitialized() {
        return tar != null;
    }

    public double get() {
        if (val == null)
            throw new Error("Lerp val was never set"); // Ensure 'val' has been initialized.
        return val;
    }

    // Set target value
    public void set(double next) {
        tar = next;
    }

    /**
     * Updates the current value towards the target value based on the elapsed time
     * and rate.
     * This method should be called periodically, typically on every tick of the
     * control loop.
     *
     * @param dTime The time elapsed since the last update.
     */
    public void tick(double dTime) {
        // If the current value is not set, initialize it with the target value.
        if (val == null)
            val = tar;
        // If the target value is not set, there's nothing to interpolate, so exit.
        if (tar == null)
            return;
        // Calculate the change needed to move 'val' closer to 'tar'.
        val += Math.signum(tar - val) * Math.min(Math.abs(rate * dTime), Math.abs(tar - val));
        // 'Math.signum(tar - val)' determines the direction of the change.
        // 'Math.min(Math.abs(rate * dTime), Math.abs(tar - val))' determines the size
        // of the change.
        // The change in value will not exceed the difference between 'val' and 'tar'.
    }
}