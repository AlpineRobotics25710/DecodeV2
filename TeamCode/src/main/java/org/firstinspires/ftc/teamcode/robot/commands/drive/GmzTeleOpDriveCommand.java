package org.firstinspires.ftc.teamcode.robot.commands.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.subsystem.Drivetrain;

public class GmzTeleOpDriveCommand extends CommandBase {

    private final DcMotor frontLeft;
    private final DcMotor backLeft;
    private final DcMotor frontRight;
    private final DcMotor backRight;
    private final GamepadEx driver;

    public GmzTeleOpDriveCommand(DcMotor frontLeft, DcMotor backLeft, DcMotor frontRight, DcMotor backRight, GamepadEx driver, Drivetrain drivetrain) {
        this.driver = driver;
        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.backRight = backRight;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double y = driver.getLeftY(); // Remember, Y stick value is reversed
        double x = driver.getLeftX() * 1.1; // Counteract imperfect strafing
        double rx = driver.getRightX();

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }
}

