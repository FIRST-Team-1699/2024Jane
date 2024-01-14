package frc.robot.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import frc.robot.team1699.Constants.DrivetrainConstants;

public class Drivetrain {
    private TalonFXConfiguration portConfig;
    private TalonFXConfiguration starConfig;
    private TalonFX portLeader;
    private TalonFX portFollower;
    private TalonFX starLeader;
    private TalonFX starFollower;
    private DutyCycleOut portDutyCycle;
    private DutyCycleOut starDutyCycle;

    public Drivetrain() {
        portConfig = new TalonFXConfiguration();
        starConfig = new TalonFXConfiguration();

        portConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        starConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        this.portLeader = new TalonFX(DrivetrainConstants.kPortLeaderID);
        this.portFollower = new TalonFX(DrivetrainConstants.kPortFollowerID);
        portLeader.getConfigurator().apply(portConfig);
        portLeader.setSafetyEnabled(true);
        portFollower.getConfigurator().apply(portConfig);
        portFollower.setControl(new Follower(portLeader.getDeviceID(), false));

        this.starLeader = new TalonFX(DrivetrainConstants.kStarLeaderID);
        this.starFollower = new TalonFX(DrivetrainConstants.kStarFollowerID);
        starLeader.getConfigurator().apply(starConfig);
        starLeader.setSafetyEnabled(true);
        starFollower.getConfigurator().apply(starConfig);
        starFollower.setControl(new Follower(starLeader.getDeviceID(), false));
    }

    public void joystickDrive(double forward, double rotate) {
        // double portOutput = 0.0;
        // double starOutput = 0.0;

        // if(Math.abs(throttle) < .1) {
        //     throttle = 0;
        // }
        // if(Math.abs(rotate) < .1) {
        //     rotate = 0;
        // }

        // throttle = Math.copySign(throttle * throttle, throttle);
        // rotate = Math.copySign(rotate * rotate, rotate);

        // double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotate)), throttle);

        // if (throttle >= 0.0) {
        //     // First quadrant, else second quadrant
        //     if (rotate >= 0.0) {
        //         portOutput = maxInput;
        //         starOutput = throttle - rotate;
        //     } else {
        //         portOutput = throttle + rotate;
        //         starOutput = maxInput;
        //     }
        // } else {
        //     // Third quadrant, else fourth quadrant
        //     if (rotate >= 0.0) {
        //         portOutput = throttle + rotate;
        //         starOutput = maxInput;
        //     } else {
        //         portOutput = maxInput;
        //         starOutput = throttle - rotate;
        //     }
        // }

        // portLeader.set(TalonFXControlMode.PercentOutput, -portOutput);
        // starLeader.set(TalonFXControlMode.PercentOutput, starOutput);

        portDutyCycle.Output = forward + rotate;
        starDutyCycle.Output = forward - rotate;
        portLeader.setControl(portDutyCycle);
        starLeader.setControl(starDutyCycle);
    }

    public void setIdleMode(NeutralModeValue neutralMode) {
        portLeader.setNeutralMode(neutralMode);
        portFollower.setNeutralMode(neutralMode);
        starLeader.setNeutralMode(neutralMode);
        starFollower.setNeutralMode(neutralMode);
    }
}