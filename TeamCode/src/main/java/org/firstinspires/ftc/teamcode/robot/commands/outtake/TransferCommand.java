package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.constants.TransferConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;

import java.util.function.DoubleSupplier;

public class TransferCommand extends CommandBase {

    private final Transfer transfer;
    private final DoubleSupplier durationSupplier;
    private final boolean useBias;
    private ElapsedTime timer;

    /** Transfer with balanced power for specified duration */
    public TransferCommand(Transfer transfer, double durationSeconds) {
        this(transfer, () -> durationSeconds, false);
    }

    /** Transfer using bias setting from Transfer subsystem for default single ball time */
    public TransferCommand(Transfer transfer) {
        this(transfer, () -> TransferConstants.SINGLE_BALL_FEED_TIME, true);
    }

    /** Transfer with dynamic duration (evaluated at initialize time) */
    public TransferCommand(Transfer transfer, DoubleSupplier durationSupplier, boolean useBias) {
        this.transfer = transfer;
        this.durationSupplier = durationSupplier;
        this.useBias = useBias;
        addRequirements(transfer);
    }

    @Override
    public void initialize() {
        timer = new ElapsedTime();
        if (useBias) {
            transfer.onWithBias();
        } else {
            transfer.on();
        }
    }

    @Override
    public boolean isFinished() {
        return timer.seconds() >= durationSupplier.getAsDouble();
    }

    @Override
    public void end(boolean interrupted) {
        transfer.off();
    }
}
