package com.jarhax.eerieentities.config;

import java.io.File;

import com.jarhax.eerieentities.config.mob.MobConfigNetherKnight;
import com.jarhax.eerieentities.config.mob.MobConfigPumpkinSlime;

import net.minecraftforge.common.config.Configuration;

public class Config {
    
    public static Configuration cfg = new Configuration(new File("config/eerieentities.cfg"));
    
    // TODO add wisp config
    public static MobConfigPumpkinSlime pumpkinSlime = new MobConfigPumpkinSlime(10d, 0.4d, 0d, 4d, 4, 1, 1, 5, "type=MAGICAL", "type=FOREST", "type=PLAINS");
    public static MobConfigNetherKnight netherKnight = new MobConfigNetherKnight(100d, 0.23d, 5d, 5d, 1, 1, 1, 1, "type=NETHER", "");
    
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