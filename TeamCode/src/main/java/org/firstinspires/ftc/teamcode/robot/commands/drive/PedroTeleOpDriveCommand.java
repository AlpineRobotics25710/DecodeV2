package org.firstinspires.ftc.teamcode.robot.commands.drive;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.robot.subsystem.Drivetrain;

public class PedroTeleOpDriveCommand extends CommandBase {

    private final Drivetrain drivetrain;
    private final GamepadEx driver;

    public PedroTeleOpDriveCommand(Drivetrain drivetrain, GamepadEx driver) {
        this.drivetrain = drivetrain;
        this.driver = driver;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double forward = driver.getLeftY();
        double strafe = driver.getLeftX();
        double turn = -driver.getRightX();

        double slow = driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

        double transScale = Math.max(0.5, 1 - slow);
        double turnScale = Math.max(0.5, 1 - slow);

        drivetrain.teleOpDrive(
                forward * transScale,
                strafe * transScale,
                turn * turnScale
        );
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}