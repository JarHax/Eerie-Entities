package com.jarhax.eerieentities.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemNetherShield extends ItemShieldBase {
    
    public ItemNetherShield() {
        
        this.setMaxDamage(512);
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        
        return EnumRarity.RARE;
    }
    
    @Override
    public void blockDamage(EntityLivingBase user, DamageSource damageSource, float amount) {
        
        final Entity source = damageSource.getImmediateSource();
        
        if (source != null) {
            
            source.setFire(2);
            
            if (source instanceof EntityArrow) {
                
                source.setFire(30);
            }
        }
    }
}