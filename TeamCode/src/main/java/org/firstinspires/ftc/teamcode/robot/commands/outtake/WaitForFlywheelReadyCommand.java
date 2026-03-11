package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.Interpolator;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;

import java.util.function.DoubleSupplier;

/**
 * Configures the flywheel/hood based on distance and waits until
 * the flywheel is at target speed AND turret is aligned.
 */
public class WaitForFlywheelReadyCommand extends CommandBase {

    private final Turret turret;
    private final DoubleSupplier distanceSupplier;

    public WaitForFlywheelReadyCommand(Turret turret, DoubleSupplier distanceSupplier) {
        this.turret = turret;
        this.distanceSupplier = distanceSupplier;
        // Don't add turret as requirement - AlignTurretToGoalCommand owns it
    }

    @Override
    public void initialize() {
        double dist = distanceSupplier.getAsDouble();
        turret.setHood(Interpolator.getHoodPos(dist));
        turret.setFlywheelTargetTPS(Interpolator.getFlywheelTPS(dist));
    }

    @Override
    public boolean isFinished() {
        return turret.isFlywheelAtTargetTPS() && turret.isTurretAligned();
    }
}

