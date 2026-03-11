package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.constants.TurretConstants;
import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;

public class TransferCommand extends CommandBase {

    private final Transfer transfer;
    private final double durationSeconds;
    private ElapsedTime timer;

    public TransferCommand(Transfer transfer) {
        this(transfer, TurretConstants.SHOOT_DURATION_SECONDS);
    }

    public TransferCommand(Transfer transfer, double durationSeconds) {
        this.transfer = transfer;
        this.durationSeconds = durationSeconds;
        addRequirements(transfer);
    }

    @Override
    public void initialize() {
        timer = new ElapsedTime();
        transfer.on();
    }

    @Override
    public boolean isFinished() {
        return timer.seconds() >= durationSeconds;
    }

    @Override
    public void end(boolean interrupted) {
        transfer.off();
    }
}
