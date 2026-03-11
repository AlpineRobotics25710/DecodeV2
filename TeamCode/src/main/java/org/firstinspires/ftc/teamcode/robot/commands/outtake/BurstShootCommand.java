package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.robot.BallQueue;
import org.firstinspires.ftc.teamcode.robot.constants.TransferConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;

import java.util.function.DoubleSupplier;

/**
 * Burst shooting using command composition.
 * 
 * This command monitors the ball queue and schedules burst sequences
 * when balls are queued. Each burst sequence is a SequentialCommandGroup:
 * 1. WaitForFlywheelReadyCommand - spins up and waits for ready
 * 2. TransferCommand - feeds all queued balls continuously
 * 3. InstantCommand - stops the flywheel
 * 
 * True burst: all queued balls are fed back-to-back once flywheel is at speed.
 */
public class BurstShootCommand extends CommandBase {

    private final Turret turret;
    private final Transfer transfer;
    private final DoubleSupplier distanceSupplier;
    private final BallQueue ballQueue;

    private Command currentBurstSequence = null;

    public BurstShootCommand(Turret turret, Transfer transfer, DoubleSupplier distanceSupplier) {
        this.turret = turret;
        this.transfer = transfer;
        this.distanceSupplier = distanceSupplier;
        this.ballQueue = new BallQueue();
    }

    /** Queue a ball for shooting */
    public void queueBall() {
        ballQueue.queue();
    }

    /** Queue multiple balls */
    public void queueBalls(int count) {
        ballQueue.queue(count);
    }

    /** Get queued ball count */
    public int getBallsQueued() {
        return ballQueue.getCount();
    }

    /** Check if actively shooting */
    public boolean isShooting() {
        return currentBurstSequence != null && currentBurstSequence.isScheduled();
    }

    /** Clear queue and cancel current burst */
    public void clearQueue() {
        ballQueue.clear();
        if (currentBurstSequence != null && currentBurstSequence.isScheduled()) {
            currentBurstSequence.cancel();
        }
        currentBurstSequence = null;
        turret.stopFlywheel();
    }

    /**
     * Creates a burst sequence command group for the given number of balls.
     */
    private Command createBurstSequence(int numBalls) {
        double totalFeedTime = numBalls * TransferConstants.SINGLE_BALL_FEED_TIME;

        return new SequentialCommandGroup(
            // Step 1: Configure flywheel/hood and wait for ready
            new WaitForFlywheelReadyCommand(turret, distanceSupplier),

            // Step 2: Feed all balls continuously
            new TransferCommand(transfer, () -> totalFeedTime, true),

            // Step 3: Stop flywheel when done
            new InstantCommand(turret::stopFlywheel)
        );
    }

    @Override
    public void execute() {
        // Check if current burst finished
        if (currentBurstSequence != null && !currentBurstSequence.isScheduled()) {
            currentBurstSequence = null;
        }

        // If balls are queued and no burst in progress, start a new burst
        if (ballQueue.hasQueuedBalls() && currentBurstSequence == null) {
            // Snapshot and clear the queue
            int ballsToShoot = ballQueue.getCount();
            ballQueue.clear();

            // Create and schedule the burst sequence
            currentBurstSequence = createBurstSequence(ballsToShoot);
            CommandScheduler.getInstance().schedule(currentBurstSequence);
        }
    }

    @Override
    public void end(boolean interrupted) {
        clearQueue();
    }

    @Override
    public boolean isFinished() {
        // Runs continuously
        return false;
    }
}
