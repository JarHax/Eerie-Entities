package com.jarhax.eerieentities.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelKnightShield extends ModelBase {
    
    private final ModelRenderer shield;
    
    public ModelKnightShield() {
        
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shield = new ModelRenderer(this, 0, 0);
        this.shield.setRotationPoint(0.0F, 8.0F, -10.0F);
        this.shield.addBox(-6.0F, -12.0F, -0.5F, 12, 24, 1, 0.0F);
    }
    
    public void render (float scale) {
        
        this.shield.render(scale);
    }
}
