package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.seattlesolvers.solverslib.command.FunctionalCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.robot.Interpolator;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;

import java.util.function.DoubleSupplier;

public class ShootCommand extends SequentialCommandGroup {

    private final Turret turret;

    /**
     * @param distanceSupplier evaluated lazily when the command starts (i.e. at button-press
     *                         time), so the distance reflects the robot's actual position then,
     *                         not when the command object was constructed.
     */
    public ShootCommand(Turret turret, Transfer transfer, DoubleSupplier distanceSupplier) {
        this.turret = turret;

        addCommands(
            // Step 1 – configure hood/flywheel, then wait for both to be ready.
            // No subsystem requirement: Turret's default AlignTurretToGoalCommand stays active.
            new FunctionalCommand(
                () -> {
                    double dist = distanceSupplier.getAsDouble();
                    turret.setHood(Interpolator.getHoodPos(dist));
                    turret.setFlywheelTargetTPS(Interpolator.getFlywheelTPS(dist));
                },
                () -> {},           // execute: Turret.periodic() runs the PIDF loops
                interrupted -> {},  // end: nothing to clean up here
                () -> turret.isFlywheelAtTargetTPS() && turret.isTurretAligned()
            ),

            // Step 2 – feed the ball. TransferCommand owns the Transfer requirement and
            // handles on/off timing internally.
            new TransferCommand(transfer)
        );
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        turret.stopFlywheel();
    }
}
