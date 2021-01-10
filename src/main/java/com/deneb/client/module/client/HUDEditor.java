package com.deneb.client.module.client;

import com.deneb.client.gui.guis.HUDEditorScreen;
import com.deneb.client.module.Category;
import com.deneb.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Info(name = "HUDEditor",category = Category.CLIENT,keyCode = Keyboard.KEY_GRAVE,visible = false)
public class HUDEditor extends Module {
    public static HUDEditor INSTANCE;

    @Override
    public void onInit(){
        INSTANCE = this;
    }

    HUDEditorScreen screen;

    @Override
    public void onEnable() {
        if (screen == null){
            setGUIScreen(new HUDEditorScreen());
        }
        if (mc.player != null){
            if (!(mc.currentScreen instanceof HUDEditorScreen)) {
                mc.displayGuiScreen(screen);
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen instanceof HUDEditorScreen) {
            mc.displayGuiScreen(null);
        }
		//ConfigManager.onSave();
    }

    private void setGUIScreen(HUDEditorScreen screen){
        this.screen = screen;
    }

}
