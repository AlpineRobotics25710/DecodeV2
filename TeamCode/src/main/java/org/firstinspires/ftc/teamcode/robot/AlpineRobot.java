package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.*;
import com.seattlesolvers.solverslib.command.Robot;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.robot.subsystem.*;

public class AlpineRobot extends Robot {

    public final Drivetrain drivetrain;
    public final IntakeRollers frontIntake;
    public final IntakeRollers backIntake;
    public final Transfer transfer;
    public final Turret turret;

    public final Limelight3A limelight;

    public AlpineRobot(HardwareMap hardwareMap) {

        // Create follower
        Follower follower = Constants.createFollower(hardwareMap);

        // Drivetrain
        drivetrain = new Drivetrain(follower, true);

        // Intake
        CRServo frontLeftIntake = hardwareMap.get(CRServo.class, "FLI");
        CRServo frontRightIntake = hardwareMap.get(CRServo.class, "FRI");

        CRServo backLeftIntake = hardwareMap.get(CRServo.class, "BLI");
        CRServo backRightIntake = hardwareMap.get(CRServo.class, "BRI");

        frontIntake = new IntakeRollers(frontLeftIntake, frontRightIntake);
        backIntake = new IntakeRollers(backLeftIntake, backRightIntake);

        // Transfer
        DcMotorEx transferLeft = hardwareMap.get(DcMotorEx.class, "TransferLeft");
        DcMotorEx transferRight = hardwareMap.get(DcMotorEx.class, "TransferRight");

        Servo flickLeft = hardwareMap.get(Servo.class, "FlickLeft");
        Servo flickRight = hardwareMap.get(Servo.class, "FlickRight");

        CRServo middleRoller = hardwareMap.get(CRServo.class, "MR");

        // Reverse any motors and stuff here
        transferLeft.setDirection(DcMotorEx.Direction.REVERSE);
        transferRight.setDirection(DcMotorEx.Direction.FORWARD);

        transfer = new Transfer(
                transferLeft,
                transferRight,
                flickLeft,
                flickRight,
                middleRoller
        );

        // Turret
        Servo turret1 = hardwareMap.get(Servo.class, "T1");
        Servo turret2 = hardwareMap.get(Servo.class, "T2");
        Servo hood = hardwareMap.get(Servo.class, "Hood");

        DcMotorEx flyLeft = hardwareMap.get(DcMotorEx.class, "FlyLeft");
        DcMotorEx flyRight = hardwareMap.get(DcMotorEx.class, "FlyRight");

        // Reverse any motors and stuff here
        flyRight.setDirection(DcMotorEx.Direction.REVERSE);
        flyLeft.setDirection(DcMotorEx.Direction.FORWARD);

        flyRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flyLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        turret = new Turret(turret1, turret2, hood, flyLeft, flyRight);

        // Limelight init
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(2);
        limelight.start();

        // Manual Bulk Caching
        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.MANUAL);
    }
}