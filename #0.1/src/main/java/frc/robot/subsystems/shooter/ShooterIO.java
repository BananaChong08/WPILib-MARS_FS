package frc.robot.subsystems.shooter;

public interface ShooterIO {

    class ShooterIOInputs {
        public double velocityRPM = 0.0;
        public double desiredVelocityRPM = 0.0;
        public double motorTempCelsius = 0.0;
        public boolean diskLoaded = false;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(ShooterIOInputs inputs) {}

    default void setVoltage(double volts) {}

    default void setVelocity(double rpm) {}

    default void stop() {}

    default void reverseMotor() {}
}
