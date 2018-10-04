package com.jarhax.spooky.client.renderer.entity;

import com.jarhax.spooky.SpookyMod;
import com.jarhax.spooky.client.ModelCube;
import com.jarhax.spooky.entities.EntityPumpkinSlime;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPumpkinSlime extends RenderLiving<EntityPumpkinSlime> {
    
    public RenderPumpkinSlime(RenderManager renderManager) {
        
        super(renderManager, new ModelCube(), 0.0F);
    }
    
    @Override
    public void doRender (EntityPumpkinSlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    @Override
    protected void preRenderCallback (EntityPumpkinSlime entity, float partialTickTime) {
        
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        final float f1 = entity.getSlimeSize();
        final float f2 = (entity.prevSquishFactor + (entity.squishFactor - entity.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        final float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
    
    @Override
    protected ResourceLocation getEntityTexture (EntityPumpkinSlime entity) {
        
        return new ResourceLocation(SpookyMod.MODID, "textures/entity/pumpkin/pumpkin_" + entity.getType() + ".png");
    }
}