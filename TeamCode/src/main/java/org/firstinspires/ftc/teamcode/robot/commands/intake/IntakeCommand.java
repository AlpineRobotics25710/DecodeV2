package org.firstinspires.ftc.teamcode.robot.commands.intake;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.IntakeRollers;

public class IntakeCommand extends CommandBase {
    private final IntakeRollers frontRollers;
    private final IntakeRollers backRollers;

    public IntakeCommand(IntakeRollers frontRollers, IntakeRollers backRollers) {
        this.frontRollers = frontRollers;
        this.backRollers = backRollers;
        addRequirements(frontRollers, backRollers);
    }

    @Override
    public void initialize() {
        frontRollers.setPower(IntakeRollerConstants.INTAKE);
        backRollers.setPower(IntakeRollerConstants.INTAKE);
    }

    @Override
    public void end(boolean interrupted) {
        frontRollers.setPower(IntakeRollerConstants.OFF);
        backRollers.setPower(IntakeRollerConstants.OFF);
    }
}
