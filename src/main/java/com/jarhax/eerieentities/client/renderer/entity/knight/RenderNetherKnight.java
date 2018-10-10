package com.jarhax.eerieentities.client.renderer.entity.knight;

import com.jarhax.eerieentities.EerieEntities;
import com.jarhax.eerieentities.client.model.ModelEmpty;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderNetherKnight extends RenderLiving<EntityNetherKnight> {
    
    private static final ResourceLocation TEXTURE_HEAD = new ResourceLocation(EerieEntities.MODID, "textures/entity/nether_knight/head.png");
    
    public RenderNetherKnight(RenderManager rendermanagerIn) {
        
        super(rendermanagerIn, new ModelEmpty(), 0f);
        this.addLayer(new RenderLayerHead());
        this.addLayer(new RenderLayerShield(EnumFacing.NORTH));
        this.addLayer(new RenderLayerShield(EnumFacing.EAST));
        this.addLayer(new RenderLayerShield(EnumFacing.SOUTH));
        this.addLayer(new RenderLayerShield(EnumFacing.WEST));
    }
    
    @Override
    protected ResourceLocation getEntityTexture (EntityNetherKnight entity) {
        
        return TEXTURE_HEAD;
    }
}