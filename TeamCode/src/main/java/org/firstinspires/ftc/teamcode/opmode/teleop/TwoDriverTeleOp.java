package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name = "Two Driver TeleOp")
public class TwoDriverTeleOp extends BaseTeleOp {

    @Override
    public void initGamepads() {
        driver1 = new GamepadEx(gamepad1);
        driver2 = new GamepadEx(gamepad2);
    }

    @Override
    public GamepadEx driver() {
        return driver1;
    }

    @Override
    public GamepadButton intakeButton() {
        return driver2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER);
    }

    @Override
    public GamepadButton reverseIntakeButton() {
        return driver2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER);
    }

    @Override
    public GamepadButton shootButton() {
        return driver2.getGamepadButton(GamepadKeys.Button.A);
    }
    
    @Override
    public GamepadButton frontSidePriorityButton() {
        return driver2.getGamepadButton(GamepadKeys.Button.X);
    }
    
    @Override
    public GamepadButton backSidePriorityButton() {
        return driver2.getGamepadButton(GamepadKeys.Button.B);
    }
    
    @Override
    public GamepadButton clearQueueButton() {
        return driver2.getGamepadButton(GamepadKeys.Button.Y);
    }
}