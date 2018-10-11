package com.jarhax.eerieentities.config.mob;

import com.jarhax.eerieentities.Configuration;
import com.jarhax.eerieentities.config.MobConfig;
import com.jarhax.eerieentities.entities.EntityPumpkinSlime;

import net.minecraft.entity.EnumCreatureType;

public class MobConfigPumpkinSlime extends MobConfig {
    
    private boolean allowTaming;
    private float tameChance;
    private boolean dieInSunlight;
    private float solidifyChance;
    
    public MobConfigPumpkinSlime(double maxHealthDefault, double speedDefault, double armorDefault, double attackDefault, int maxInChunkDefault, int minPackSizeDefault, int maxPackSizeDefault, int spawnWeightDefault, int baseEXPDefault, String... biomesDefault) {
        
        super("pumpkin_slime", EntityPumpkinSlime.class, EnumCreatureType.MONSTER, maxHealthDefault, speedDefault, armorDefault, attackDefault, maxInChunkDefault, minPackSizeDefault, maxPackSizeDefault, spawnWeightDefault, baseEXPDefault, biomesDefault);
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
        
        return this.allowTaming;
    }
    
    public float getTameChance () {
        
        return this.tameChance;
    }
    
    public boolean isDieInSunlight () {
        
        return this.dieInSunlight;
    }
    
    public float getSolidifyChance () {
        
        return this.solidifyChance;
    }
}