package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.TransferConstants;
import org.firstinspires.ftc.teamcode.robot.constants.enums.TransferSide;

public class Transfer extends SubsystemBase {

    private final DcMotorEx front, back;
    private TransferSide currentPriority = TransferSide.BALANCED;

    public Transfer(DcMotorEx front, DcMotorEx back) {
        this.front = front;
        this.back = back;
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
        front.setPower(TransferConstants.ON);
        back.setPower(TransferConstants.ON);
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

    /** Run transfer prioritizing front (front) side */
    public void onLeftPriority() {
        setPower(TransferConstants.PRIMARY_SIDE_POWER, TransferConstants.SECONDARY_SIDE_POWER);
    }

    /** Run transfer prioritizing back (back) side */
    public void onRightPriority() {
        setPower(TransferConstants.SECONDARY_SIDE_POWER, TransferConstants.PRIMARY_SIDE_POWER);
    }

    public void off() {
        front.setPower(TransferConstants.OFF);
        back.setPower(TransferConstants.OFF);
    }

    public void setFrontPower(double frontPower) {
        front.setPower(frontPower);
    }

    public void setBackPower(double backPower) {
        back.setPower(backPower);
    }

    public void setPower(double frontPower, double backPower) {
        front.setPower(frontPower);
        back.setPower(backPower);
    }

    public double getFrontPower() {
        return front.getPower();
    }

    public double getBackPower() {
        return back.getPower();
    }
}
