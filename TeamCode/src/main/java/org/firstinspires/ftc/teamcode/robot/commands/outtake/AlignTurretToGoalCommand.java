package org.firstinspires.ftc.teamcode.robot.commands.outtake;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.constants.enums.Alliance;
import org.firstinspires.ftc.teamcode.robot.subsystem.Turret;

import java.util.List;

public class AlignTurretToGoalCommand extends CommandBase {
    private final Turret turret;
    private final Limelight3A limelight;
    private final Follower follower;
    private final int targetTagId;

    public AlignTurretToGoalCommand(Turret turret, Limelight3A limelight, Follower follower, Alliance alliance) {
        this.turret = turret;
        this.limelight = limelight;
        this.follower = follower;

        // Assign target ID based on alliance (blue goal is 20 red goal is 24)
        this.targetTagId = (alliance == Alliance.BLUE) ? 20 : 24;

        addRequirements(turret);
    }

    @Override
    public void execute() {
        // Feed heading to Limelight for internal MT2/orientation math if needed
        limelight.updateRobotOrientation(follower.getHeading());

        LLResult llResult = limelight.getLatestResult();
        LLResultTypes.FiducialResult targetTag = null;

        if (llResult != null && llResult.isValid()) {
            List<LLResultTypes.FiducialResult> detectedTags = llResult.getFiducialResults();
            for (LLResultTypes.FiducialResult tag : detectedTags) {
                if (tag.getFiducialId() == targetTagId) {
                    targetTag = tag;
                    break;
                }
            }
        }

        if (targetTag != null) {
            // Horizontal tag error in degrees; Turret PID drives this to zero.
            //turret.setTurretAlignmentError(targetTag.getTargetXDegrees());
        } else {
            // Tag not visible – enter recovery creep back toward last known direction
            //turret.onTagLost();
        }
    }
}
