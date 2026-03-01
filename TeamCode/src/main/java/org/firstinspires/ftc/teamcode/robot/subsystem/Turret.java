package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;

public class Turret extends SubsystemBase {
    Servo rotate1, rotate2, hood;

    DcMotorEx flywheel1, flywheel2;

    public Turret(Servo r1, Servo r2, Servo h, DcMotorEx f1, DcMotorEx f2) {
        rotate1 = r1;
        rotate2 = r2;
        hood = h;
        flywheel1 = f1;
        flywheel2 = f2;
    }

    /**
     * Sets hood to the close shooting position.
     */
    public void setHoodClose() {
        hood.setPosition(TurretConstants.CLOSE_HOOD);
    }

    /**
     * Sets hood to the far shooting servo position.
     */
    public void setHoodFar() {
        hood.setPosition(TurretConstants.FAR_HOOD);
    }

}
