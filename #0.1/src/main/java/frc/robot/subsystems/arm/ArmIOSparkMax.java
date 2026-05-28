package frc.robot.subsystems.arm;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;

public class ArmIOSparkMax implements ArmIO {

    private static final double GEAR_RATIO = 1.0;
    private static final double DEG_PER_MOTOR_ROTATION = 360.0 / GEAR_RATIO;

    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_lowerLimit;
    private final DigitalInput m_upperLimit;

    private double m_desiredAngleDeg = 0.0;

    public ArmIOSparkMax(int motorId, int lowerLimitChannel, int upperLimitChannel) {
        m_motor = new SparkMax(motorId, MotorType.kBrushless);
        m_encoder = m_motor.getEncoder();
        m_lowerLimit = new DigitalInput(lowerLimitChannel);
        m_upperLimit = new DigitalInput(upperLimitChannel);

        SparkMaxConfig config = new SparkMaxConfig();

        config.encoder
                .positionConversionFactor(DEG_PER_MOTOR_ROTATION)
                .velocityConversionFactor(1.0 / 60.0);

        m_motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void updateInputs(ArmIOInputs inputs) {
        inputs.currentAngleDeg = m_encoder.getPosition();
        inputs.desiredAngleDeg = m_desiredAngleDeg;
        inputs.lowerLimitSwitch = m_lowerLimit.get();
        inputs.upperLimitSwitch = m_upperLimit.get();
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
    public void calibrate() {
        m_encoder.setPosition(0.0);
        m_desiredAngleDeg = 0.0;
    }
}
