package org.firstinspires.ftc.teamcode.robot.commands.intake;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.IntakeRollers;

import java.util.function.BooleanSupplier;

public class BackIntakeCommand extends CommandBase {

    private final IntakeRollers intake;
    private final BooleanSupplier flywheelReachedSpeedSupplier;

    public BackIntakeCommand(IntakeRollers intake, BooleanSupplier flywheelReachedSpeedSupplier) {
        this.intake = intake;
        this.flywheelReachedSpeedSupplier = flywheelReachedSpeedSupplier;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        applyPower();
    }

    @Override
    public void execute() {
        applyPower();
    }

    private void applyPower() {
        if (!flywheelReachedSpeedSupplier.getAsBoolean()) {
            intake.setPower(IntakeRollerConstants.OFF, IntakeRollerConstants.INTAKE_POWER);
        } else {
            intake.setPower(IntakeRollerConstants.HELP_POWER, IntakeRollerConstants.INTAKE_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
