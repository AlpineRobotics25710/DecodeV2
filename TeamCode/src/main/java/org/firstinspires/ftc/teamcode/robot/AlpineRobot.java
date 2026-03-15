package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
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
    public final IntakeRollers intake;
    public final Turret turret;

    public final Follower follower;
    private final Pose goalPose;

    /** Default to blue alliance **/
    public AlpineRobot(HardwareMap hardwareMap) {
        this(hardwareMap, Alliance.BLUE);
    }

    public AlpineRobot(HardwareMap hardwareMap, Alliance alliance) {
        follower = Constants.createFollower(hardwareMap);
        goalPose = alliance == Alliance.BLUE ? TurretConstants.BLUE_GOAL_POSE : TurretConstants.RED_GOAL_POSE;

        // Drivetrain
        drivetrain = new Drivetrain(follower, true);

        // Intake + Transfer are now combined: one motor per side
        DcMotor intakeFront = hardwareMap.get(DcMotor.class, "IntakeFront");
        DcMotor intakeBack = hardwareMap.get(DcMotor.class, "IntakeBack");

        // Reverse any motors and stuff here
        intakeFront.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeBack.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        intake = new IntakeRollers(intakeFront, intakeBack);

        // Turret
        Servo turretServo = hardwareMap.get(Servo.class, "Turret");

        DcMotorEx flyLeft = hardwareMap.get(DcMotorEx.class, "FlyLeft");
        DcMotorEx flyRight = hardwareMap.get(DcMotorEx.class, "FlyRight");

        // Reverse any motors and stuff here
        flyRight.setDirection(DcMotorEx.Direction.REVERSE);
        flyLeft.setDirection(DcMotorEx.Direction.FORWARD);
        flyRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flyLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        turret = new Turret(turretServo, flyLeft, flyRight);
        //turret.setDefaultCommand(new AlignTurretToGoalCommand(turret, follower, alliance));

        // Manual Bulk Caching
        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.AUTO);
    }

    public double getDistanceFromGoal() {
        return follower.getPose().distanceFrom(goalPose);
    }
}