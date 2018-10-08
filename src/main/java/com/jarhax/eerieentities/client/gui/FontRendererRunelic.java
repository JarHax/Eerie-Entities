package com.jarhax.eerieentities.client.gui;

import org.lwjgl.opengl.GL11;

import com.jarhax.eerieentities.EerieEntities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class FontRendererRunelic extends FontRenderer {
    
    private final int charHeight = 8;
    private final int charWidth = 7;
    private final int charsPerRow = 16;
    private final int totalCharacters = 32;
    
    public FontRendererRunelic() {
        
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation(EerieEntities.MODID, "textures/font/runelic.png"), Minecraft.getMinecraft().renderEngine, true);
    }
    
    @Override
    protected float renderUnicodeChar (char ch, boolean italic) {
        
        final int runeWidth = (this.glyphWidth[ch] & 255) * 2;
        final float sheetSize = 256f;
        
        if (runeWidth == 0) {
            
            return 0.0F;
        }
        
        else {
            
            this.bindTexture(this.locationFontTexture);
            
            final float drawLength = (runeWidth & 15) + 1;
            final float runeX = ch % 16 * 16;
            final float runeY = (ch & 255) / 16 * 16;
            final float horizontalOffset = italic ? 1.0F : 0.0F;
            
            GlStateManager.glBegin(GL11.GL_TRIANGLE_STRIP);
            
            // Top Left
            GlStateManager.glTexCoord2f(runeX / sheetSize, runeY / sheetSize);
            GlStateManager.glVertex3f(this.posX + horizontalOffset, this.posY, 0.0F);
            
            // Bottom Left
            GlStateManager.glTexCoord2f(runeX / sheetSize, (runeY + 16F) / sheetSize);
            GlStateManager.glVertex3f(this.posX - horizontalOffset, this.posY + 8F, 0.0F);
            
            // Top Right
            GlStateManager.glTexCoord2f((runeX + drawLength) / sheetSize, runeY / sheetSize);
            GlStateManager.glVertex3f(this.posX + drawLength / 2.0F + horizontalOffset, this.posY, 0.0F);
            
            // Bottom Right
            GlStateManager.glTexCoord2f((runeX + drawLength) / sheetSize, (runeY + 16F) / sheetSize);
            GlStateManager.glVertex3f(this.posX + drawLength / 2.0F - horizontalOffset, this.posY + 8F, 0.0F);
            
            GlStateManager.glEnd();
            return drawLength / 2.0F + 1.0F;
        }
    }
    
    public int getIndexForChar (char c) {
        
        return c % this.totalCharacters;
    }
}