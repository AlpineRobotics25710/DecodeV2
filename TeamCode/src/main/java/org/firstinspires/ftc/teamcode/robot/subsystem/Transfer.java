package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.TransferConstants;
import org.firstinspires.ftc.teamcode.robot.constants.enums.TransferSide;

public class Transfer extends SubsystemBase {

    private final DcMotorEx left, right;
    private TransferSide currentPriority = TransferSide.BALANCED;

    public Transfer(DcMotorEx left, DcMotorEx right) {
        this.left = left;
        this.right = right;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    /** Set which transferSide has priority (runs faster to feed balls first) */
    public void setPriority(TransferSide transferSide) {
        this.currentPriority = transferSide;
    }

    public TransferSide getCurrentPriority() {
        return currentPriority;
    }

    /** Toggle between LEFT and RIGHT priority */
    public void togglePriority() {
        if (currentPriority == TransferSide.LEFT) {
            currentPriority = TransferSide.RIGHT;
        } else {
            currentPriority = TransferSide.LEFT;
        }
    }

    /** Run both transfer motors at full power */
    public void on() {
        left.setPower(TransferConstants.ON);
        right.setPower(TransferConstants.ON);
    }

    /** Run transfer with current side priority bias */
    public void onWithBias() {
        switch (currentPriority) {
            case LEFT:
                onLeftPriority();
                break;
            case RIGHT:
                onRightPriority();
                break;
            case BALANCED:
            default:
                on();
                break;
        }
    }

    /** Run transfer prioritizing left (front) side */
    public void onLeftPriority() {
        setPower(TransferConstants.PRIMARY_SIDE_POWER, TransferConstants.SECONDARY_SIDE_POWER);
    }

    /** Run transfer prioritizing right (back) side */
    public void onRightPriority() {
        setPower(TransferConstants.SECONDARY_SIDE_POWER, TransferConstants.PRIMARY_SIDE_POWER);
    }

    public void off() {
        left.setPower(TransferConstants.OFF);
        right.setPower(TransferConstants.OFF);
    }

    public void setPower(double leftPower, double rightPower) {
        left.setPower(leftPower);
        right.setPower(rightPower);
    }
}
