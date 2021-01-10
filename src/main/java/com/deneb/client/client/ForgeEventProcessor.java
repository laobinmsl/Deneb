package com.deneb.client.client;

import com.deneb.client.module.ModuleManager;
import com.deneb.client.utils.ChatUtil;
import com.deneb.client.utils.DenebTessellator;
import com.deneb.client.utils.Utils;
import com.deneb.client.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ForgeEventProcessor {

    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent.Pre event) {
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled() || Utils.nullCheck()) return;
        try {
            ModuleManager.onWorldRender(event);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            ChatUtil.printErrorChatMessage("RuntimeException: onWorldRender");
            ChatUtil.printErrorChatMessage(ex.toString());
        }
    }

    @SubscribeEvent
    public void onKey(InputUpdateEvent event){
        try {
            ModuleManager.onKey(event);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            ChatUtil.printErrorChatMessage("RuntimeException: onKey");
            ChatUtil.printErrorChatMessage(ex.toString());
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.isCanceled() || Utils.nullCheck()) return;

        try {
            RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.EXPERIENCE;
            if (!Wrapper.getPlayer().isCreative() && Wrapper.getPlayer().getRidingEntity() instanceof AbstractHorse)
                target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;

            if (event.getType() == target) {
                ModuleManager.onRender(event);
                DenebTessellator.releaseGL();
            }

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            ChatUtil.printErrorChatMessage("RuntimeException: onRender");
            ChatUtil.printErrorChatMessage(ex.toString());
        }
    }


    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.isCanceled() || Utils.nullCheck()) return;

        try {
            ModuleManager.onTick();
            /*
            if (Peek.sb != null) {
                ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                GuiShulkerBox gui = new GuiShulkerBox(Wrapper.getPlayer().inventory, Peek.sb);
                gui.setWorldAndResolution(Wrapper.getMinecraft(), i, j);
                Minecraft.getMinecraft().displayGuiScreen(gui);
                Peek.sb = null;
            }

             */
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            ChatUtil.printErrorChatMessage("RuntimeException: onTick");
            ChatUtil.printErrorChatMessage(ex.toString());
        }
    }


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if (Utils.nullCheck()) return;
        try {
            if (Keyboard.getEventKeyState()) {
                ModuleManager.onBind(Keyboard.getEventKey());
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            ChatUtil.printErrorChatMessage("RuntimeException: onKeyInput");
            ChatUtil.printErrorChatMessage(ex.toString());
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {
        /*
        if (Utils.nullCheck()) return;
        try {
            if (event.getMessage().startsWith(HyperLethal.getINSTANCE().getCommandManager().getCmdPrefix())) {
                event.setCanceled(true);
                try {
                    Wrapper.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

                    if (event.getMessage().length() > 1)
                        HyperLethal.getINSTANCE().commandManager.runCommands(event.getMessage().substring(HyperLethal.getINSTANCE().getCommandManager().getCmdPrefix().length() - 1));
                    else
                        ChatUtil.sendNoSpamMessage("Please enter a command.");
                } catch (Exception e) {
                    e.printStackTrace();
                    ChatUtil.sendNoSpamMessage("Command Error! Exception: [" + e.getMessage() + "]");
                }
                event.setMessage("");
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            ChatUtil.printErrorChatMessage("RuntimeException: onChatSent");
            ChatUtil.printErrorChatMessage(ex.toString());
        }

         */
    }
}
