package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.CommonTelemetry;
import org.firstinspires.ftc.teamcode.robot.Interpolator;
import org.firstinspires.ftc.teamcode.robot.commands.drive.GmzTeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.BackIntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.FrontIntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.outtake.TurnOnFlywheelCommand;

public abstract class BaseTeleOp extends CommandOpMode {

    protected AlpineRobot robot;
    protected GamepadEx driver1;
    protected GamepadEx driver2;
    private TurnOnFlywheelCommand flywheelOnCommand;

    public abstract void initGamepads();

    public abstract GamepadEx driver();

    public abstract GamepadButton frontIntakeButton();

    public abstract GamepadButton backIntakeButton();

    public abstract GamepadButton flywheelToggle();

    @Override
    public void initialize() {
        CommonTelemetry.init(telemetry);
        initGamepads();

        robot = new AlpineRobot(hardwareMap);
        robot.drivetrain.setDefaultCommand(new GmzTeleOpDriveCommand(hardwareMap.get(DcMotor.class, "FrontLeftDrive"), hardwareMap.get(DcMotor.class, "BackLeftDrive"), hardwareMap.get(DcMotor.class, "FrontRightDrive"), hardwareMap.get(DcMotor.class, "BackRightDrive"), driver(), robot.drivetrain)); // or pedroteleopdrive command
        flywheelOnCommand = new TurnOnFlywheelCommand(robot.turret, () -> robot.getDistanceFromGoal());

        frontIntakeButton().whenHeld(new FrontIntakeCommand(robot.intake, () -> robot.turret.isFlywheelAtTargetTPS()));
        backIntakeButton().whenHeld(new BackIntakeCommand(robot.intake, () -> robot.turret.isFlywheelAtTargetTPS()));

        flywheelToggle().whenPressed(new InstantCommand(() -> {
            if (robot.turret.isFlywheelOn()) {
                flywheelOnCommand.cancel();
            } else {
                flywheelOnCommand.schedule();
            }
        }));
    }

    @Override
    public void run() {
        super.run();

        // Display telemetry for driver awareness
        CommonTelemetry.addData("Distance from goal", robot.getDistanceFromGoal());
        CommonTelemetry.addData("target flywheel tps", robot.turret.getFlywheelTargetTPS());
        CommonTelemetry.addData("actual flywheel tps", robot.turret.getFlywheelVelocity());
        CommonTelemetry.addData("interpolated flywheel tps", Interpolator.getFlywheelTPS(robot.getDistanceFromGoal()));
        CommonTelemetry.addData("turret servo pos (cmd)", robot.turret.getCommandedTurretServoPosition());
        CommonTelemetry.addData("turret servo pos (actual)", robot.turret.getTurretServoPosition());
        CommonTelemetry.addData("front intake power", robot.intake.getFrontPower());
        CommonTelemetry.addData("back intake power", robot.intake.getBackPower());
        CommonTelemetry.update();
    }
}
