package org.firstinspires.ftc.teamcode.opmode.testers;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.CommonTelemetry;

@TeleOp(group = "testers")
@Config
@Configurable
public class TurretTester extends LinearOpMode {

    public static double turretPos = 0.0;
    public static double flywheelPower = 0.0;

    @Override
    public void runOpMode() {
        Servo turret = hardwareMap.get(Servo.class, "Turret");
        DcMotor flyLeft = hardwareMap.get(DcMotor.class, "FlyLeft");
        DcMotor flyRight = hardwareMap.get(DcMotor.class, "FlyRight");
        CommonTelemetry.init(telemetry);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            turret.setPosition(turretPos);
            flyLeft.setPower(flywheelPower);
            flyRight.setPower(flywheelPower);
            flyRight.setDirection(DcMotorSimple.Direction.REVERSE);
            flyLeft.setDirection(DcMotorSimple.Direction.FORWARD);

            CommonTelemetry.addData("turret pos", turretPos);
            CommonTelemetry.addData("flywheel power", flywheelPower);
            CommonTelemetry.update();
        }
    }
}
