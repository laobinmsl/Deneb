package club.deneb.client.gui;

import club.deneb.client.features.Category;
import club.deneb.client.features.HUDModule;
import club.deneb.client.features.IModule;
import club.deneb.client.features.ModuleManager;
import club.deneb.client.gui.font.CFontRenderer;
import club.deneb.client.Deneb;
import club.deneb.client.client.GuiManager;
import club.deneb.client.gui.component.Component;
import club.deneb.client.gui.component.ModuleButton;
import club.deneb.client.gui.component.ValueButton;
import club.deneb.client.gui.guis.HUDEditorScreen;
import club.deneb.client.utils.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by B_312 on 01/10/21
 */
public class Panel {
    public int x,y,width,height;
    public Category category;

    public boolean extended;
    boolean dragging;

    boolean isHUD = false;

    int x2,y2;

    CFontRenderer font;

    public List<ModuleButton> Elements = new ArrayList<>();

    public Panel(Category category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.extended = true;
        this.dragging = false;
        this.category = category;
        isHUD = category.isHUD();
        font = Deneb.getINSTANCE().getFont();
        setup();
    }

    public void setup() {
        for (IModule m : ModuleManager.getAllModules()) {
            if(m.category == category){
                Elements.add(new ModuleButton(m,width,height,this));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }

        int panelColor = 0x85000000;

        int color = new Color(GuiManager.getINSTANCE().getRed(),GuiManager.getINSTANCE().getGreen(),GuiManager.getINSTANCE().getBlue(),208).getRGB();

        Gui.drawRect(x, y, x + width, y + height, color);
        font.drawString(category.getName(), x + (width / 2 - font.getStringWidth(category.getName()) / 2), y + height / 2 - font.getHeight() / 2f + 2, 0xffefefef);

        Gui.drawRect(x, y+height, x + width, y +height +1 , panelColor);

        if (this.extended && !Elements.isEmpty()) {

            int startY = y + height + 2;

            for (ModuleButton button : Elements){
                button.solvePos();
                button.y = startY;
                button.render(mouseX,mouseY,partialTicks);
                int settingY = startY-1;
                startY += height + 1;
                if(button.isExtended){
                    for(Component component : button.settings){
                        if(component instanceof ValueButton) {
                            if(!((ValueButton<?>) component).getValue().visible()) continue;
                        }
                        component.solvePos();
                        component.y = startY;
                        component.render(mouseX,mouseY,partialTicks);
                        startY += height;
                    }
                    if(GuiManager.getINSTANCE().isSettingRect() || GuiManager.getINSTANCE().isSettingSide())
                        Gui.drawRect(x,settingY,x+1,startY,color);
                    if(GuiManager.getINSTANCE().isSettingRect()) {
                        Gui.drawRect(x + width, settingY, x + width - 1, startY, color);
                        Gui.drawRect(x + 1, settingY, x + width - 1, settingY + 1, color);
                        Gui.drawRect(x + 1, startY - 1, x + width - 1, startY, color);
                    }
                }
                //Gui.drawRect(x, settingY -1, x + width, settingY + 1, panelColor);
                startY += 1;
                if(button.module.isHUD && Wrapper.mc.currentScreen instanceof HUDEditorScreen) {
                    HUDModule hud = (HUDModule) button.module;
                    Gui.drawRect(hud.x, hud.y, hud.x + hud.width, hud.y + hud.height , panelColor);
                    hud.onRender();
                }
            }

        }

    }


    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isHovered(mouseX, mouseY).test(this)) {
            x2 = this.x - mouseX;
            y2 = this.y - mouseY;
            dragging = true;
            if(!isHUD) {
                Collections.swap(GUIRender.getINSTANCE().panels, 0,GUIRender.getINSTANCE().panels.indexOf(this));
            } else {
                Collections.swap(HUDRender.getINSTANCE().panels, 0,HUDRender.getINSTANCE().panels.indexOf(this));
            }
            return true;
        }
        if (mouseButton == 1 && isHovered(mouseX, mouseY).test(this)) {
            extended = !extended;
            return true;
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            this.dragging = false;
        }
        for (Component part : Elements) {
            part.mouseReleased(mouseX, mouseY, state);
        }

    }

    public void keyTyped(char typedChar, int keyCode) {
        for (Component part : Elements) {
            part.keyTyped(typedChar, keyCode);
        }
    }

    public Predicate<Panel> isHovered(int mouseX, int mouseY) {
        return c -> mouseX >= Math.min(c.x,c.x + c.width) && mouseX <= Math.max(c.x,c.x+c.width)  && mouseY >= Math.min(c.y,c.y + c.height) && mouseY <= Math.max(c.y,c.y + c.height);
    }
}
