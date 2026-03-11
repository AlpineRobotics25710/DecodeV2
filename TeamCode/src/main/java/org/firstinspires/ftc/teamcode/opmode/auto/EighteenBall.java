package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auto.poses.EighteenBallPoses;
import org.firstinspires.ftc.teamcode.robot.constants.enums.Alliance;

@Autonomous(name = "Eighteen Ball", group = "pedro")
public class EighteenBall extends PedroAutoBase {

    private EighteenBallPoses poses;

    @Override
    protected void setupPaths() {

    }

    @Override
    protected void scheduleAuto() {

    }

    @Override
    protected Pose getStartPose() {
        return poses.startPose;
    }

    @Override
    protected void allianceSetup(Alliance alliance) {
        poses = new EighteenBallPoses();
        if (alliance != poses.originalPosesAlliance()) {
            poses.mirror();
        }
    }

    @Override
    protected Pose getEndPose() {
        return poses.parkPose;
    }

    @Override
    protected void buildPaths() {

        // Path 1
        PathChain path1 = follower.pathBuilder()
                .addPath(new BezierLine(
                        poses.startPose,
                        poses.scorePose))
                .setLinearHeadingInterpolation(
                        poses.startPose.getHeading(),
                        poses.scorePose.getHeading())
                .build();
        addPath(path1);

        // Path 2
        PathChain path2 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.scorePose,
                        poses.cpPath2,
                        poses.sample1Pose))
                .setConstantHeadingInterpolation(poses.sample1Pose.getHeading())
                .build();
        addPath(path2);

        // Path 3
        PathChain path3 = follower.pathBuilder()
                .addPath(new BezierLine(
                        poses.sample1Pose,
                        poses.sample2Pose))
                .setConstantHeadingInterpolation(poses.sample2Pose.getHeading())
                .build();
        addPath(path3);

        // Path 4
        PathChain path4 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.sample2Pose,
                        poses.cpPath4,
                        poses.scorePose))
                .setConstantHeadingInterpolation(poses.scorePose.getHeading())
                .build();
        addPath(path4);

        // Path 5
        PathChain path5 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.scorePose,
                        poses.cpPath5,
                        poses.intakePose))
                .setLinearHeadingInterpolation(
                        poses.scorePose.getHeading(),
                        poses.intakePose.getHeading())
                .build();
        addPath(path5);

        // Path 6
        PathChain path6 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.intakePose,
                        poses.cpPath6,
                        poses.scorePose))
                .setLinearHeadingInterpolation(
                        poses.intakePose.getHeading(),
                        poses.scorePose.getHeading())
                .build();
        addPath(path6);

        // Path 7
        PathChain path7 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.scorePose,
                        poses.cpPath7,
                        poses.intakePose))
                .setLinearHeadingInterpolation(
                        poses.scorePose.getHeading(),
                        poses.intakePose.getHeading())
                .build();
        addPath(path7);

        // Path 8
        PathChain path8 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.intakePose,
                        poses.cpPath8,
                        poses.scorePose))
                .setLinearHeadingInterpolation(
                        poses.intakePose.getHeading(),
                        poses.scorePose.getHeading())
                .build();
        addPath(path8);

        // Path 9
        PathChain path9 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.scorePose,
                        poses.cpPath9,
                        poses.intakePose))
                .setLinearHeadingInterpolation(
                        poses.scorePose.getHeading(),
                        poses.intakePose.getHeading())
                .build();
        addPath(path9);

        // Path 10
        PathChain path10 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        poses.intakePose,
                        poses.cpPath10,
                        poses.scorePose))
                .setLinearHeadingInterpolation(
                        poses.intakePose.getHeading(),
                        poses.scorePose.getHeading())
                .build();
        addPath(path10);

        // Path 11
        PathChain path11 = follower.pathBuilder()
                .addPath(new BezierLine(
                        poses.scorePose,
                        poses.midPickupPose))
                .setConstantHeadingInterpolation(poses.midPickupPose.getHeading())
                .build();
        addPath(path11);

        // Path 12
        PathChain path12 = follower.pathBuilder()
                .addPath(new BezierLine(
                        poses.midPickupPose,
                        poses.scorePose))
                .setConstantHeadingInterpolation(poses.scorePose.getHeading())
                .build();
        addPath(path12);

        // Path 13
        PathChain path13 = follower.pathBuilder()
                .addPath(new BezierLine(
                        poses.scorePose,
                        poses.parkPose))
                .setLinearHeadingInterpolation(
                        poses.scorePose.getHeading(),
                        poses.parkPose.getHeading())
                .build();
        addPath(path13);
    }
}



