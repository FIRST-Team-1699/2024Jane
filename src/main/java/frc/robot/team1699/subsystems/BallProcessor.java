package frc.robot.team1699.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.team1699.subsystems.IntakeHopper.IntakeHopperState;
import frc.robot.team1699.subsystems.Shooter.ShooterState;

public class BallProcessor {
    private DoubleSolenoid hopperSolenoid;
    private IntakeHopper intakeHopper;
    private Shooter shooter;
    private BallProcessorState wantedState, currentState;

    public void setHopperClosed() {
        hopperSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void setHopperOpen() {
        hopperSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setWantedState(BallProcessorState wantedState) {
        if(this.wantedState != wantedState) {
            this.wantedState = wantedState;
            handleStateTransition();
        }
    }

    public void handleStateTransition() {
        switch(wantedState) {
            case IDLE:
                setHopperClosed();
                intakeHopper.setWantedState(IntakeHopperState.STORED);
                shooter.setWantedState(ShooterState.OFF);
                break;
            case INTAKING:
                setHopperClosed();
                intakeHopper.setWantedState(IntakeHopperState.INTAKING);
                shooter.setWantedState(ShooterState.OFF);
                break;
            case OUTTAKING:
                setHopperClosed();
                intakeHopper.setWantedState(IntakeHopperState.OUTTAKING);
                shooter.setWantedState(ShooterState.OFF);
                break;
            case SHOOTING:
                setHopperClosed();
                intakeHopper.setWantedState(IntakeHopperState.STORED);
                shooter.setWantedState(ShooterState.SHOOT_UP);
                shooter.setSetpoint(60);
                break;
            default:
                break;
        }
        currentState = wantedState;
    }

    public void update() {
        switch(currentState) {
            case IDLE:
                break;
            case INTAKING:
                break;
            case OUTTAKING:
                break;
            case SHOOTING:
                if(shooter.atSetpoint()) {
                    setHopperOpen();
                    intakeHopper.setWantedState(IntakeHopperState.RUNNING);
                }
                break;
            default:
                break;
        }
        intakeHopper.update();
        shooter.update();
    }
    
    public enum BallProcessorState {
        INTAKING,
        IDLE,
        OUTTAKING,
        SHOOTING
    }
}
