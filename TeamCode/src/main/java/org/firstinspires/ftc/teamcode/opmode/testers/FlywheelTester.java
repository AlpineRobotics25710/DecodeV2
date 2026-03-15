package org.firstinspires.ftc.teamcode.opmode.testers;

import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.turretKD;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.turretKF;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.turretKI;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.turretKP;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandScheduler;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.CommonTelemetry;

@TeleOp(group = "testers")
@Config
@Configurable
public class FlywheelTester extends LinearOpMode {

    public static double targetTPS = 0.0;
    public static double kp = turretKP, ki = turretKI, kd = turretKD, kf = turretKF;

    @Override
    public void runOpMode() {
        AlpineRobot robot = new AlpineRobot(hardwareMap);
        CommonTelemetry.init(telemetry);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            robot.turret.setFlywheelTargetTPS(targetTPS);
            robot.turret.setFlywheelPIDF(kp, ki, kd, kf);

            CommonTelemetry.addData("Target TPS", targetTPS);
            CommonTelemetry.addData("Actual TPS", robot.turret.getFlywheelVelocity());
            CommonTelemetry.addData("At Target?", robot.turret.isFlywheelAtTargetTPS());
            CommonTelemetry.update();
            CommandScheduler.getInstance().run();
        }
    }
}