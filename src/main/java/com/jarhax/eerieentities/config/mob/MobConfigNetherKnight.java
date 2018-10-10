package com.jarhax.eerieentities.config.mob;

import com.jarhax.eerieentities.config.MobConfig;
import com.jarhax.eerieentities.entities.EntityNetherKnight;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.config.Configuration;

public class MobConfigNetherKnight extends MobConfig {
    
    public MobConfigNetherKnight(double maxHealthDefault, double speedDefault, double armorDefault, double attackDefault, int maxInChunkDefault, int minPackSizeDefault, int maxPackSizeDefault, int spawnWeightDefault, String... biomesDefault) {
        
        super("nether_knight", EntityNetherKnight.class, EnumCreatureType.MONSTER, maxHealthDefault, speedDefault, armorDefault, attackDefault, maxInChunkDefault, minPackSizeDefault, maxPackSizeDefault, spawnWeightDefault, biomesDefault);
    }

    @Override
    public void syncConfig (Configuration config) {
        
        super.syncConfig(config);       
    }
}