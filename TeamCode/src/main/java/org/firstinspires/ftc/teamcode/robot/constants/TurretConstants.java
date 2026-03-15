package org.firstinspires.ftc.teamcode.robot.constants;

import com.pedropathing.geometry.Pose;

public class TurretConstants {

    public static Pose BLUE_GOAL_POSE = new Pose(0, 144);
    public static Pose RED_GOAL_POSE = new Pose(144, 0);
    public static Pose GOAL_POSE = BLUE_GOAL_POSE;

    public static double flywheelKP = 0.0, flywheelKI = 0.0, flywheelKD = 0.0, flywheelKF = 0.0; // MANUAL: tune on robot
    public static double turretKP = 0.0, turretKI = 0.0, turretKD = 0.0, turretKF = 0.0; // MANUAL: tune on robot

    public static double TURRET_TX_TOLERANCE_DEG = 1; // MANUAL: tune on robot
    public static double TURRET_ALIGNMENT_DEADBAND_RAD = Math.toRadians(TURRET_TX_TOLERANCE_DEG); // MANUAL: tune on robot
    public static double TURRET_FORWARD_OFFSET_RAD = 0.0; // MANUAL: calibrate turret "straight ahead" vs robot heading
    public static double TURRET_ALIGNMENT_SIGN = 1.0; // Set to -1 if odometry alignment steers opposite direction

    public static double TURRET_RIGHT_SERVO_POS = 0.0; // MANUAL: calibrate right hard limit
    public static double TURRET_LEFT_SERVO_POS = 0.51; // MANUAL: calibrate left hard limit

    public static double TURRET_MAX_PID_STEP_SERVO_POS = 0.02; // MANUAL: tune on robot
    public static double TURRET_RECOVERY_STEP_SERVO_POS = 0.01; // MANUAL: tune on robot

    public static double FLYWHEEL_TPS_TOLERANCE = 20; // MANUAL: tune on robot

    public static double FLYWHEEL_IDLE_TPS = 150; // MANUAL: tune on robot
}
