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
    private double commandedTurretAngleDeg = 0;
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

        // Initialize from the configured servo position and clamp to the allowed turret range.
        commandedTurretAngleDeg = clampTurretAngle(servoPositionToTurretAngle(turret.getPosition()));
        applyTurretAngle(commandedTurretAngleDeg);
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

    public double getTurretAngleDeg() {
        return commandedTurretAngleDeg;
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

    public void setTurretAngleDeg(double targetAngleDeg) {
        hasAlignmentTarget = false;
        inRecovery = false;
        turretPIDF.reset();
        turretPIDF.clearTotalError();
        commandedTurretAngleDeg = clampTurretAngle(targetAngleDeg);
        applyTurretAngle(commandedTurretAngleDeg);
    }

    private void nudgeTurretAngleDeg(double deltaDeg) {
        commandedTurretAngleDeg = clampTurretAngle(commandedTurretAngleDeg + deltaDeg);
        applyTurretAngle(commandedTurretAngleDeg);
    }

    private double getTurretAbsLimitDeg() {
        return Math.min(TurretConstants.TURRET_SOFT_LIMIT_DEG, TurretConstants.TURRET_PHYSICAL_LIMIT_DEG);
    }

    private double clampTurretAngle(double turretAngleDeg) {
        double limit = getTurretAbsLimitDeg();
        return MathUtils.clamp(turretAngleDeg, -limit, limit);
    }

    private double turretAngleToServoPosition(double turretAngleDeg) {
        double servoDeg = TurretConstants.TURRET_SERVO_CENTER_DEG
                + (turretAngleDeg * TurretConstants.TURRET_SERVO_DEG_PER_TURRET_DEG);
        return MathUtils.clamp(servoDeg / TurretConstants.TURRET_SERVO_MAX_DEG, 0, 1);
    }

    private double servoPositionToTurretAngle(double servoPosition) {
        double servoDeg = MathUtils.clamp(servoPosition, 0, 1) * TurretConstants.TURRET_SERVO_MAX_DEG;
        return (servoDeg - TurretConstants.TURRET_SERVO_CENTER_DEG)
                / TurretConstants.TURRET_SERVO_DEG_PER_TURRET_DEG;
    }

    private void applyTurretAngle(double turretAngleDeg) {
        turret.setPosition(turretAngleToServoPosition(turretAngleDeg));
    }

    @Override
    public void periodic() {
        double currentTPS = (flyLeft.getVelocity() + flyRight.getVelocity()) / 2.0;
        double flywheelOutput = flywheelPIDF.calculate(currentTPS, targetTPS);
        flywheelOutput = MathUtils.clamp(flywheelOutput, -1, 1);
        setFlywheelPower(flywheelOutput);

        if (!hasAlignmentTarget) {
            if (inRecovery && lastKnownErrorSign != 0) {
                // Creep a fixed angle step back toward where the tag was last seen.
                nudgeTurretAngleDeg(lastKnownErrorSign * TurretConstants.TURRET_RECOVERY_STEP_DEG);
            }
            return;
        }

        double turretStepDeg = turretPIDF.calculate(alignmentErrorDeg, 0);
        turretStepDeg = MathUtils.clamp(
                turretStepDeg,
                -TurretConstants.TURRET_MAX_PID_STEP_DEG,
                TurretConstants.TURRET_MAX_PID_STEP_DEG
        );
        if (Math.abs(alignmentErrorDeg) <= TurretConstants.TURRET_TX_TOLERANCE_DEG || turretPIDF.atSetPoint()) {
            turretStepDeg = 0;
        }

        nudgeTurretAngleDeg(turretStepDeg);
    }
}