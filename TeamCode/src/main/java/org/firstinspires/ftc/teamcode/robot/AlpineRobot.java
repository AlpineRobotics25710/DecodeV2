package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.*;
import com.seattlesolvers.solverslib.command.Robot;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.robot.commands.outtake.AlignTurretToGoalCommand;
import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;
import org.firstinspires.ftc.teamcode.robot.constants.enums.Alliance;
import org.firstinspires.ftc.teamcode.robot.subsystem.*;

public class AlpineRobot extends Robot {

    public final Drivetrain drivetrain;
    public final IntakeRollers frontIntake;
    public final IntakeRollers backIntake;
    public final Transfer transfer;
    public final Turret turret;

    public final Limelight3A limelight;
    public final Follower follower;

    /** Default to blue alliance **/
    public AlpineRobot(HardwareMap hardwareMap) {
        this(hardwareMap, Alliance.BLUE);
    }

    public AlpineRobot(HardwareMap hardwareMap, Alliance alliance) {
        follower = Constants.createFollower(hardwareMap);

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

        // Reverse any motors and stuff here
        transferLeft.setDirection(DcMotorEx.Direction.REVERSE);
        transferRight.setDirection(DcMotorEx.Direction.FORWARD);
        transferLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        transferRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        transfer = new Transfer(transferLeft, transferRight);

        // Turret
        CRServo turretRight = hardwareMap.get(CRServo.class, "T1");
        CRServo turretLeft = hardwareMap.get(CRServo.class, "T2");
        Servo hood = hardwareMap.get(Servo.class, "Hood");

        DcMotorEx flyLeft = hardwareMap.get(DcMotorEx.class, "FlyLeft");
        DcMotorEx flyRight = hardwareMap.get(DcMotorEx.class, "FlyRight");

        // Reverse any motors and stuff here
        flyRight.setDirection(DcMotorEx.Direction.REVERSE);
        flyLeft.setDirection(DcMotorEx.Direction.FORWARD);

        flyRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flyLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Limelight init
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(2);
        limelight.start();

        // Turret
        turret = new Turret(turretRight, turretLeft, hood, flyLeft, flyRight);
        turret.setDefaultCommand(new AlignTurretToGoalCommand(turret, limelight, follower, alliance));

        // Manual Bulk Caching
        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.AUTO);
    }

    public double getDistanceFromGoal() {
        return follower.getPose().distanceFrom(TurretConstants.GOAL_POSE);
    }
}