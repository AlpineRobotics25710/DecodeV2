package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants;

public class IntakeRollers extends SubsystemBase {

    private final CRServo left, right;

    public IntakeRollers(CRServo left, CRServo right) {
        this.left = left;
        this.right = right;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void setPower(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    public void stop() {
        setPower(IntakeRollerConstants.OFF);
    }
}