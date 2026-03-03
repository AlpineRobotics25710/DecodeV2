package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.pedropathing.follower.Follower;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    private final Follower follower;
    private boolean robotCentric;

    public Drivetrain(Follower follower, boolean robotCentric) {
        this.follower = follower;
        this.robotCentric = robotCentric;
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

    @Override
    public void periodic() {
        follower.update();
    }
}