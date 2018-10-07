package com.jarhax.eerieentities.client.renderer.entity;

import com.jarhax.eerieentities.client.model.ModelKnightShield;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.EnumFacing;

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
        
        float angle = 90f * (direction.getHorizontalIndex() + 1);
        float bonus = entity.ticksExisted;
        angle += bonus;
        angle = angle % 360;
        angle -= -(180.0F - this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks));
        
        GlStateManager.rotate(angle, 0, 1, 0);
        GlStateManager.rotate(-24, 1, 0, 0);
        shieldModel.render(0.0625F);
        GlStateManager.popMatrix();
    }

    protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
    {
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
    
    @Override
    public boolean shouldCombineTextures () {
        
        return false;
    }   
}