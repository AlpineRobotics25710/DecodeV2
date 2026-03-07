package org.firstinspires.ftc.teamcode.robot.constants;

public class TurretConstants {

    public static double kP, kI, kD, kF;

    public static  double TICKS_PER_REV = 112; // 28 PPR * 4 = 112
    // Hood positions
    public static double CLOSE_HOOD = 0; // TODO: need to find
    public static double FAR_HOOD = 0; // TODO: need to find

    // Turret positions(direction turret is facing):
    public static double INIT = 0; // should be forward? idk

    // Flywheel speeds (remove if using interpolation)
    public static long CLOSE_SHOT = 0; // TODO: need to find
    public static long FAR_SHOT = 0; // TODO: need to find
}
