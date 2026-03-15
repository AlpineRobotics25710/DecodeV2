package org.firstinspires.ftc.teamcode.robot;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.*;
import com.seattlesolvers.solverslib.command.CommandScheduler;
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

    //public final Limelight3A limelight;
    public final Follower follower;

    /** Default to blue alliance **/
    public AlpineRobot(HardwareMap hardwareMap) {
        this(hardwareMap, Alliance.BLUE);
    }

    public AlpineRobot(HardwareMap hardwareMap, Alliance alliance) {
        follower = Constants.createFollower(hardwareMap);

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
        flyRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flyLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        turret = new Turret(turretServo, flyLeft, flyRight);

        // Limelight init
        /*
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(2);
        limelight.start();
         */
        //turret.setDefaultCommand(new AlignTurretToGoalCommand(turret, limelight, follower, alliance));

        // Manual Bulk Caching
        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.AUTO);
    }

    public double getDistanceFromGoal() {
        return follower.getPose().distanceFrom(TurretConstants.GOAL_POSE);
    }
}