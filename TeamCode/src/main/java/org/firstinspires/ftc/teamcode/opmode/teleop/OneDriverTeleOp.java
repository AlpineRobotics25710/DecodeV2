package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name = "One Driver TeleOp")
public class OneDriverTeleOp extends BaseTeleOp {

    @Override
    public void initGamepads() {
        driver1 = new GamepadEx(gamepad1);
    }

    @Override
    public GamepadEx driver() {
        return driver1;
    }

    @Override
    public GamepadButton intakeButton() {
        return driver1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER);
    }

    @Override
    public GamepadButton reverseIntakeButton() {
        return driver1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER);
    }

    @Override
    public GamepadButton shootButton() {
        return driver1.getGamepadButton(GamepadKeys.Button.A);
    }
}
