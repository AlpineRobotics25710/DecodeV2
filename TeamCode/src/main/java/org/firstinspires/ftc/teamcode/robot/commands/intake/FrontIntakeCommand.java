package org.firstinspires.ftc.teamcode.robot.commands.intake;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.IntakeRollers;

import java.util.function.BooleanSupplier;

public class FrontIntakeCommand extends CommandBase {

    private final IntakeRollers intake;
    private final BooleanSupplier flywheelReachedSpeedSupplier;

    public FrontIntakeCommand(IntakeRollers intake, BooleanSupplier flywheelReachedSpeedSupplier) {
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
            intake.setPower(IntakeRollerConstants.INTAKE_POWER, IntakeRollerConstants.OFF);
        } else {
            intake.setPower(IntakeRollerConstants.INTAKE_POWER, IntakeRollerConstants.HELP_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
