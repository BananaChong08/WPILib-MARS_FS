package frc.robot.subsystems.turret;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class TurretIOSparkMax implements TurretIO {

    private static final double GEAR_RATIO = 1.0;

    private static final double DEG_PER_MOTOR_ROTATION = 360.0 / GEAR_RATIO;

    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;

    private double m_desiredAngleDeg = 0.0;

    public TurretIOSparkMax(int motorId) {
        m_motor = new SparkMax(motorId, MotorType.kBrushless);
        m_encoder = m_motor.getEncoder();

        SparkMaxConfig config = new SparkMaxConfig();
        m_motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        config.encoder
                .positionConversionFactor(DEG_PER_MOTOR_ROTATION)
                .velocityConversionFactor(1.0 / 60.0);

    }

    @Override
    public void updateInputs(TurretIOInputs inputs) {
        inputs.currentAngleDeg = m_encoder.getPosition();
        inputs.desiredAngleDeg = m_desiredAngleDeg;
        inputs.velocityRPS = m_encoder.getVelocity();
        inputs.appliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
        inputs.currentAmps = m_motor.getOutputCurrent();
    }

    @Override
    public void setVoltage(double volts) {
        m_motor.setVoltage(volts);
    }

    @Override
    public void setDutyCycle(double dutyCycle) {
        m_motor.set(dutyCycle);
    }

    @Override
    public void setPosition(double angleDeg) {
        m_desiredAngleDeg = angleDeg;
    }

    @Override
    public void stop() {
        m_motor.stopMotor();
    }

    @Override
    public void resetEncoder() {
        m_encoder.setPosition(0.0);
        m_desiredAngleDeg = 0.0;
    }
}
