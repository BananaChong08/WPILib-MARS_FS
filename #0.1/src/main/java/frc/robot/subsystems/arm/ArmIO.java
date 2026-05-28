package frc.robot.subsystems.arm;

public interface ArmIO {

    class ArmIOInputs {
        public double currentAngleDeg = 0.0;
        public double desiredAngleDeg = 0.0;
        public boolean lowerLimitSwitch = false;
        public boolean upperLimitSwitch = false;
        public double appliedVolts = 0.0;
        public double currentAmps = 0.0;
    }

    default void updateInputs(ArmIOInputs inputs) {}

    default void setVoltage(double volts) {}

    default void setDutyCycle(double dutyCycle) {}

    default void setPosition(double angleDeg) {}

    default void stop() {}

    default void calibrate() {}
}
