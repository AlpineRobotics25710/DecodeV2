package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants;

public class IntakeRollers extends SubsystemBase {

    private final DcMotor front, back;

    public IntakeRollers(DcMotor front, DcMotor back) {
        this.front = front;
        this.back = back;
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void stop() {
        setPower(IntakeRollerConstants.OFF, IntakeRollerConstants.OFF);
    }

    public double getPower() {
        return (front.getPower() + back.getPower()) / 2.0;
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
