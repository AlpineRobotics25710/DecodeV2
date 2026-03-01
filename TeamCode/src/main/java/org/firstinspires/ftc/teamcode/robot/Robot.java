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
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.subsystem.IntakeRollers;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;
import org.firstinspires.ftc.teamcode.robot.subsystem.Drivetrain;

import java.util.List;

public class Robot {
    public List<LynxModule> allHubs; // hubs (for bulk caching and stuff)

    public final Drivetrain drivetrain;
    public final IntakeRollers frontIntake;
    public final IntakeRollers backIntake;
    public final Transfer transfer;
    public final Turret turret;

    public final Limelight3A limelight;
    public final GamepadEx driver;


    // Drivetrain motors:
    public static DcMotorEx frontLeftDrive;
    public static DcMotorEx frontRightDrive;
    public static DcMotorEx backLeftDrive;
    public static DcMotorEx backRightDrive;

    // FrontRollers motors/servos:
    public CRServo frontLeftIntake, frontRightIntake;

    // BackRollers motors/servos:
    public CRServo backLeftIntake, backRightIntake;


    // Turret motors/servos:
    public Servo turret1, turret2, hood;
    public DcMotorEx flywheelLeft, flywheelRight;


    // Transfer motors/servos:
    public DcMotorEx transferLeft, transferRight;
    public Servo flickerLeft, flickerRight;
    CRServo middleRoller;

    public Robot(HardwareMap hardwareMap, GamepadEx driver) {

        this.driver = driver;

        // Init follower
        Follower follower = org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower(hardwareMap);
        MultipleTelemetry.addData("follower heading constraint", follower.getConstraints().getHeadingConstraint());

        // Drivetrain motors:
        frontLeftDrive = hardwareMap.get(DcMotorEx.class, "FL");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "FR");
        backLeftDrive = hardwareMap.get(DcMotorEx.class, "BL");
        backRightDrive = hardwareMap.get(DcMotorEx.class, "BR");

        // FrontRollers motors/servos:
        frontLeftIntake = hardwareMap.get(CRServo.class, "FLI");
        frontRightIntake = hardwareMap.get(CRServo.class, "FRI");

        // BackRollers motors/servos:
        backLeftIntake = hardwareMap.get(CRServo.class, "BLI");
        backRightIntake = hardwareMap.get(CRServo.class, "BRI");


        // Turret motors/servos:
        turret1 = hardwareMap.get(Servo.class, "T1");
        turret2 = hardwareMap.get(Servo.class, "T2");
        hood = hardwareMap.get(Servo.class, "Hood");
        flywheelLeft = hardwareMap.get(DcMotorEx.class, "FlyLeft");
        flywheelRight = hardwareMap.get(DcMotorEx.class, "FlyRight");


        // Transfer motors/servos:
        transferLeft = hardwareMap.get(DcMotorEx.class, "TransferLeft");
        transferRight = hardwareMap.get(DcMotorEx.class, "TransferRight");
        flickerLeft = hardwareMap.get(Servo.class, "FlickLeft");
        flickerRight = hardwareMap.get(Servo.class, "FlickRight");
        middleRoller = hardwareMap.get(CRServo.class, "MR");

        drivetrain = new Drivetrain(
                frontLeftDrive,
                frontRightDrive,
                backLeftDrive,
                backRightDrive,
                follower
        );

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

        // Set directions of all motors and servos here
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        frontLeftDrive.setZeroPowerBehavior(BRAKE);
        frontRightDrive.setZeroPowerBehavior(BRAKE);
        backRightDrive.setZeroPowerBehavior(BRAKE);
        backLeftDrive.setZeroPowerBehavior(BRAKE);

        flywheelRight.setDirection(DcMotor.Direction.REVERSE); // reverse one of the flywheel ones

        allHubs = hardwareMap.getAll(LynxModule.class);

        // Bulk caching mode of hubs
        // Bulk reading enabled!
        // AUTO mode will bulk read by default and will redo and clear cache once the exact same read is done again
        // MANUAL mode will bulk read once per loop but needs to be manually cleared
        // Also in opModes only clear ControlHub cache as it is a hardware write
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        // remember to clear cache at the end of opmodes :0

    }
}
