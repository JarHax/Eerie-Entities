package com.jarhax.eerieentities.config.mob;

import com.jarhax.eerieentities.Configuration;
import com.jarhax.eerieentities.config.MobConfig;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;

public class MobConfigNetherKnight extends MobConfig {
    
    private float reinforcementChance;
    private double bonusArmor;
    private double bonusHealth;
    private int[] spawnRange;
    private ResourceLocation[] reinforcementIDs;
    private float burnChance;
    
    public MobConfigNetherKnight(double maxHealthDefault, double speedDefault, double armorDefault, double attackDefault, int maxInChunkDefault, int minPackSizeDefault, int maxPackSizeDefault, int spawnWeightDefault, int baseEXPDefault, String... biomesDefault) {
        
        super("nether_knight", EntityNetherKnight.class, EnumCreatureType.MONSTER, maxHealthDefault, speedDefault, armorDefault, attackDefault, maxInChunkDefault, minPackSizeDefault, maxPackSizeDefault, spawnWeightDefault, baseEXPDefault, biomesDefault);
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        super.syncConfig(config);
        
        this.reinforcementChance = config.getFloat("reinforcementChance", this.getName(), 0.15f, 0f, 1f, "The chance that attacking will spawn a reinforcement.");
        this.bonusArmor = config.getDouble("reinforcementArmorBonus", this.getName(), 5d, 0d, 1024d, "The amount of bonus armor to give reinforcement mobs.");
        this.bonusHealth = config.getDouble("reinforcementHealthBonus", this.getName(), 10d, 0d, 1024d, "The amount of bonus health to give reinforcement mobs.");
        this.spawnRange = config.getRange("ReinforcementSpawnRange", this.getName(), 0, 5, 0, 512, "the reinforcement spawn range.");
        this.reinforcementIDs = config.getResourceLocationList("reinforcementIDs", this.getName(), new String[] { "minecraft:wither_skeleton", "minecraft:blaze" }, "List of mob IDs that can spawn as reinforcements.");
        this.burnChance = config.getFloat("burnChance", this.getName(), 0.33f, 0f, 1f, "The chance that an attacker would be set on fire.");
    }
    
    public float getReinforcementChance () {
        
        return this.reinforcementChance;
    }
    
    public double getBonusArmor () {
        
        return this.bonusArmor;
    }
    
    public double getBonusHealth () {
        
        return this.bonusHealth;
    }
    
    public int[] getSpawnRange () {
        
        return this.spawnRange;
    }
    
    public ResourceLocation[] getReinforcementIDs () {
        
        return this.reinforcementIDs;
    }
    
    public float getBurnChance () {
        
        return this.burnChance;
    }
}