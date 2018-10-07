package com.jarhax.eerieentities.client.renderer.entity;

import com.jarhax.eerieentities.client.model.ModelNetherKnight;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderNetherKnight extends RenderLiving<EntityNetherKnight> {

    public RenderNetherKnight(RenderManager rendermanagerIn) {
        
        super(rendermanagerIn, new ModelNetherKnight(), 0f);
        this.addLayer(new RenderLayerShield(EnumFacing.NORTH));
        this.addLayer(new RenderLayerShield(EnumFacing.EAST));
        this.addLayer(new RenderLayerShield(EnumFacing.SOUTH));
        this.addLayer(new RenderLayerShield(EnumFacing.WEST));
    }

    @Override
    protected ResourceLocation getEntityTexture (EntityNetherKnight entity) {
        
        return null;
    }
}