package org.firstinspires.ftc.teamcode.opmode.testers;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.CommonTelemetry;

@TeleOp(group = "testers")
@Config
@Configurable
public class ValueTester extends LinearOpMode {
    public static double TransferPower = 0.0;// needs to be tested

    public static double FrontIntakePower = 0.0;

    public static double BackIntakePower = 0.0;

    public static double HoodPos = 0.0;

    public static double FlyWheelVelocity = 0.0;

    public static double TurretPower = 0.0;


    @Override
    public void runOpMode() throws InterruptedException {
            DcMotorEx transferLeft = hardwareMap.get(DcMotorEx.class, "TransferLeft");
            DcMotorEx transferRight = hardwareMap.get(DcMotorEx.class, "TransferRight");

            CRServo frontLeftIntake = hardwareMap.get(CRServo.class, "FrontLeftIntake");
            CRServo frontRightIntake = hardwareMap.get(CRServo.class, "FrontRightIntake");

            CRServo backLeftIntake = hardwareMap.get(CRServo.class, "BackLeftIntake");
            CRServo backRightIntake = hardwareMap.get(CRServo.class, "BackRightIntake");

            CRServo turretRight = hardwareMap.get(CRServo.class, "TurretRight");
            CRServo turretLeft = hardwareMap.get(CRServo.class, "TurretLeft");
            Servo hood = hardwareMap.get(Servo.class, "Hood");

            DcMotorEx flyLeft = hardwareMap.get(DcMotorEx.class, "FlyLeft");
            DcMotorEx flyRight = hardwareMap.get(DcMotorEx.class, "FlyRight");

            transferLeft.setDirection(DcMotorEx.Direction.REVERSE);
            transferRight.setDirection(DcMotorEx.Direction.FORWARD);
            transferLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            transferRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            transferLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            transferRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            flyRight.setDirection(DcMotorEx.Direction.REVERSE);
            flyLeft.setDirection(DcMotorEx.Direction.FORWARD);

            flyRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            flyLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


            CommonTelemetry.init(telemetry);

            waitForStart();

            while (!isStopRequested() && opModeIsActive()) {


                hood.setPosition(HoodPos);
                frontLeftIntake.setPower(FrontIntakePower);
                frontRightIntake.setPower(FrontIntakePower);

                backLeftIntake.setPower(BackIntakePower);
                backRightIntake.setPower(BackIntakePower);

                flyLeft.setVelocity(FlyWheelVelocity);
                flyRight.setVelocity(FlyWheelVelocity);

                transferLeft.setVelocity(TransferPower);
                transferRight.setVelocity(TransferPower);

                turretLeft.setPower(TurretPower);
                transferRight.setPower(TurretPower);

                CommonTelemetry.addData("=== TRANSFER ===", "");
                CommonTelemetry.addData("Transfer Power (set)", TransferPower);
                CommonTelemetry.addData("TransferLeft  velocity", transferLeft.getVelocity());
                CommonTelemetry.addData("TransferRight velocity", transferRight.getVelocity());

                CommonTelemetry.addData("=== FRONT INTAKE ===", "");
                CommonTelemetry.addData("Front Intake Power (set)", FrontIntakePower);
                CommonTelemetry.addData("FrontLeft  power", frontLeftIntake.getPower());
                CommonTelemetry.addData("FrontRight power", frontRightIntake.getPower());

                CommonTelemetry.addData("=== BACK INTAKE ===", "");
                CommonTelemetry.addData("Back Intake Power (set)", BackIntakePower);
                CommonTelemetry.addData("BackLeft  power", backLeftIntake.getPower());
                CommonTelemetry.addData("BackRight power", backRightIntake.getPower());

                CommonTelemetry.addData("=== FLYWHEEL ===", "");
                CommonTelemetry.addData("FlyWheel Velocity (set)", FlyWheelVelocity);
                CommonTelemetry.addData("FlyLeft  velocity", flyLeft.getVelocity());
                CommonTelemetry.addData("FlyRight velocity", flyRight.getVelocity());

                CommonTelemetry.addData("=== HOOD ===", "");
                CommonTelemetry.addData("Hood Pos (set)", HoodPos);
                CommonTelemetry.addData("Hood Pos (actual)", hood.getPosition());

                CommonTelemetry.addData("=== TURRET ===", "");
                CommonTelemetry.addData("Turret Power (set)", TurretPower);
                CommonTelemetry.addData("TurretLeft  power", turretLeft.getPower());
                CommonTelemetry.addData("TurretRight power", turretRight.getPower());

                telemetry.update();
            }
        }
    }



