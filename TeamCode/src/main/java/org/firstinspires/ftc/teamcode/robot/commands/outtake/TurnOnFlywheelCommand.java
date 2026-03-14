package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.Interpolator;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;

import java.util.function.DoubleSupplier;

public class TurnOnFlywheelCommand extends CommandBase {

    private final Turret turret;
    private final DoubleSupplier distanceSupplier;

    public TurnOnFlywheelCommand(Turret turret, DoubleSupplier distanceSupplier) {
        this.turret = turret;
        this.distanceSupplier = distanceSupplier;
        addRequirements(turret);
    }

    @Override
    public void initialize() {
        updateFlywheelAndHood();
    }

    @Override
    public void execute() {
        updateFlywheelAndHood();
    }

    private void updateFlywheelAndHood() {
        double dist = distanceSupplier.getAsDouble();
        turret.setHoodPosition(Interpolator.getHoodPos(dist));
        turret.setFlywheelTargetTPS(Interpolator.getFlywheelTPS(dist));
    }
}
