package frc.robot.Devices;
import com.ctre.phoenix6.hardware.Pigeon2;
public class Imu {
    private Pigeon2 pigeon;
    public Imu(int canId) {
        this.pigeon = new Pigeon2(canId);
        var config = pigeon.getConfigurator();
        config.setYaw(90);
    }
    public double getHeading() {
        return this.pigeon.getAngle();
    }
    public double getPitch() {
        return this.pigeon.getPitch().getValue();
    }
    public double getRoll() {
        return this.pigeon.getRoll().getValue();
    }
    public double getAccelerationX(){
        return this.pigeon.getAccelerationX().getValue();
    }
    public double getAccelerationY(){
        return this.pigeon.getAccelerationY().getValue();
    }
    public double getAccelerationZ(){
        return this.pigeon.getAccelerationZ().getValue();
    }
    public double getAngularVelocityX(){
        return this.pigeon.getAngularVelocityX().getValue();
    }
    public double getAngularVelocityY(){
        return this.pigeon.getAngularVelocityY().getValue();
    }
    public double getAngularVelocityZ(){
        return this.pigeon.getAngularVelocityZ().getValue();
    }
    
}