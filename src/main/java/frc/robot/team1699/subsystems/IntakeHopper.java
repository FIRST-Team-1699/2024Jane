package frc.robot.team1699.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.team1699.Constants.IntakeHopperConstants;

public class IntakeHopper {
    private TalonSRX motor;
    private DoubleSolenoid solenoid;

    private IntakeHopperState currentState = IntakeHopperState.STORED;
    private IntakeHopperState wantedState = IntakeHopperState.STORED;

    public IntakeHopper() {
        motor = new TalonSRX(IntakeHopperConstants.kIntakeHopperID);
        solenoid = new DoubleSolenoid(IntakeHopperConstants.kIntakeSolenoidModulePort, PneumaticsModuleType.CTREPCM, IntakeHopperConstants.kIntakeSolenoidForwardPort, IntakeHopperConstants.kIntakeSolenoidReversePort);
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setMotor(double percent) {
        motor.set(TalonSRXControlMode.PercentOutput, percent);
    }

    public void deploySolenoid() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retractSolenoid() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setWantedState(IntakeHopperState wantedState) {
        if(this.wantedState != wantedState) {
            this.wantedState = wantedState;
            handleStateTransition();
        }
    }

    public void handleStateTransition() {
        switch(wantedState) {
            case INTAKING:
                deploySolenoid();
                break;
            case OUTTAKING:
                retractSolenoid();
                break;
            case RUNNING:
                retractSolenoid();
                break;
            case STORED:
                retractSolenoid();
                break;
            default:
                break;

        }
        currentState = wantedState;
    }

    public void update() {
        switch(currentState) {
            case INTAKING:
                setMotor(IntakeHopperConstants.kIntakePercent);
                break;
            case OUTTAKING:
                setMotor(IntakeHopperConstants.kOuttakePercent);
                break;
            case RUNNING:
                setMotor(IntakeHopperConstants.kRunPercent);
                break;
            case STORED:
                setMotor(0);
                break;
            default:
                break;

        }
    }

    public enum IntakeHopperState {
        STORED,
        RUNNING,
        INTAKING,
        OUTTAKING
    }
}
