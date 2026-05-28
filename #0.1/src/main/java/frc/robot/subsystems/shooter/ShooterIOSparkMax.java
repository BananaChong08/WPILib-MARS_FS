package frc.robot.subsystems.shooter;

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

public class ShooterIOSparkMax implements ShooterIO {

    private static final double REVERSE_DUTY_CYCLE = -0.3;

    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_diskSensor;

    private double m_desiredVelocityRPM = 0.0;

    public ShooterIOSparkMax(int motorId, int diskSensorChannel) {
        m_motor = new SparkMax(motorId, MotorType.kBrushless);
        m_encoder = m_motor.getEncoder();
        m_diskSensor = new DigitalInput(diskSensorChannel);

        SparkMaxConfig config = new SparkMaxConfig();

        m_motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.velocityRPM = m_encoder.getVelocity();
        inputs.desiredVelocityRPM = m_desiredVelocityRPM;
        inputs.motorTempCelsius = m_motor.getMotorTemperature();
        inputs.diskLoaded = m_diskSensor.get();
        inputs.appliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
        inputs.currentAmps = m_motor.getOutputCurrent();
    }

    @Override
    public void setVoltage(double volts) {
        m_motor.setVoltage(volts);
    }

    @Override
    public void setVelocity(double rpm) {
        m_desiredVelocityRPM = rpm;
    }

    @Override
    public void stop() {
        m_motor.stopMotor();
    }

    @Override
    public void reverseMotor() {
        m_motor.set(REVERSE_DUTY_CYCLE);
    }
}
