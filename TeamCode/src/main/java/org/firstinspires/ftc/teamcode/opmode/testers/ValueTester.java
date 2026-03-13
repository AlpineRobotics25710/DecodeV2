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
    public static double transferFrontPower = 0.0;// needs to be tested
    public static double transferBackPower = 0.0;// needs to be tested
    public static double FrontIntakePower = 0.0;
    public static double BackIntakePower = 0.0;
    public static double HoodPos = 0.0;
    public static double FlyWheelTargetTPS = 0.0;
    public static double TurretPower = 0.0;


    @Override
    public void runOpMode() {
        AlpineRobot robot = new AlpineRobot(hardwareMap);
        CommonTelemetry.init(telemetry);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            robot.turret.setHoodPosition(HoodPos);

            robot.frontIntake.setPower(FrontIntakePower);
            robot.backIntake.setPower(BackIntakePower);
            robot.turret.setFlywheelTargetTPS(FlyWheelTargetTPS);

            robot.transfer.setFrontPower(transferFrontPower);
            robot.transfer.setBackPower(transferBackPower);

            robot.turret.setTurretPower(TurretPower);

            CommonTelemetry.addData("=== TRANSFER ===", "");
            CommonTelemetry.addData("Transfer Power (set)", "Front: " + transferFrontPower + ", Back: " + transferBackPower);
            CommonTelemetry.addData("TransferFront power", robot.transfer.getFrontPower());
            CommonTelemetry.addData("TransferBack  power", robot.transfer.getBackPower());

            CommonTelemetry.addData("=== FRONT INTAKE ===", "");
            CommonTelemetry.addData("Front Intake Power (set)", FrontIntakePower);
            CommonTelemetry.addData("Front Intake power", robot.frontIntake.getPower());

            CommonTelemetry.addData("=== BACK INTAKE ===", "");
            CommonTelemetry.addData("Back Intake Power (set)", BackIntakePower);
            CommonTelemetry.addData("Back Intake power", robot.backIntake.getPower());

            CommonTelemetry.addData("=== FLYWHEEL ===", "");
            CommonTelemetry.addData("FlyWheel TPS (set)", FlyWheelTargetTPS);
            CommonTelemetry.addData("Flywheel target tps", robot.turret.getFlywheelTargetTPS());
            CommonTelemetry.addData("Flywheel avg velocity", robot.turret.getFlywheelVelocity());

            CommonTelemetry.addData("=== HOOD ===", "");
            CommonTelemetry.addData("Hood Pos (set)", HoodPos);
            CommonTelemetry.addData("Hood Pos (actual)", robot.turret.getHoodPosition());

            CommonTelemetry.addData("=== TURRET ===", "");
            CommonTelemetry.addData("Turret Power (set)", TurretPower);
            CommonTelemetry.addData("TurretFront power", robot.turret.getTurretFrontPower());
            CommonTelemetry.addData("TurretBack  power", robot.turret.getTurretBackPower());

            CommonTelemetry.update();
            CommandScheduler.getInstance().run();
        }
    }
}



