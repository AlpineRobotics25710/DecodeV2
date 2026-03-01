package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Transfer extends SubsystemBase {
    DcMotorEx transferLeft, transferRight;
    Servo flickerLeft, flickerRight;
    CRServo middleRoller;

    public Transfer(DcMotorEx TL, DcMotorEx TR, Servo FL, Servo FR, CRServo MR) {
        transferLeft = TL;
        transferRight = TR;
        flickerLeft = FL;
        flickerRight = FR;
        middleRoller = MR;
    }
}
