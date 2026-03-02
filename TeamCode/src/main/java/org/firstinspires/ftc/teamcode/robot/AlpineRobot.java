package org.firstinspires.ftc.teamcode.robot;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.commands.drive.PedroTeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.robot.subsystem.IntakeRollers;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;
import org.firstinspires.ftc.teamcode.robot.subsystem.Drivetrain;

public class AlpineRobot extends com.seattlesolvers.solverslib.command.Robot {

    // Subsystems
    public static Drivetrain drivetrain;
    public static IntakeRollers frontIntake;
    public static IntakeRollers backIntake;
    public static Transfer transfer;
    public static Turret turret;
    public static Limelight3A limelight;
    public static Follower follower;

    public AlpineRobot(HardwareMap hardwareMap, GamepadEx driver) {
        // Init follower
        follower = org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower(hardwareMap);

        // FrontRollers motors/servos:
        CRServo frontLeftIntake = hardwareMap.get(CRServo.class, "FLI");
        CRServo frontRightIntake = hardwareMap.get(CRServo.class, "FRI");

        // BackRollers motors/servos:
        CRServo backLeftIntake = hardwareMap.get(CRServo.class, "BLI");
        CRServo backRightIntake = hardwareMap.get(CRServo.class, "BRI");

        // Turret motors/servos:
        Servo turret1 = hardwareMap.get(Servo.class, "T1");
        Servo turret2 = hardwareMap.get(Servo.class, "T2");
        Servo hood = hardwareMap.get(Servo.class, "Hood");
        DcMotorEx flywheelLeft = hardwareMap.get(DcMotorEx.class, "FlyLeft");
        DcMotorEx flywheelRight = hardwareMap.get(DcMotorEx.class, "FlyRight");


        // Transfer motors/servos:
        DcMotorEx transferLeft = hardwareMap.get(DcMotorEx.class, "TransferLeft");
        DcMotorEx transferRight = hardwareMap.get(DcMotorEx.class, "TransferRight");
        Servo flickerLeft = hardwareMap.get(Servo.class, "FlickLeft");
        Servo flickerRight = hardwareMap.get(Servo.class, "FlickRight");
        CRServo middleRoller = hardwareMap.get(CRServo.class, "MR");

        drivetrain = new Drivetrain(true); // Robot-centric drive on
        drivetrain.setDefaultCommand(new PedroTeleOpDriveCommand(drivetrain, driver));

        frontIntake = new IntakeRollers(
                frontLeftIntake,
                frontRightIntake
        );

        backIntake = new IntakeRollers(
                backLeftIntake,
                backRightIntake
        );

        transfer = new Transfer(
                transferLeft,
                transferRight,
                flickerLeft,
                flickerRight,
                middleRoller
        );

        turret = new Turret(
                turret1,
                turret2,
                hood,
                flywheelLeft,
                flywheelRight
        );

        // Init LimeLight 3A
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(2);
        limelight.start();

        flywheelRight.setDirection(DcMotor.Direction.REVERSE); // reverse one of the flywheel ones

        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.AUTO);
    }
}
