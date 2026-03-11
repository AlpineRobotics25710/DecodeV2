package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.TransferConstants;

public class Transfer extends SubsystemBase {

    private final DcMotorEx left, right;

    public Transfer(DcMotorEx left, DcMotorEx right) {
        this.left = left;
        this.right = right;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void on() {
        left.setPower(TransferConstants.ON);
        right.setPower(TransferConstants.ON);
    }

    public void off() {
        left.setPower(TransferConstants.OFF);
        right.setPower(TransferConstants.OFF);
    }
}
