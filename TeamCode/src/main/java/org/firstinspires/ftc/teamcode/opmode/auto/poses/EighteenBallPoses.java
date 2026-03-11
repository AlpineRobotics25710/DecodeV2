package org.firstinspires.ftc.teamcode.opmode.auto.poses;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.robot.constants.enums.Alliance;



    @Configurable
    public class EighteenBallPoses implements AutonomousPoses {

        // -------------------------------------------------------------------------
        // End poses
        // -------------------------------------------------------------------------
        public Pose startPose;
        public Pose scorePose;
        public Pose sample1Pose;
        public Pose sample2Pose;
        public Pose intakePose;
        public Pose midPickupPose;
        public Pose parkPose;


        public Pose cpPath2;
        public Pose cpPath4;
        public Pose cpPath5;
        public Pose cpPath6;
        public Pose cpPath7;
        public Pose cpPath8;
        public Pose cpPath9;
        public Pose cpPath10;

        public EighteenBallPoses() {

            // End / key poses
            startPose = new Pose(21.4, 123.9, Math.toRadians(90));
            scorePose = new Pose(59.7, 83.3, Math.toRadians(180));
            sample1Pose = new Pose(40.5, 59.6, Math.toRadians(180));
            sample2Pose = new Pose(18.6, 59.0, Math.toRadians(180));
            intakePose = new Pose(11.2, 60.6, Math.toRadians(130));
            midPickupPose = new Pose(19.5, 83.6, Math.toRadians(180));
            parkPose = new Pose(44.8, 80.0, Math.toRadians(180));

            // Control points (heading unused — supply only x, y)
            cpPath2 = new Pose(62.008, 75.622);
            cpPath4 = new Pose(54.888, 57.412);
            cpPath5 = new Pose(49.7, 52.4);
            cpPath6 = new Pose(27.4, 61.8);
            cpPath7 = new Pose(38.523, 67.311);
            cpPath8 = new Pose(38.5, 67.6);
            cpPath9 = new Pose(34.5, 68.0);
            cpPath10 = new Pose(33.0, 67.5);
        }

        @Override
        public void mirror() {
            startPose = startPose.mirror();
            scorePose = scorePose.mirror();
            sample1Pose = sample1Pose.mirror();
            sample2Pose = sample2Pose.mirror();
            intakePose = intakePose.mirror();
            midPickupPose = midPickupPose.mirror();
            parkPose = parkPose.mirror();

            cpPath2 = cpPath2.mirror();
            cpPath4 = cpPath4.mirror();
            cpPath5 = cpPath5.mirror();
            cpPath6 = cpPath6.mirror();
            cpPath7 = cpPath7.mirror();
            cpPath8 = cpPath8.mirror();
            cpPath9 = cpPath9.mirror();
            cpPath10 = cpPath10.mirror();
        }

        @Override
        public Alliance originalPosesAlliance() {
            return Alliance.BLUE;
        }
    }

