package com.jarhax.eerieentities.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockCarvedPumpkin extends BlockPumpkin {
    
    // TODO add some additional properties here
    // TODO add snow man code
    
    public static enum PumpkinType {
        
        NORMAL(Item.getItemFromBlock(Blocks.PUMPKIN), Item.getItemFromBlock(Blocks.LIT_PUMPKIN)),
        CREEPER,
        OWO,
        RAWR,
        CYCLOPS,
        SURPRISED;
        
        private Item normal;
        private Item lit;
        
        PumpkinType() {
            
            this(null, null);
        }
        
        PumpkinType(Item normal, Item lit) {
            
            this.normal = normal;
            this.lit = lit;
        }
        
        public void setItems(Item normal, Item lit) {
            
            if (this != NORMAL) {
                
                this.normal = normal;
                this.lit = lit;
            }
        }

        public Item getNormal () {
            
            return normal;
        }

        public Item getLit () {
            
            return lit;
        }
    }
}