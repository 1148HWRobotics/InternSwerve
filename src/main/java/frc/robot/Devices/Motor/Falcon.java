package frc.robot.Devices.Motor;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

// import frc.robot.Core.Scheduler;
import frc.robot.Devices.AnyMotor;
import frc.robot.Utils.GetDTime;
import frc.robot.Utils.LERP;

public class Falcon extends AnyMotor {
    private TalonFX falcon; // The Talon FX motor controller object.
    double stallVolt; // The voltage at which the motor is considered to be stalling.

    final int id; // Unique identifier for the motor controller.

    // Return motor ID
    public int getID() {
        return id;
    }

    // Sets maximum current in amperes for motor
    public void setCurrentLimit(int amps) {
        var config = falcon.getConfigurator();
        var currentConfig = new CurrentLimitsConfigs();
        currentConfig.SupplyCurrentLimit = amps;
        config.apply(currentConfig);
    }

    // Constructors
    public Falcon(int deviceNumber, boolean isReversed, boolean isStallable) {
        super(isReversed);

        this.id = deviceNumber;

        this.falcon = new TalonFX(deviceNumber);
        falcon.setInverted(false);
        this.stallVolt = isStallable ? 3 : 0; // Set stall voltage if the motor is stallable.
    }

    public Falcon(int deviceNumber, boolean isReversed) {
        this(deviceNumber, isReversed, false);
    }

    protected void uSetVoltage(double volts) {
        var sinceTimeoutChecked = sinceCheckedTimeout.tick();
        // checks for motor disconnect
        if (!enabledLerp.isInitialized())
            enabledLerp.set(1);
        enabledLerp.set(falcon.isAlive() ? 1 : 0);
        enabledLerp.tick(sinceTimeoutChecked);
        enabled = enabledLerp.get() > 0.5;
        // sends voltages
        if (!enabled) {
            falcon.setVoltage(0);
            return;
        }
        double fac = (volts > 0) ? 1 : -1; // Determine the direction of the voltage.
        if (Math.abs(volts) < stallVolt / 2) {
            falcon.setVoltage(0); // If voltage is below half stall, turn off motor.
            return;
        } else if (Math.abs(volts) < stallVolt) {
            falcon.setVoltage(stallVolt * fac); // Apply stall voltage if voltage is below stall level.
            return;
        }

        falcon.setVoltage(volts); // Apply the full voltage if above stall level.
    }

    protected double uGetRevs() {
        return falcon.getPosition().getValue();
    }

    public void stop() {
        falcon.stopMotor();
    }

    boolean enabled = true;
    LERP enabledLerp = new LERP(0.01);
    GetDTime sinceCheckedTimeout = new GetDTime();
}
