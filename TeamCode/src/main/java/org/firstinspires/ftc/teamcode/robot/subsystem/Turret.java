package org.firstinspires.ftc.teamcode.robot.subsystem;

import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.FLYWHEEL_IDLE_TPS;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.flywheelKD;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.flywheelKF;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.flywheelKI;
import static org.firstinspires.ftc.teamcode.robot.constants.TurretConstants.flywheelKP;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.Subsystem;

import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;

public class Turret implements Subsystem {

    private final Servo turretServo;
    private final DcMotorEx flyRight, flyLeft;

    private double targetTPS = 0.0;
    private double prevTargetTPS = targetTPS;

    public Turret(Servo turretServo, DcMotorEx flyRight, DcMotorEx flyLeft) {
        this.turretServo = turretServo;
        this.flyRight = flyRight;
        this.flyLeft = flyLeft;
        setFlywheelPIDF(flywheelKP, flywheelKI, flywheelKD, flywheelKF);
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public void seFlywheelPower(double power){
        flyRight.setPower(power);
        flyLeft.setPower(power);
    }

    public double getFlywheelTargetTPS() {
        return targetTPS;
    }

    public void setFlywheelTargetTPS(double tps) {
        if (tps == targetTPS) return;
        if (tps == 0.0) this.targetTPS = FLYWHEEL_IDLE_TPS;
        else this.targetTPS = tps;
        setFlywheelVelocity(targetTPS);
    }

    public boolean isFlywheelAtTargetTPS() {
        return Math.abs(getFlywheelVelocity() - targetTPS) < TurretConstants.FLYWHEEL_TPS_TOLERANCE;
    }

    public double getFlywheelVelocity() {
        return (flyRight.getVelocity() + flyLeft.getVelocity()) / 2.0;
    }

    public void setFlywheelVelocity(double targetTPS) {
        flyRight.setVelocity(targetTPS);
        flyLeft.setVelocity(targetTPS);
    }

    public void setFlywheelPIDF(double kp, double ki, double kd, double kf) {
        flyRight.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(kp, ki, kd, kf));
        flyLeft.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(kp, ki, kd, kf));
    }

    public boolean isFlywheelOn() {
        return targetTPS != 0 && targetTPS != FLYWHEEL_IDLE_TPS;
    }

    public void setTurretPos(double pos) {
        turretServo.setPosition(pos);
    }

    /*@Override
    public void periodic() {
        if (targetTPS != prevTargetTPS) {
            setFlywheelVelocity(targetTPS);
            prevTargetTPS = targetTPS;
        }
    }*/
}
