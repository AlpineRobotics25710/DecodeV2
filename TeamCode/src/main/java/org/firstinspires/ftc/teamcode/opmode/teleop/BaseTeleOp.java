package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.commands.drive.PedroTeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.BackIntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.FrontIntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.outtake.TurnOnFlywheelCommand;

public abstract class BaseTeleOp extends CommandOpMode {

    protected AlpineRobot robot;
    protected GamepadEx driver1;
    protected GamepadEx driver2;

    public abstract void initGamepads();

    public abstract GamepadEx driver();

    public abstract GamepadButton frontIntakeButton();

    public abstract GamepadButton backIntakeButton();

    public abstract GamepadButton flywheelToggle();

    @Override
    public void initialize() {
        initGamepads();

        robot = new AlpineRobot(hardwareMap);
        robot.drivetrain.setDefaultCommand(new PedroTeleOpDriveCommand(robot.drivetrain, driver()));

        frontIntakeButton().whenHeld(new FrontIntakeCommand(robot.intake, () -> robot.turret.isFlywheelAtTargetTPS()));
        backIntakeButton().whenHeld(new BackIntakeCommand(robot.intake, () -> robot.turret.isFlywheelAtTargetTPS()));

        flywheelToggle().whenPressed(new ConditionalCommand(
                new InstantCommand(() -> robot.turret.stopFlywheel(), robot.turret),
                new TurnOnFlywheelCommand(robot.turret, () -> robot.getDistanceFromGoal()),
                () -> robot.turret.isFlywheelOn()
        ));
    }

    @Override
    public void run() {
        super.run();

        // Display telemetry for driver awareness

    }
}
