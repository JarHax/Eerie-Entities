package com.jarhax.eerieentities.client.renderer.entity;

import java.util.HashMap;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.jarhax.eerieentities.EerieEntities;
import com.jarhax.eerieentities.client.ShaderHandler;
import com.jarhax.eerieentities.entities.EntityWisp;
import com.jarhax.eerieentities.entities.WispType;

import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderWisp extends Render<EntityWisp> {
    
    // month, day, index, color, rgb
    public static float[][][][] colors = new float[12][31][16][3];
    
    static {
        
        for (int meta = 0; meta < 16; meta++) {
            
            colors[6][12][meta] = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(meta));
        }
        
        colors[7][1] = createRepeating(EnumDyeColor.RED, EnumDyeColor.WHITE);
        colors[4][27] = createRepeating(EnumDyeColor.BLACK, EnumDyeColor.YELLOW, EnumDyeColor.RED, EnumDyeColor.GREEN, EnumDyeColor.WHITE, EnumDyeColor.BLUE);
    }
    
    public RenderWisp(RenderManager renderManagerIn) {
        
        super(renderManagerIn);
    }
    
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture (EntityWisp entity) {
        
        return new ResourceLocation(EerieEntities.MODID, "textures/entity/wisp.png");
    }
    
    @Override
    public void doRender (EntityWisp entity, double x, double y, double z, float entityYaw, float partialTicks) {
        
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 769);
        
        this.bindEntityTexture(entity);
        final float width = entity.width / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, width, 0.0F);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        final double scale = 0.5 + (Math.sin(entity.ticksExisted / 15f) + 1) / 2 * 0.5;
        GlStateManager.scale(scale, scale, scale);
        final HashMap<String, Object> data = new HashMap<>();
        ShaderHandler.useShader(ShaderHandler.WISP, data);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        final WispType type = WispType.values()[entity.getType()];
        
        float red = 1f;
        float green = 1f;
        float blue = 1f;
        float alpha = 1;
        
        float[][] colorTable = colors[entity.getMonth()][entity.getDay()];
        
        if (entity.hasCustomName() && ("Jaredlll08".equalsIgnoreCase(entity.getName()) || "Darkhax".equalsIgnoreCase(entity.getName()))) {
            
            colorTable = colors[6][12];
        }
        
        if (colorTable != null) {
            
            float[] rgb = this.getColor(entity, colorTable, partialTicks);
            red = rgb[0];
            green = rgb[1];
            blue = rgb[2];
        }
        
        else {
            
            red = type.getRed();
            green = type.getGreen();
            blue = type.getBlue();
        }
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        bufferbuilder.pos(-width, -width, 0).tex(0, 1).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(width, -width, 0).tex(1, 1).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(width, width, 0).tex(1, 0).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-width, width, 0).tex(0, 0).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        ShaderHandler.releaseShader();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    private float[] getColor(EntityWisp wisp, float[][] colorTable, float partialTicks) {
        
        final float[] rgb = new float[3];
        
        final int ticks = wisp.ticksExisted / 25 + wisp.getEntityId();
        final int colorCount = EnumDyeColor.values().length;
        final int colorMeta1 = ticks % colorCount;
        final int colorMeta2 = (ticks + 1) % colorCount;
        final float f = (wisp.ticksExisted % 25 + partialTicks) / 25.0F;
        final float[] color1 = colorTable[colorMeta1];
        final float[] color2 = colorTable[colorMeta2];
        
        rgb[0] = color1[0] * (1.0F - f) + color2[0] * f;
        rgb[1] = color1[1] * (1.0F - f) + color2[1] * f;
        rgb[2] = color1[2] * (1.0F - f) + color2[2] * f;
        return rgb;
    }

    private static float[][] createRepeating(EnumDyeColor... colors) {
        
        final float[][] colorValues = new float[16][3];
        
        for (int meta = 0; meta < 16; meta++) {
            
            int i = meta;
            
            while (i >= colors.length) {
                
                i -= colors.length;
            }
            
            colorValues[meta] = EntitySheep.getDyeRgb(colors[i]);
        }
        
        return colorValues;
    }
}
