package org.firstinspires.ftc.teamcode.opmode.auto;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;

public abstract class PedroAutoBase extends CommandOpMode {

    protected AlpineRobot robot;

    @Override
    public void initialize() {

        // Create robot
        robot = new AlpineRobot(hardwareMap);

        // Let child build paths
        setupPaths();

        waitForStart();
        if (isStopRequested()) return;

        // Let child schedule commands
        scheduleAuto();
    }

    /**
     * Build Pedro paths here.
     */
    protected abstract void setupPaths();

    /**
     * Schedule autonomous commands here.
     */
    protected abstract void scheduleAuto();
}