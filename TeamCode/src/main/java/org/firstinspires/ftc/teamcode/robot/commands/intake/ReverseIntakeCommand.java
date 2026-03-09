package org.firstinspires.ftc.teamcode.robot.commands.intake;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.IntakeRollers;

public class ReverseIntakeCommand extends InstantCommand {
    private final IntakeRollers frontRollers;
    private final IntakeRollers backRollers;

    public ReverseIntakeCommand(IntakeRollers frontRollers, IntakeRollers backRollers) {
        this.frontRollers = frontRollers;
        this.backRollers = backRollers;
        addRequirements(frontRollers, backRollers);
    }

    @Override
    public void execute() {
        frontRollers.setPower(IntakeRollerConstants.OFF);
        backRollers.setPower(IntakeRollerConstants.OFF);
    }
}
