package com.jarhax.eerieentities.config;

import java.io.File;

import com.jarhax.eerieentities.config.mob.MobConfigPumpkinSlime;
import com.jarhax.eerieentities.entities.EntityPumpkinSlime;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.config.Configuration;

public class Config {
    
    public static Configuration cfg = new Configuration(new File("config/eerieentities.cfg"));
    
    public static MobConfigPumpkinSlime pumpkinSlime = new MobConfigPumpkinSlime("pumpkin_slime", EntityPumpkinSlime.class, EnumCreatureType.MONSTER, 10d, 0.4d, 0d, 4d, 4, 1, 1, 5, "type=MAGICAL", "type=FOREST", "type=PLAINS");
    
    public static void syncConfigData () {
        
        for (final MobConfig mobCfg : MobConfig.mobConfigs) {
            
            mobCfg.syncConfig(cfg);
        }
        
        if (cfg.hasChanged()) {
            
            cfg.save();
        }
    }
    
    public static void updateSpawnEntries () {
        
        for (final MobConfig mobCfg : MobConfig.mobConfigs) {
            
            mobCfg.insertSpawns();
        }
    }
}