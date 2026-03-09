package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;

public class Turret extends SubsystemBase {

    private final CRServo turretLeft, turretRight;
    private final Servo hood;
    private final DcMotorEx flyLeft, flyRight;

    private double targetTPS = 0;
    private double alignmentErrorDeg = 0;
    private boolean hasAlignmentTarget = false;

    // Recovery: when tag is lost, creep back in the last known error direction
    private double lastKnownErrorSign = 0;  // +1, -1, or 0 (unknown)
    private boolean inRecovery = false;

    private final PIDFController flywheelPIDF;
    private final PIDFController turretPIDF;

    public Turret(CRServo turret1, CRServo turret2, Servo hood, DcMotorEx flyLeft, DcMotorEx flyRight) {
        this.turretLeft = turret1;
        this.turretRight = turret2;
        this.hood = hood;
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
    }

    public void setTurretPower(double power) {
        double clampedPower = MathUtils.clamp(power, -TurretConstants.TURRET_MAX_POWER, TurretConstants.TURRET_MAX_POWER);
        turretLeft.setPower(clampedPower);
        turretRight.setPower(clampedPower);
    }

    public void setHood(double pos) {
        hood.setPosition(pos);
    }

    public void setTargetTPS(double tps) {
        targetTPS = tps;
    }

    public void stopFlywheel() {
        targetTPS = 0;
        flyLeft.setPower(0);
        flyRight.setPower(0);
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

    /** Called by AlignTurretToGoalCommand when the tag drops out of view. Enters recovery creep. */
    public void onTagLost() {
        hasAlignmentTarget = false;
        alignmentErrorDeg = 0;
        turretPIDF.reset();
        turretPIDF.clearTotalError();
        inRecovery = (lastKnownErrorSign != 0);
    }

    /** Hard stop – call when you want to completely abandon turret alignment. */
    public void clearTurretAlignment() {
        hasAlignmentTarget = false;
        alignmentErrorDeg = 0;
        inRecovery = false;
        lastKnownErrorSign = 0;
        turretPIDF.reset();
        turretPIDF.clearTotalError();
        setTurretPower(0);
    }

    @Override
    public void periodic() {
        double currentTPS = (flyLeft.getVelocity() + flyRight.getVelocity()) / 2.0;
        double flywheelOutput = flywheelPIDF.calculate(currentTPS, targetTPS);
        flywheelOutput = MathUtils.clamp(flywheelOutput, -1, 1);

        flyLeft.setPower(flywheelOutput);
        flyRight.setPower(flywheelOutput);

        if (!hasAlignmentTarget) {
            if (inRecovery && lastKnownErrorSign != 0) {
                // Creep back in the direction the tag was last seen
                setTurretPower(lastKnownErrorSign * TurretConstants.TURRET_RECOVERY_POWER);
            } else {
                setTurretPower(0);
            }
            return;
        }

        double turretOutput = turretPIDF.calculate(alignmentErrorDeg, 0);
        if (Math.abs(alignmentErrorDeg) <= TurretConstants.TURRET_TX_TOLERANCE_DEG) {
            turretOutput = 0;
        }

        setTurretPower(turretOutput);
    }
}