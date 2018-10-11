package com.jarhax.eerieentities.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;

public class BlockCarvedPumpkin extends BlockPumpkin {
    
    // TODO add some additional properties here
    // TODO add snow man code
    
    public static enum PumpkinType {
        
        NORMAL(Blocks.PUMPKIN, Blocks.LIT_PUMPKIN),
        CREEPER,
        OWO,
        RAWR,
        CYCLOPS,
        SURPRISED;
        
        private Block normal;
        private Block lit;
        
        PumpkinType() {
            
            this(null, null);
        }
        
        PumpkinType(Block normal, Block lit) {
            
            this.normal = normal;
            this.lit = lit;
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
    }
}