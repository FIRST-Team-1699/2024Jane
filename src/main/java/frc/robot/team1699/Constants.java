package frc.robot.team1699;

public class Constants {
    public static class InputConstants {
        public static final int kDriverID = 0;
        public static final int kOperatorID = 1;
    }

    public static class DrivetrainConstants {
        public static final int kStarLeaderID = 36;
        public static final int kStarFollowerID = 31;
        public static final int kPortLeaderID = 35;
        public static final int kPortFollowerID = 32;
    }

    public static class IntakeHopperConstants {
        public static final int kIntakeHopperID = 16;

        public static final double kIntakePercent = .4;
        public static final double kOuttakePercent = -.5;
        public static final double kRunPercent = .4;

        public static final int kIntakeSolenoidModulePort = 1;
        public static final int kIntakeSolenoidForwardPort = 3;
        public static final int kIntakeSolenoidReversePort = 2;
    }

    public static class ShooterConstants {
        public static final int kTopMotorID = 37;
        public static final int kBottomMotorID = 38;
        public static final int kHoodSolenoidModulePort = 1;
        public static final int kHoodSolenoidForwardPort = 6;
        public static final int kHoodSolenoidReversePort = 7;

        public static final double kP = .0001;
        public static final double kI = 0.0;
        public static final double kD = 0.0;

        public static final double kTolerance = 5;
    }
}
