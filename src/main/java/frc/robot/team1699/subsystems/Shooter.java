package frc.robot.team1699.subsystems;

import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.team1699.Constants.ShooterConstants;

public class Shooter {
    private TalonFX topMotor;
    private TalonFX bottomMotor;
    private SlotConfigs motorConfig;

    private DoubleSolenoid hoodSolenoid;

    private ShooterState currentState, wantedState;
    private double shooterSetpoint = 0.0;

    public Shooter() {
        topMotor = new TalonFX(ShooterConstants.kTopMotorID);
        bottomMotor = new TalonFX(ShooterConstants.kBottomMotorID);

        motorConfig.kP = ShooterConstants.kP;
        motorConfig.kI = ShooterConstants.kI;
        motorConfig.kD = ShooterConstants.kD;
        
        topMotor.getConfigurator().apply(motorConfig);
        bottomMotor.getConfigurator().apply(motorConfig);        
        hoodSolenoid = new DoubleSolenoid(ShooterConstants.kHoodSolenoidModulePort, PneumaticsModuleType.CTREPCM, ShooterConstants.kHoodSolenoidForwardPort, ShooterConstants.kHoodSolenoidReversePort);

        currentState = ShooterState.OFF;
        wantedState = ShooterState.OFF;
    }
    
    public void setTopMotor(double setpoint) {
        VelocityVoltage voltage = new VelocityVoltage(setpoint);
        topMotor.setControl(voltage);
    }

    public void setBottomMotor(double setpoint) {
        VelocityVoltage voltage = new VelocityVoltage(setpoint);
        bottomMotor.setControl(voltage);
    }

    public void setHoodUp() {
        hoodSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void setHoodDown() {
        hoodSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setSetpoint(double setpoint) {
        shooterSetpoint = setpoint;
    }

    private boolean topAtSetpoint() {
        if(Math.abs(topMotor.getVelocity().getValue() - shooterSetpoint) < ShooterConstants.kTolerance) {
            return true;
        }
        return false;
    }

    private boolean bottomAtSetpoint() {
        if(Math.abs(bottomMotor.getVelocity().getValue() - shooterSetpoint) < ShooterConstants.kTolerance) {
            return true;
        }
        return false;
    }

    public boolean atSetpoint() {
        if(topAtSetpoint() && bottomAtSetpoint()) {
            return true;
        }
        return false;
    }

    public void setWantedState(ShooterState wantedState) {
        if (this.wantedState != wantedState) {
            this.wantedState = wantedState;
            handleStateTransition();
        }
    }

    public void handleStateTransition() {
        switch(wantedState) {
            case OFF:
                break;
            case SHOOT_DOWN:
                setHoodDown();
                break;
            case SHOOT_UP:
                setHoodUp();
                break;
            default:
                break;
            
        } 
        currentState = wantedState;
    }

    public void update() {
        switch(currentState) {
            case OFF:
                setTopMotor(0);
                setBottomMotor(0);
                break;
            case SHOOT_DOWN:
            case SHOOT_UP:
                setTopMotor(shooterSetpoint);
                setBottomMotor(shooterSetpoint);
                break;
            default:
                break;
            
        }
    }
    
    public enum ShooterState {
        SHOOT_UP,
        SHOOT_DOWN,
        OFF
    } 
}