package com.jarhax.spooky.client;


import com.jarhax.spooky.entities.EntityWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

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
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        //        GlStateManager.translate(0, entity.width / 2, 0);
        //        GlStateManager.disableLighting();
        //        //        GlStateManager.disableTexture2D();
        //        bindEntityTexture(entity);
        //        GlStateManager.color(0, 0.4f + (float) (((Math.sin(entity.ticksExisted / 15f) + 1f) / 2f) * 0.4f), 1);
        //        double size = 0.8 + ((Math.sin((entity.ticksExisted / 15f)) + 1) / 2) * 0.2;
        //        double oSize = 0.8 + ((Math.cos(entity.ticksExisted / 15f) + 1) / 2) * 0.2;
        //
        //        double laggedSize = 0.8 + ((Math.sin(((entity.ticksExisted + 40) / 15f)) + 1) / 2) * 0.2;
        //        double laggedOSize = 0.8 + ((Math.cos((entity.ticksExisted + 40) / 15f) + 1) / 2) * 0.2;
        //
        //        GlStateManager.scale(laggedSize, laggedOSize, laggedSize);
        //        GlStateManager.enableBlend();
        //        GlStateManager.blendFunc(770, 769);
        //        GlStateManager.depthMask(false);
        //        Sphere sphere = new Sphere();
        //        sphere.setTextureFlag(true);
        //        sphere.setOrientation(GLU.GLU_INSIDE);
        //        sphere.draw(entity.width / 2, 36, 36);
        //        GlStateManager.scale(oSize, size, oSize);
        //        sphere.setOrientation(GLU.GLU_OUTSIDE);
        //        if(entity.hurtTime>0) {
        //            if(entity.hurtTime % 2 == 0) {
        //                GlStateManager.color(1, 0.4f + (float) (((Math.sin(entity.ticksExisted / 15f) + 1f) / 2f) * 0.4f), 1);
        //            }
        //        }
        //        sphere.draw(entity.width / 3, 36, 36);
        //        GlStateManager.depthMask(true);
        //        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //        GlStateManager.scale(1, 1, 1);
        //        GlStateManager.enableLighting();
        //        GlStateManager.translate(0, -entity.width / 2, 0);
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(getEntityTexture(entity));
        
        
        HashMap<String, Object> data = new HashMap<>();
        ShaderHandler.useShader(ShaderHandler.WISP, data);
        GlStateManager.translate(0, entity.width / 2, 0);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 769);
        GlStateManager.depthMask(false);
        
        //TODO this is mostly(?) correct, the Y axis fails
        GlStateManager.rotate(-mc.player.rotationYaw, 0, 1, 0);
        GlStateManager.rotate(mc.player.rotationPitch, 1, 0, 1);
    
        BufferBuilder buff = Tessellator.getInstance().getBuffer();
        buff.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        float min = -0.5f;
        float max = -min;
        buff.pos(min, max, 0).tex(0, 1).color(1f, 1f, 1f, 1f).endVertex();
        buff.pos(max, max, 0).tex(1, 1).color(1f, 1f, 1f, 1f).endVertex();
        buff.pos(max, min, 0).tex(1, 0).color(1f, 1f, 1f, 1f).endVertex();
        buff.pos(min, min, 0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
        Tessellator.getInstance().draw();
        ShaderHandler.releaseShader();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.translate(-x, -y, -z);
        GlStateManager.popMatrix();
    }
    
    public static class Factory implements IRenderFactory<EntityWisp> {
        
        @Override
        public Render<? super EntityWisp> createRenderFor(RenderManager manager) {
            return new RenderWisp(manager);
        }
    }
}
