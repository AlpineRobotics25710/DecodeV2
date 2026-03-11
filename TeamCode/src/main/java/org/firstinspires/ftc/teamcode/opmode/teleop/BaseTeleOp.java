package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.robot.AlpineRobot;
import org.firstinspires.ftc.teamcode.robot.commands.drive.PedroTeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.IntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.intake.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.outtake.BurstShootCommand;
import org.firstinspires.ftc.teamcode.robot.constants.enums.TransferSide;

public abstract class BaseTeleOp extends CommandOpMode {

    protected AlpineRobot robot;
    protected GamepadEx driver1;
    protected GamepadEx driver2;
    
    // Burst shoot command instance - runs continuously
    protected BurstShootCommand burstShootCommand;

    public abstract void initGamepads();

    public abstract GamepadEx driver();

    public abstract GamepadButton intakeButton();

    public abstract GamepadButton reverseIntakeButton();

    /** A button - queues a ball for shooting */
    public abstract GamepadButton shootButton();
    
    /** X button - set front (left) side priority */
    public abstract GamepadButton frontSidePriorityButton();
    
    /** B button - set back (right) side priority */
    public abstract GamepadButton backSidePriorityButton();
    
    /** Y button - clear shot queue */
    public abstract GamepadButton clearQueueButton();

    @Override
    public void initialize() {
        initGamepads();

        robot = new AlpineRobot(hardwareMap);
        robot.drivetrain.setDefaultCommand(new PedroTeleOpDriveCommand(robot.drivetrain, driver()));

        intakeButton().whenHeld(new IntakeCommand(robot.frontIntake, robot.backIntake));
        reverseIntakeButton().whenHeld(new ReverseIntakeCommand(robot.frontIntake, robot.backIntake));

        // Create burst shoot command - runs continuously in the background
        burstShootCommand = new BurstShootCommand(robot.turret, robot.transfer, robot::getDistanceFromGoal);
        
        // Schedule it to run continuously (it will stay idle until balls are queued)
        schedule(burstShootCommand);

        // A button: Queue a ball for burst shooting
        // Each press queues one ball. All queued balls fire in succession once flywheel is ready.
        shootButton().whenPressed(new InstantCommand(() -> burstShootCommand.queueBall()));
        
        // X button: Set front (left) side priority - balls from front intake shoot first
        frontSidePriorityButton().whenPressed(new InstantCommand(() -> robot.transfer.setPriority(TransferSide.LEFT)));
        
        // B button: Set back (right) side priority - balls from back intake shoot first
        backSidePriorityButton().whenPressed(new InstantCommand(() -> robot.transfer.setPriority(TransferSide.RIGHT)));
        
        // Y button: Clear the shot queue (emergency cancel)
        clearQueueButton().whenPressed(new InstantCommand(() -> burstShootCommand.clearQueue()));
    }
    
    @Override
    public void run() {
        super.run();
        
        // Display telemetry for driver awareness
        telemetry.addData("Balls Queued", burstShootCommand.getBallsQueued());
        telemetry.addData("Shooting", burstShootCommand.isShooting() ? "YES" : "NO");
        telemetry.addData("Transfer Priority", robot.transfer.getCurrentPriority().name());
        telemetry.update();
    }
}
