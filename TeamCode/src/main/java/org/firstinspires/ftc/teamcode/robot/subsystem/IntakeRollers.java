package org.firstinspires.ftc.teamcode.robot.subsystem;

import static org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants.INTAKE;
import static org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants.OFF;
import static org.firstinspires.ftc.teamcode.robot.constants.IntakeRollerConstants.OUTTAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class IntakeRollers extends SubsystemBase {
    CRServo leftServo, rightServo;

    public IntakeRollers(CRServo LS, CRServo RS) {
        leftServo = LS;
        rightServo = RS;
    }

    /**
     * Turns on rollers to intake balls.
     */
    public void setForward() {
        leftServo.setPower(OUTTAKE);
    }

    /**
     * Turns on rollers to outtake balls.
     */
    public void setReverse() {
        leftServo.setPower(INTAKE);
    }

    /**
     * Turns off rollers.
     */
    public void setOff() {
        leftServo.setPower(OFF);
    }
}
