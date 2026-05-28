package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkMax m_motor1;
    private final SparkMax m_motor2;

    public ShooterSubsystem() {
        m_motor1 = new SparkMax(1, MotorType.kBrushless);
        m_motor2 = new SparkMax(2, MotorType.kBrushless);

        SparkMaxConfig motor2Config = new SparkMaxConfig();
        motor2Config.inverted(true);
        m_motor2.configure(motor2Config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        SparkMaxConfig motor1Config = new SparkMaxConfig();
        motor1Config.follow(m_motor2);
        m_motor1.configure(motor1Config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void shoot(double speed) {
        m_motor2.set(speed);
    }

    public void stop() {
        m_motor2.stopMotor();
    }
}
