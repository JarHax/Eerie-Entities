package com.jarhax.eerieentities.config.mob;

import com.jarhax.eerieentities.config.MobConfig;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.config.Configuration;

public class MobConfigPumpkinSlime extends MobConfig {
    
    private boolean allowTaming;
    private float tameChance;
    private boolean dieInSunlight;
    private float solidifyChance;
    
    public MobConfigPumpkinSlime(String name, Class<? extends EntityLiving> entClass, EnumCreatureType mobType, double maxHealthDefault, double speedDefault, double armorDefault, double attackDefault, int maxInChunkDefault, int minPackSizeDefault, int maxPackSizeDefault, int spawnWeightDefault, String... biomesDefault) {
        
        super(name, entClass, mobType, maxHealthDefault, speedDefault, armorDefault, attackDefault, maxInChunkDefault, minPackSizeDefault, maxPackSizeDefault, spawnWeightDefault, biomesDefault);
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        super.syncConfig(config);
        
        this.allowTaming = config.getBoolean("allowTaming", this.getName(), true, "Should this mob be tameable?");
        this.tameChance = config.getFloat("tameChance", this.getName(), 0.40f, 0f, 1f, "The chance that this mob will be tamed.");
        this.dieInSunlight = config.getBoolean("dieAtDay", this.getName(), true, "Should untamed versions of this mob die during the day?");
        this.solidifyChance = config.getFloat("revertChance", this.getName(), 0.30f, 0f, 1f, "The chance that this mob will revert into a pumpkin block during day.");
    }

    public boolean isAllowTaming () {
        
        return allowTaming;
    }

    public float getTameChance () {
        
        return tameChance;
    }

    public boolean isDieInSunlight () {
        
        return dieInSunlight;
    }

    public float getSolidifyChance () {
        
        return solidifyChance;
    }
}