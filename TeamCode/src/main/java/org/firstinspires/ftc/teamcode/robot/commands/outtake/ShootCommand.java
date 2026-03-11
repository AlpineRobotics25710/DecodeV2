package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.robot.BallQueue;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;

import java.util.function.DoubleSupplier;

/**
 * Shoots a single ball:
 * 1. Waits for flywheel at speed AND turret aligned
 * 2. Feeds the ball using TransferCommand with bias
 * 3. Decrements the ball queue (if provided)
 * 4. Stops the flywheel
 */
public class ShootCommand extends SequentialCommandGroup {

    public ShootCommand(Turret turret, Transfer transfer, DoubleSupplier distanceSupplier, BallQueue ballQueue) {
        addCommands(
            // Step 1: Wait for flywheel ready and turret aligned
            new WaitForFlywheelReadyCommand(turret, distanceSupplier),

            // Step 2: Feed the ball with bias support
            new TransferCommand(transfer),

            // Step 3: Decrement queue if provided
            new InstantCommand(() -> {
                if (ballQueue != null) {
                    ballQueue.dequeue();
                }
            }),

            // Step 4: Stop flywheel
            new InstantCommand(turret::stopFlywheel)
        );
    }

    /** Constructor without ball queue (for single shots) */
    public ShootCommand(Turret turret, Transfer transfer, DoubleSupplier distanceSupplier) {
        this(turret, transfer, distanceSupplier, null);
    }
}
