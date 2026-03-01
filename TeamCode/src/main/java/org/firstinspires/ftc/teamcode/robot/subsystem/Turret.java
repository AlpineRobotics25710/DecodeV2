package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;

public class Turret extends SubsystemBase {

    private final Servo turret1, turret2, hood;
    private final DcMotorEx flyLeft, flyRight;

    private double targetRPM = 0;
    private double integral = 0;
    private double lastError = 0;
    private long lastTime = System.nanoTime();

    public Turret(
            Servo turret1,
            Servo turret2,
            Servo hood,
            DcMotorEx flyLeft,
            DcMotorEx flyRight
    ) {
        this.turret1 = turret1;
        this.turret2 = turret2;
        this.hood = hood;
        this.flyLeft = flyLeft;
        this.flyRight = flyRight;
    }

    public void setTurretPosition(double pos) {
        turret1.setPosition(pos);
        turret2.setPosition(1.0 - pos);
    }

    public void setHood(double pos) {
        hood.setPosition(pos);
    }

    public void setTargetRPM(double rpm) {
        targetRPM = rpm;
    }

    public void stopFlywheel() {
        targetRPM = 0;
        flyLeft.setPower(0);
        flyRight.setPower(0);
    }

    @Override
    public void periodic() {

        long now = System.nanoTime();
        double dt = (now - lastTime) * 1e-9;
        lastTime = now;

        if (dt <= 0) return;

        double velocity = (flyLeft.getVelocity() + flyRight.getVelocity()) / 2.0;
        double currentRPM = velocity / TurretConstants.TICKS_PER_REV * 60.0;

        double error = targetRPM - currentRPM;

        integral += error * dt;
        double derivative = (error - lastError) / dt;
        lastError = error;

        double output =
                TurretConstants.kP * error +
                        TurretConstants.kI * integral +
                        TurretConstants.kD * derivative +
                        TurretConstants.kF * targetRPM;

        output = Math.max(-1, Math.min(1, output));

        flyLeft.setPower(output);
        flyRight.setPower(output);
    }
}