package club.deneb.client.features.huds.info;

import club.deneb.client.client.GuiManager;
import club.deneb.client.features.Category;
import club.deneb.client.features.HUDModule;

import java.awt.*;
import java.util.Objects;

/**
 * Created by B_312 on 01/03/21
 */
@HUDModule.Info(name = "Server", x = 160, y = 160, width = 100, height = 10,category = Category.INFO)
public class Server extends HUDModule {

    @Override
    public void onRender() {

        int fontColor = new Color(GuiManager.getINSTANCE().getRed() / 255f, GuiManager.getINSTANCE().getGreen() / 255f, GuiManager.getINSTANCE().getBlue() / 255f, 1F).getRGB();

        String Final = "IP " + "\u00a7f" + (mc.isSingleplayer() ? "Single Player" : Objects.requireNonNull(mc.getCurrentServerData()).serverIP.toLowerCase());

        font.drawString(Final, this.x + 2, this.y + 4, fontColor);

        this.height = font.getHeight() * 2;
        this.width = font.getStringWidth(Final) + 4;

    }

}
