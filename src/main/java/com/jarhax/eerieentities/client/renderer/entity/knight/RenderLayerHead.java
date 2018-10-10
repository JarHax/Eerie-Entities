package com.jarhax.eerieentities.client.renderer.entity.knight;

import com.jarhax.eerieentities.client.model.ModelNetherKnightHead;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class RenderLayerHead implements LayerRenderer<EntityNetherKnight> {
    
    private final ModelNetherKnightHead headModel;
    
    public RenderLayerHead() {
        
        this.headModel = new ModelNetherKnightHead();
    }
    
    @Override
    public void doRenderLayer (EntityNetherKnight entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        
        GlStateManager.pushMatrix();
        
        GlStateManager.translate(0, -0.5, 0);
        headModel.render(0.0625f);
        GlStateManager.popMatrix();
    }
    
    @Override
    public boolean shouldCombineTextures () {
        
        return false;
    }
}