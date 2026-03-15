package org.firstinspires.ftc.teamcode.opmode.testers;

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
public class ValueTester extends LinearOpMode {
    public static double hoodPos = 0.0;
    public static double flywheelTargetTPS = 0.0;
    public static double turretPower = 0.0;
    public static double frontIntakePower = 0.0;
    public static double backIntakePower = 0.0;


    @Override
    public void runOpMode() {
        AlpineRobot robot = new AlpineRobot(hardwareMap);
        CommonTelemetry.init(telemetry);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            robot.turret.setFlywheelTargetTPS(flywheelTargetTPS);
            robot.turret.setTurretPower(turretPower);

            robot.intake.setPower(frontIntakePower, backIntakePower);

            CommonTelemetry.addData("=== Intake ===", "");
            CommonTelemetry.addData("Intake Power (set)", "Front: " + frontIntakePower + ", Back: " + backIntakePower);
            CommonTelemetry.addData("IntakeFront power", robot.intake.getFrontPower());
            CommonTelemetry.addData("IntakeBack power", robot.intake.getBackPower());

            CommonTelemetry.addData("=== FLYWHEEL ===", "");
            CommonTelemetry.addData("FlyWheel TPS (set)", flywheelTargetTPS);
            CommonTelemetry.addData("Flywheel target tps", robot.turret.getFlywheelTargetTPS());
            CommonTelemetry.addData("Flywheel avg velocity", robot.turret.getFlywheelVelocity());

            CommonTelemetry.addData("=== TURRET ===", "");
            CommonTelemetry.addData("Turret Power (set)", turretPower);
            CommonTelemetry.addData("TurretFront power", robot.turret.getTurretFrontPower());
            CommonTelemetry.addData("TurretBack  power", robot.turret.getTurretBackPower());

            CommonTelemetry.update();
            CommandScheduler.getInstance().run();
        }
    }
}
