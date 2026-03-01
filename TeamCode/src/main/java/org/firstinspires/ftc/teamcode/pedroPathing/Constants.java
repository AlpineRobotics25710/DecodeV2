package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PredictiveBrakingCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(12.15628) // in kilograms - TODO: need to update
            .forwardZeroPowerAcceleration(-30.633405046849877) // TODO: need to update
            .lateralZeroPowerAcceleration(-46.70776196795261) // TODO: need to update
//            .translationalPIDFCoefficients(new PIDFCoefficients(0.25, 0, 0.027, 0.024))  // dont need with predictive

            // heading PIDF
            .headingPIDFCoefficients(new PIDFCoefficients(2.1, 0, 0.02, 0.033))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(1.0, 0, 0.1, 0.015))
            .useSecondaryHeadingPIDF(true)

//            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.2, 0, 0.001, 0, 0.03)) // dont need with predictive
//            .centripetalScaling(0.0005) // TODO: need to update

            // get values from running automatic predictive braking tuner
            .predictiveBrakingCoefficients(new PredictiveBrakingCoefficients(0.2, 0, 0)); // TODO: need to update

    public static PathConstraints pathConstraints = new PathConstraints(0.96, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("FR")
            .rightRearMotorName("BR")
            .leftRearMotorName("BL")
            .leftFrontMotorName("FL")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .useBrakeModeInTeleOp(true)
            .xVelocity(64.01810917891856) // TODO: need to update
            .yVelocity(54.21175883135457); // TODO: need to update

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-33.3) // TODO: need to update
            .strafePodX(-111.7) // TODO: need to update
            .distanceUnit(DistanceUnit.MM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED) // TODO: need to test for reversed
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED); // TODO: need to test for reversed

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
