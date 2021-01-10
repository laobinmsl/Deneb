package com.deneb.client.module.hud;

import com.deneb.client.client.GuiManager;
import com.deneb.client.module.HUDModule;
import com.deneb.client.module.IModule;
import com.deneb.client.module.Module;
import com.deneb.client.module.ModuleManager;
import com.deneb.client.utils.ChatUtil;
import com.deneb.client.utils.clazz.ActivedModule;
import com.deneb.client.value.BValue;
import com.deneb.client.value.MValue;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by B_312 on 01/03/21
 */
@HUDModule.Info(name = "ArrayList",x=50,y=50,width = 100,height = 100)
public class ShowArrayList extends HUDModule {

    BValue sort = setting("SortList", true);
    MValue listPos = setting("ListPos", new MValue.Mode("RightTop", true), new MValue.Mode("RightDown"), new MValue.Mode("LeftTop"), new MValue.Mode("LeftDown"));

    private String getArrayList(IModule module) {
        return module.getName() + (module.getHudInfo() == null
                || module.getHudInfo().equals("") ? "" : " " + ChatUtil.SECTIONSIGN + "7" + (module.getHudInfo().equals("")
                || module.getHudInfo() == null ? "" : "[") + ChatUtil.SECTIONSIGN + "f" + module.getHudInfo() + '\u00a7' + "7" + (module.getHudInfo().equals("") ? "" : "]"));
    }

    @Override
    public void onRender(){

        int fontColor = new Color(GuiManager.getINSTANCE().getRed() / 255f, GuiManager.getINSTANCE().getGreen() / 255f, GuiManager.getINSTANCE().getBlue() / 255f, 1F).getRGB();

        List<ActivedModule> modList = new ArrayList<>();

        ModuleManager.getModules().stream().filter(IModule::isEnabled).forEach(module -> {
            if(((Module)module).isShownOnArray()) {
                String string = getArrayList(module);
                modList.add(new ActivedModule((Module) module, string));
            }
        });

        if (sort.getValue()) {
            modList.sort((string, string2) -> font.getStringWidth(string2.string()) - font.getStringWidth(string.string()));
        }

        int y;

        if(listPos.getMode("RightTop").isToggled() ||listPos.getMode("LeftTop").isToggled() ) {
            y = this.y;
        } else {
            y = this.y - 14;
        }


        AtomicInteger offset = new AtomicInteger(y);
        AtomicInteger index = new AtomicInteger();

        int maxWidth = 0;

        for (ActivedModule string : modList) {

            int width = font.getStringWidth(string.string()) + 4;
            if(width > maxWidth) maxWidth = width;

            int rgb = rainbow(index.get() * 100);
            int red = rgb >> 16 & 255;
            int green = rgb >> 8 & 255;
            int blue = rgb & 255;
            int color;

            color = GuiManager.getINSTANCE().isRainbow() ? new Color(red,green,blue).getRGB() : fontColor;

            int x=this.x;

            int rect = new Color(10, 10, 10, 127).getRGB();

            if(listPos.getMode("RightTop").isToggled() ||listPos.getMode("RightDown").isToggled() ) {
                x = x + this.width - width;
                Gui.drawRect(x, offset.get() + 1, x + 2 + width, offset.get() + 13, rect);
                Gui.drawRect(x + width, offset.get() + 1, x + 2 +  width, offset.get() + 13, color);
                font.drawString(string.string(), x + 1, offset.get() + 5.5f, color);
            } else {
                Gui.drawRect(x + 4, offset.get() + 1, x + 4 + width, offset.get() + 13, rect);
                Gui.drawRect(x , offset.get() + 1, x + 2, offset.get() + 13, color);
                font.drawString(string.string(), x + 3, offset.get() + 5.5f, color);
            }

            offset.addAndGet(listPos.getMode("RightTop").isToggled() || listPos.getMode("LeftTop").isToggled()? 12 : -12);

            index.getAndIncrement();
        }

        this.width = maxWidth + 8;

        if(listPos.getMode("RightTop").isToggled() ||listPos.getMode("LeftTop").isToggled() ) {
            this.height = modList.size() * 12 + 4;
        } else {
            this.height = modList.size() * - 12 - 4;
        }

        modList.clear();
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 1.0f, 1.0f).getRGB();
    }



}