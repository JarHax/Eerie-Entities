package com.jarhax.eerieentities.client.renderer.entity.knight;

import com.jarhax.eerieentities.EerieEntities;
import com.jarhax.eerieentities.client.model.ModelKnightShield;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderLayerShield implements LayerRenderer<EntityNetherKnight> {
    
    private final ModelKnightShield shieldModel;
    private final EnumFacing direction;
    
    public RenderLayerShield(EnumFacing direction) {
        
        this.shieldModel = new ModelKnightShield();
        this.direction = direction;
    }
    
    @Override
    public void doRenderLayer (EntityNetherKnight entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        
        GlStateManager.pushMatrix();
        
        // Rotate the shield to face the direction it represents.
        float angle = 90f * (this.direction.getHorizontalIndex() + 1);
        
        // Add some rotation to it.
        angle += entity.ticksExisted;
        
        // Ensure rotation is within valid range.
        angle = angle % 360;
        
        // Counter act the natural mob rotation.
        angle -= -(180.0F - this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks));
        // Smooths the rotation
        angle += partialTicks;
        
        GlStateManager.rotate(angle, 0, 1, 0);
        GlStateManager.rotate(-24, 1, 0, 0);
        GlStateManager.color(1f, 1f, 1f, 1f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EerieEntities.MODID, "textures/entity/nether_knight/nether_knight.png"));
        this.shieldModel.render(0.0625F);
        // 0.001 to get rid of zfighting
        GlStateManager.translate(-0.25, 0.25, -(10f / 16f + 0.5f / 16f + 0.001));
        GlStateManager.scale(0.0625f, 0.0625f, 0.0625f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 769);
        EerieEntities.fontRunelic.drawString(String.valueOf(entity.getRune(this.direction.getHorizontalIndex())), 0, 0, 0x00FFFF);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    protected float interpolateRotation (float prevYawOffset, float yawOffset, float partialTicks) {
        
        float f;
        
        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
            ;
        }
        
        while (f >= 180.0F) {
            f -= 360.0F;
        }
        
        return prevYawOffset + partialTicks * f;
    }
    
    @Override
    public boolean shouldCombineTextures () {
        
        return false;
    }
}