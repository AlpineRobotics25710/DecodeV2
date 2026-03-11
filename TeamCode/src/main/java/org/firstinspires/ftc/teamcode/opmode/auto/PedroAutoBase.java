package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.constants.enums.Alliance;

import java.util.LinkedList;

public abstract class PedroAutoBase extends CommandOpMode {

    protected AlpineRobot robot;
    protected LinkedList<Object> allPaths;


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

    protected void addPath(Object path) {
        allPaths.add(path);
    }

    protected abstract Pose getStartPose();

    protected abstract void allianceSetup(Alliance alliance);

    protected abstract Pose getEndPose();

    protected abstract void buildPaths();
}