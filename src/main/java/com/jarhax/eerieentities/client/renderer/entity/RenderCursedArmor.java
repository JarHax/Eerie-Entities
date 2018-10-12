package com.jarhax.eerieentities.client.renderer.entity;

import com.jarhax.eerieentities.entities.EntityCursedArmor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderCursedArmor extends RenderLiving<EntityCursedArmor> {
    
    public RenderCursedArmor(RenderManager rendermanagerIn) {
        
        super(rendermanagerIn, new ModelBiped(), 0.45f);
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        
        // for (ModelRenderer box : this.getMainModel().boxList) {
        //
        // box.isHidden = true;
        // }
    }
    
    @Override
    protected ResourceLocation getEntityTexture (EntityCursedArmor entity) {
        
        return null;
    }
}