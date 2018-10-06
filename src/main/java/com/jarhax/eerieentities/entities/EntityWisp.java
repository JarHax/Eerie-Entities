package com.jarhax.eerieentities.entities;

import com.jarhax.eerieentities.EerieEntities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityWisp extends EntityLiving {
    
    public EntityWisp(World worldIn) {
        
        super(worldIn);
        this.setSize(1, 1f);
    }
    
    @Override
    public ResourceLocation getLootTable () {
        
        return EerieEntities.LOOT_WISP;
    }
}
