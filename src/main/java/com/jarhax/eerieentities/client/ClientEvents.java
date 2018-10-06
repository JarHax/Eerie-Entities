package com.jarhax.eerieentities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientEvents {
    
    public static int gameTicks = 0;
    public static float partialTicks = 0;
    public static float deltaTime = 0;
    public static float totalTime = 0;
    
    private void calcDelta () {
        
        final float oldTotal = totalTime;
        totalTime = gameTicks + partialTicks;
        deltaTime = totalTime - oldTotal;
    }
    
    @SubscribeEvent
    public void onTickRenderTick (TickEvent.RenderTickEvent event) {
        
        if (event.phase == TickEvent.Phase.START) {
            partialTicks = event.renderTickTime;
        }
        else {
            this.calcDelta();
        }
        
    }
    
    @SubscribeEvent
    public void clientTickEnd (TickEvent.ClientTickEvent event) {
        
        if (event.phase == TickEvent.Phase.END) {
            final Minecraft mc = Minecraft.getMinecraft();
            
            final GuiScreen gui = mc.currentScreen;
            if (gui == null || !gui.doesGuiPauseGame()) {
                gameTicks++;
                partialTicks = 0;
            }
            this.calcDelta();
        }
    }
}
