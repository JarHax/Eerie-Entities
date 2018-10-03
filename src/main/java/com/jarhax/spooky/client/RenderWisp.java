package com.jarhax.spooky.client;


import com.jarhax.spooky.entities.EntityWisp;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.HashMap;

public class RenderWisp extends Render<EntityWisp> {
    
    public RenderWisp(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }
    
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWisp entity) {
        return new ResourceLocation("spooky:textures/entity/wisp.png");
    }
    
    @Override
    public void doRender(EntityWisp entity, double x, double y, double z, float entityYaw, float partialTicks) {
        
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 769);
        
        this.bindEntityTexture(entity);
        float width = entity.width / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, width, 0.0F);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        double scale = 0.5 + (Math.sin(entity.ticksExisted / 15f) + 1) / 2 * 0.5;
        GlStateManager.scale(scale, scale, scale);
        HashMap<String, Object> data = new HashMap<>();
        ShaderHandler.useShader(ShaderHandler.WISP, data);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        bufferbuilder.pos(-width, -width, 0).tex(0, 1).color(0f, 1f, 1f, 1f).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(width, -width, 0).tex(1, 1).color(0f, 1f, 1f, 1f).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(width, width, 0).tex(1, 0).color(0f, 1f, 1f, 1f).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-width, width, 0).tex(0, 0).color(0f, 1f, 1f, 1f).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        ShaderHandler.releaseShader();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    public static class Factory implements IRenderFactory<EntityWisp> {
        
        @Override
        public Render<? super EntityWisp> createRenderFor(RenderManager manager) {
            return new RenderWisp(manager);
        }
    }
}
