package com.jarhax.eerieentities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
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
    
    public static void renderPumpkinOverlay (ScaledResolution scaledRes, ResourceLocation res) {
        
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
