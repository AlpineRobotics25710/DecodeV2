package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.commands.drive.PedroTeleOpDriveCommand;

public abstract class BaseTeleOp extends CommandOpMode {

    protected AlpineRobot robot;
    protected GamepadEx driver1;
    protected GamepadEx driver2;

    @Override
    public void initialize() {

        // Wrap gamepads
        driver1 = new GamepadEx(gamepad1);
        driver2 = new GamepadEx(gamepad2);

        // Create robot
        robot = new AlpineRobot(hardwareMap);

        // Set default drivetrain command
        robot.drivetrain.setDefaultCommand(
                new PedroTeleOpDriveCommand(robot.drivetrain, driver1)
        );

        // Let child TeleOp define button bindings
        configureBindings();
    }

    /**
     * Child classes define button mappings here.
     */
    protected abstract void configureBindings();
}