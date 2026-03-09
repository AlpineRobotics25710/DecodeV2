package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.subsystem.Transfer;

public class TransferCommand extends CommandBase {
    private final Transfer transfer;

    public TransferCommand(Transfer transfer) {
        this.transfer = transfer;
        addRequirements(transfer);
    }

    @Override
    public void execute() {
        transfer.on();
    }
}
