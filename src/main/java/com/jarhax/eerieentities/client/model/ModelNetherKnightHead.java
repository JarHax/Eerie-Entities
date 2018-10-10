package com.jarhax.eerieentities.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNetherKnightHead extends ModelBase {
    
    private ModelRenderer head;
    private ModelRenderer helm;

    public ModelNetherKnightHead() {
        
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.helm = new ModelRenderer(this, 23, 7);
        this.helm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helm.addBox(-4.5F, -4.5F, -4.5F, 9, 9, 9, 0.0F);
        
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
    }
    
    public void render(float scale) { 
        
        this.helm.render(scale);
        this.head.render(scale);
    }
}
