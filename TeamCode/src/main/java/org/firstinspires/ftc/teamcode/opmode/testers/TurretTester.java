package org.firstinspires.ftc.teamcode.opmode.testers;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.CommonTelemetry;

@TeleOp(group = "testers")
@Config
@Configurable
public class TurretTester extends LinearOpMode {

    public static double turretPos = 0.0;

    @Override
    public void runOpMode() {
        Servo frontTurret = hardwareMap.get(Servo.class, "TurretFront");
        Servo backTurret = hardwareMap.get(Servo.class, "TurretBack");
        CommonTelemetry.init(telemetry);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            frontTurret.setPosition(turretPos);
            backTurret.setPosition(turretPos);

            CommonTelemetry.addData("turret pos", turretPos);
            CommonTelemetry.update();
        }
    }
}
