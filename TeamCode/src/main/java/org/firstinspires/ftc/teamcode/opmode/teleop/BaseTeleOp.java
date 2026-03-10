package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.commands.drive.PedroTeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.IntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.ReverseIntakeCommand;

public abstract class BaseTeleOp extends CommandOpMode {

    protected AlpineRobot robot;
    protected GamepadEx driver1;
    protected GamepadEx driver2;

    public abstract void initGamepads();

    public abstract GamepadEx driver();

    public abstract GamepadButton intakeButton();

    public abstract GamepadButton reverseIntakeButton();

    @Override
    public void initialize() {
        initGamepads();

        robot = new AlpineRobot(hardwareMap);
        robot.drivetrain.setDefaultCommand(new PedroTeleOpDriveCommand(robot.drivetrain, driver()));

        // Use whileHeld for a cleaner implementation
        intakeButton().whenHeld(new IntakeCommand(robot.frontIntake, robot.backIntake));
        reverseIntakeButton().whenHeld(new ReverseIntakeCommand(robot.frontIntake, robot.backIntake));
    }
}
