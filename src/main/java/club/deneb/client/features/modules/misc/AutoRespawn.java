package club.deneb.client.features.modules.misc;

import club.deneb.client.features.Category;
import club.deneb.client.features.Module;
import club.deneb.client.utils.ChatUtil;
import club.deneb.client.value.BooleanValue;
import club.deneb.client.event.events.client.GuiScreenEvent;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by 086 on 9/04/2018.
 */
@Module.Info(name = "AutoRespawn", description = "Automatically respawns upon death and tells you where you died", category = Category.MISC)
public class AutoRespawn extends Module {

    BooleanValue deathCoords = setting("DeathCoords", false);
    BooleanValue respawn = setting("Respawn", true);

    @SubscribeEvent
    public void listener(GuiScreenEvent.Displayed event){
        if (event.getScreen() instanceof GuiGameOver) {
            if (deathCoords.getValue())
                ChatUtil.printChatMessage(String.format("You died at x %d y %d z %d", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));

            if (respawn.getValue()) {
                mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
            }
        }
    }

}
