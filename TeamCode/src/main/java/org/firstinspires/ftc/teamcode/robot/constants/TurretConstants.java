package org.firstinspires.ftc.teamcode.robot.constants;

import com.pedropathing.geometry.Pose;

public class TurretConstants {

    public static Pose GOAL_POSE = new Pose(0, 144);

    public static double flywheelKP = 0.0, flywheelKI = 0.0, flywheelKD = 0.0, flywheelKF = 0.0;
    public static double turretKP = 0.0, turretKI = 0.0, turretKD = 0.0, turretKF = 0.0;

    public static double TURRET_MAX_POWER = 0.85;
    public static double TURRET_TX_TOLERANCE_DEG = 1;
    public static double TURRET_RECOVERY_POWER = 0.2; // slow creep to re-acquire tag

    // Flywheel velocity tolerance (ticks per second) to consider "at speed"
    public static double FLYWHEEL_TPS_TOLERANCE = 20; // TODO: tune

    // Idle flywheel speed used when not actively tracking an interpolated shooting target
    public static double FLYWHEEL_IDLE_TPS = 150; // TODO: tune

    // How long (seconds) to run the transfer motors to fire one ball
    public static double SHOOT_DURATION_SECONDS = 0.5; // TODO: tune

    public static double TICKS_PER_REV = 112; // 28 PPR * 4 = 112

    // Hood positions
    public static double CLOSE_HOOD = 0; // TODO: need to find
    public static double FAR_HOOD = 0; // TODO: need to find

    // Turret positions(direction turret is facing):
    public static double INIT = 0; // should be forward? idk

    // Flywheel speeds (remove if using interpolation)
    public static long CLOSE_SHOT = 0; // TODO: need to find
    public static long FAR_SHOT = 0; // TODO: need to find
}
