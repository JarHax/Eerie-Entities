package com.jarhax.eerieentities.block;

import com.jarhax.eerieentities.EerieEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class BlockCarvedPumpkin extends BlockPumpkin {
    
    public static enum PumpkinType {
        
        NORMAL(Blocks.PUMPKIN, Blocks.LIT_PUMPKIN),
        CREEPER,
        OWO,
        RAWR,
        CYCLOPS,
        SURPRISED;
        
        private final ResourceLocation overlay;
        private Block normal;
        private Block lit;
        
        PumpkinType() {
            
            this.overlay = new ResourceLocation(EerieEntities.MODID, "textures/misc/" + this.name().toLowerCase() + "blur.png");
        }
        
        PumpkinType(Block normal, Block lit) {
            
            this.normal = normal;
            this.lit = lit;
            this.overlay = null;
        }
        
        public void setItems (Block normal, Block lit) {
            
            if (this != NORMAL) {
                
                this.normal = normal;
                this.lit = lit;
            }
        }
        
        public Block getNormal () {
            
            return this.normal;
        }
        
        public Block getLit () {
            
            return this.lit;
        }
        
        public ResourceLocation getOverlayTexture () {
            
            return this.overlay;
        }
    }
}