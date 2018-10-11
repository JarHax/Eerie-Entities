package com.jarhax.eerieentities.config.mob;

import com.jarhax.eerieentities.config.MobConfig;
import com.jarhax.eerieentities.entities.EntityCursedArmor;

import net.minecraft.entity.EnumCreatureType;

public class MobConfigCursedArmor extends MobConfig {

    public MobConfigCursedArmor(double maxHealthDefault, double speedDefault, double armorDefault, double attackDefault, int maxInChunkDefault, int minPackSizeDefault, int maxPackSizeDefault, int spawnWeightDefault, String... biomesDefault) {
        
        super("cursed_armor", EntityCursedArmor.class, EnumCreatureType.MONSTER, maxHealthDefault, speedDefault, armorDefault, attackDefault, maxInChunkDefault, minPackSizeDefault, maxPackSizeDefault, spawnWeightDefault, biomesDefault);
    }
}