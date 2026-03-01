package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    private final DcMotorEx fl, fr, bl, br;
    private final Follower follower;

    private boolean robotCentric = true;

    public Drivetrain(
            DcMotorEx fl,
            DcMotorEx fr,
            DcMotorEx bl,
            DcMotorEx br,
            Follower follower
    ) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
        this.follower = follower;
    }

    public void teleOpDrive(double forward, double strafe, double turn) {
        follower.setTeleOpDrive(forward, strafe, turn, robotCentric);
    }

    public void stop() {
        follower.setTeleOpDrive(0, 0, 0, robotCentric);
    }

    public void setRobotCentric(boolean robotCentric) {
        this.robotCentric = robotCentric;
    }

    public Follower getFollower() {
        return follower;
    }

    @Override
    public void periodic() {
        follower.update();
    }
}