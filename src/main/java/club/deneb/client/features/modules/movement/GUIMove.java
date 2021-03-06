package club.deneb.client.features.modules.movement;

import club.deneb.client.features.Category;
import club.deneb.client.features.Module;
import club.deneb.client.value.BooleanValue;
import club.deneb.client.value.IntValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.InputUpdateEvent;
import org.lwjgl.input.Keyboard;

/**
 * Created by KillRED on 2019
 * Updated by B_312 on 2021
 */
@Module.Info(name = "GUIMove",category = Category.MOVEMENT)
public class GUIMove extends Module {

    IntValue pitchSpeed = setting("PitchSpeed", 6, 0, 20);
    IntValue yawSpeed = setting("YawSpeed", 6, 0, 20);
    BooleanValue chat = setting("Chat", false);
    BooleanValue sneak = setting("Sneak", false);

    @Override
    public void onTick() {
        if (isEnabled() && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) || (mc.currentScreen instanceof GuiChat && chat.getValue())) {
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                for (int i = 0; i < pitchSpeed.getValue(); ++i) {
                    mc.player.rotationPitch --;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                for (int i = 0; i < pitchSpeed.getValue(); ++i) {
                    mc.player.rotationPitch ++;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                for (int i = 0; i < yawSpeed.getValue(); ++i) {
                    mc.player.rotationYaw ++;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                for (int i = 0; i < yawSpeed.getValue(); ++i) {
                    mc.player.rotationYaw --;
                }
            }
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())) {
                mc.player.setSprinting(true);
            }
            if (mc.player.rotationPitch > 90) mc.player.rotationPitch = 90;
            if (mc.player.rotationPitch < -90) mc.player.rotationPitch = -90;
        }
    }

    @Override
    public void onKey(InputUpdateEvent event){
        if (isEnabled() && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) || (mc.currentScreen instanceof GuiChat && chat.getValue())) {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
                event.getMovementInput().moveForward = getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                event.getMovementInput().moveForward = -getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
                event.getMovementInput().moveStrafe = getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
                event.getMovementInput().moveStrafe = -getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                event.getMovementInput().jump = true;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && sneak.getValue()) {
                event.getMovementInput().sneak = true;
            }
        }
    }

    private float getSpeed() {
        float x = 1;
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && sneak.getValue()) {
            x = 0.30232558139f;
        }
        return x;
    }

}
