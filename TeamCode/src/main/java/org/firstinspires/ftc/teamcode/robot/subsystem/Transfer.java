package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Transfer extends SubsystemBase {

    private final DcMotorEx left, right;
    private final Servo flickLeft, flickRight;
    private final CRServo middle;

    public Transfer(
            DcMotorEx left,
            DcMotorEx right,
            Servo flickLeft,
            Servo flickRight,
            CRServo middle
    ) {
        this.left = left;
        this.right = right;
        this.flickLeft = flickLeft;
        this.flickRight = flickRight;
        this.middle = middle;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void setLeftMotor(double power) {
        left.setPower(power);
    }

    public void setRightMotor(double power) {
        right.setPower(power);
    }

    public void setMiddlePower(double power) {
        middle.setPower(power);
    }

    public void stopMotors() {
        left.setPower(0);
        right.setPower(0);
        middle.setPower(0);
    }

    public void setLeftFlicker(double position) {
        flickLeft.setPosition(position);
    }

    public void setRightFlicker(double position) {
        flickRight.setPosition(position);
    }
}