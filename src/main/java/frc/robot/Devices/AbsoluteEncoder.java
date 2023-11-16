package frc.robot.Devices;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;

import frc.robot.Utils.AngleMath;

public class AbsoluteEncoder {
    CANcoder coder; // The CANcoder device representing the absolute encoder.
    double zeroReading; // The encoder reading considered as the zero position.
    boolean reversed = false; // Flag indicating if the encoder values should be reversed.

    public AbsoluteEncoder(int canPort) {
        this(canPort, false);
    }

    public AbsoluteEncoder(int canPort, boolean isReversed) {
        this(canPort, 0, isReversed);
    }

    public AbsoluteEncoder(int canPort, double zeroReading, boolean isReversed) {
        this.reversed = isReversed;
        this.zeroReading = zeroReading;

        // Initialize the CANcoder with the specified CAN port.
        this.coder = new CANcoder(canPort);
        // Set up the encoder configuration.
        var configs = new CANcoderConfiguration();
        // Configure the encoder to measure the absolute position within a +/-180 degree
        // range.
        configs.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Signed_PlusMinusHalf;
        // Apply the configuration to the encoder.
        coder.getConfigurator().apply(configs);
    }

    public AbsoluteEncoder offset(double offset) {
        zeroReading = AngleMath.conformAngle(zeroReading - offset);
        return this;
    }

    private double reverse(double num) {
        return reversed ? -num : num;
        // returns negative value if reversed is true, otherwise returns original value
    }

    public double absVal() {
        // Get the absolute position from the encoder, adjust for zero reading and
        // reverse if needed.
        // The returned value is conformed to be within the range of -180 to 180
        // degrees.
        return AngleMath.conformAngle(reverse(coder.getAbsolutePosition().getValue() * 360.0 - zeroReading));
    }

}
