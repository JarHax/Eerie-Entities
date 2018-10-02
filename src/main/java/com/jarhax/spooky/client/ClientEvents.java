package com.jarhax.spooky.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientEvents {
    
    public static int gameTicks = 0;
    public static float partialTicks = 0;
    public static float deltaTime = 0;
    public static float totalTime = 0;
    
    private void calcDelta() {
        float oldTotal = totalTime;
        totalTime = gameTicks + partialTicks;
        deltaTime = totalTime - oldTotal;
    }
    
    @SubscribeEvent
    public void onTickRenderTick(TickEvent.RenderTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            partialTicks = event.renderTickTime;
        } else {
            calcDelta();
        }
        
    }
    
    @SubscribeEvent
    public void clientTickEnd(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            
            GuiScreen gui = mc.currentScreen;
            if(gui == null || !gui.doesGuiPauseGame()) {
                gameTicks++;
                partialTicks = 0;
            }
            calcDelta();
        }
    }
}
