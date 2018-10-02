package com.jarhax.spooky.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityWisp extends EntityLiving {
    
    
    public EntityWisp(World worldIn) {
        super(worldIn);
        setSize(1, 1f);
    }
    
}
