package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;

public class Turret extends SubsystemBase {

    private final Servo turret;
    private final DcMotorEx flyLeft, flyRight;
    private final PIDFController flywheelPIDF;
    private final PIDFController turretPIDF;
    private double targetTPS = TurretConstants.FLYWHEEL_IDLE_TPS;
    private boolean shootingTargetActive = false;
    private double alignmentErrorDeg = 0;
    private boolean hasAlignmentTarget = false;
    private double commandedServoPosition = TurretConstants.TURRET_FORWARD_SERVO_POS;
    // Recovery: when tag is lost, creep back in the last known error direction
    private double lastKnownErrorSign = 0;  // +1, -1, or 0 (unknown)
    private boolean inRecovery = false;

    public Turret(Servo turret, DcMotorEx flyLeft, DcMotorEx flyRight) {
        this.turret = turret;
        this.flyLeft = flyLeft;
        this.flyRight = flyRight;

        flywheelPIDF = new PIDFController(
                TurretConstants.flywheelKP,
                TurretConstants.flywheelKI,
                TurretConstants.flywheelKD,
                TurretConstants.flywheelKF
        );
        turretPIDF = new PIDFController(
                TurretConstants.turretKP,
                TurretConstants.turretKI,
                TurretConstants.turretKD,
                TurretConstants.turretKF
        );

        turretPIDF.setTolerance(TurretConstants.TURRET_TX_TOLERANCE_DEG);
        flywheelPIDF.setTolerance(TurretConstants.FLYWHEEL_TPS_TOLERANCE);

        // Initialize from hardware and immediately clamp into the allowed servo motion window.
        commandedServoPosition = clampServoPosition(turret.getPosition());
        applyServoPosition(commandedServoPosition);
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public boolean isFlywheelAtTargetTPS() {
        return shootingTargetActive && flywheelPIDF.atSetPoint();
    }

    public boolean isTurretAligned() {
        return hasAlignmentTarget && turretPIDF.atSetPoint();
    }

    public void setFlywheelPower(double power) {
        flyLeft.setPower(power);
        flyRight.setPower(power);
    }

    public boolean isFlywheelOn() {
        return shootingTargetActive;
    }

    public double getFlywheelVelocity() {
        return (flyLeft.getVelocity() + flyRight.getVelocity()) / 2.0;
    }

    public double getTurretServoPosition() {
        return turret.getPosition();
    }

    public double getCommandedTurretServoPosition() {
        return commandedServoPosition;
    }

    public double getFlywheelTargetTPS() {
        return targetTPS;
    }

    public void setFlywheelTargetTPS(double tps) {
        if (tps <= 0) {
            if (shootingTargetActive) {
                stopFlywheel();
            } else {
                targetTPS = TurretConstants.FLYWHEEL_IDLE_TPS;
            }
            return;
        }

        if (!shootingTargetActive) {
            flywheelPIDF.reset();
            flywheelPIDF.clearTotalError();
        }

        targetTPS = tps;
        shootingTargetActive = true;
    }

    public void stopFlywheel() {
        if (!shootingTargetActive && targetTPS == TurretConstants.FLYWHEEL_IDLE_TPS) {
            return;
        }

        shootingTargetActive = false;
        targetTPS = TurretConstants.FLYWHEEL_IDLE_TPS;
        flywheelPIDF.reset();
        flywheelPIDF.clearTotalError();
    }

    public void setTurretAlignmentError(double errorDeg) {
        alignmentErrorDeg = errorDeg;
        hasAlignmentTarget = true;
        inRecovery = false;
        // Remember which way the error was so we can recover if tag is lost
        if (Math.abs(errorDeg) > TurretConstants.TURRET_TX_TOLERANCE_DEG) {
            lastKnownErrorSign = Math.signum(errorDeg);
        }
    }

    /**
     * Called by AlignTurretToGoalCommand when the tag drops out of view. Enters recovery creep.
     */
    public void onTagLost() {
        hasAlignmentTarget = false;
        alignmentErrorDeg = 0;
        turretPIDF.reset();
        turretPIDF.clearTotalError();
        inRecovery = (lastKnownErrorSign != 0);
    }

    /**
     * Hard stop – call when you want to completely abandon turret alignment.
     */
    public void clearTurretAlignment() {
        hasAlignmentTarget = false;
        alignmentErrorDeg = 0;
        inRecovery = false;
        lastKnownErrorSign = 0;
        turretPIDF.reset();
        turretPIDF.clearTotalError();
    }

    public void setTurretServoPosition(double targetServoPosition) {
        hasAlignmentTarget = false;
        inRecovery = false;
        turretPIDF.reset();
        turretPIDF.clearTotalError();
        commandedServoPosition = clampServoPosition(targetServoPosition);
        applyServoPosition(commandedServoPosition);
    }

    private void nudgeTurretServoPosition(double deltaServoPosition) {
        double boundedStep = boundStepAtEndstops(deltaServoPosition);
        commandedServoPosition = clampServoPosition(commandedServoPosition + boundedStep);
        applyServoPosition(commandedServoPosition);
    }

    private double boundStepAtEndstops(double deltaServoPosition) {
        final double epsilon = 1e-6;

        // Prevent continuing outward at either extreme by redirecting the command inward.
        if (commandedServoPosition <= TurretConstants.TURRET_MIN_SERVO_POS + epsilon && deltaServoPosition < 0) {
            return Math.abs(deltaServoPosition);
        }

        if (commandedServoPosition >= TurretConstants.TURRET_MAX_SERVO_POS - epsilon && deltaServoPosition > 0) {
            return -Math.abs(deltaServoPosition);
        }

        return deltaServoPosition;
    }

    private double clampServoPosition(double servoPosition) {
        return MathUtils.clamp(
                servoPosition,
                TurretConstants.TURRET_MIN_SERVO_POS,
                TurretConstants.TURRET_MAX_SERVO_POS
        );
    }

    private void applyServoPosition(double servoPosition) {
        turret.setPosition(clampServoPosition(servoPosition));
    }

    @Override
    public void periodic() {
        double currentTPS = (flyLeft.getVelocity() + flyRight.getVelocity()) / 2.0;
        double flywheelOutput = flywheelPIDF.calculate(currentTPS, targetTPS);
        flywheelOutput = MathUtils.clamp(flywheelOutput, -1, 1);
        setFlywheelPower(flywheelOutput);

        if (!hasAlignmentTarget) {
            if (inRecovery && lastKnownErrorSign != 0) {
                // Creep a fixed servo-position step back toward where the tag was last seen.
                nudgeTurretServoPosition(lastKnownErrorSign * TurretConstants.TURRET_RECOVERY_STEP_SERVO_POS);
            }
            return;
        }

        double turretStepServoPos = turretPIDF.calculate(alignmentErrorDeg, 0);
        turretStepServoPos = MathUtils.clamp(
                turretStepServoPos,
                -TurretConstants.TURRET_MAX_PID_STEP_SERVO_POS,
                TurretConstants.TURRET_MAX_PID_STEP_SERVO_POS
        );
        if (Math.abs(alignmentErrorDeg) <= TurretConstants.TURRET_TX_TOLERANCE_DEG || turretPIDF.atSetPoint()) {
            turretStepServoPos = 0;
        }

        nudgeTurretServoPosition(turretStepServoPos);
    }
}