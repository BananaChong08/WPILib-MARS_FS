package frc.robot.subsystems.turret;

public interface TurretIO {

    class TurretIOInputs {
        public double currentAngleDeg = 0.0;
        public double desiredAngleDeg = 0.0;
        public double velocityRPS = 0.0;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(TurretIOInputs inputs) {
    }

    default void setVoltage(double volts) {
    }

    default void setDutyCycle(double dutyCycle) {
    }

    default void setPosition(double angleDeg) {
    }

    default void stop() {
    }

    default void resetEncoder() {
    }
}
