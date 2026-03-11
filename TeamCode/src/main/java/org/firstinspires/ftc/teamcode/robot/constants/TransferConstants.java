package org.firstinspires.ftc.teamcode.robot.constants;

public class TransferConstants {
    // Transfer power settings
    public static double ON = 1.0;
    public static double OFF = 0.0;
    
    // Biased transfer - one side runs faster to prioritize that side's balls
    public static double PRIMARY_SIDE_POWER = 1.0;    // TransferSide with priority
    public static double SECONDARY_SIDE_POWER = 0.6;  // Slower side
    
    // Time between feeding successive balls in burst mode (seconds)
    public static double BURST_FEED_INTERVAL = 0.4;
    
    // Time to run transfer to feed one ball (seconds)
    public static double SINGLE_BALL_FEED_TIME = 0.35;
}
