package com.jarhax.eerieentities.config;

import com.jarhax.eerieentities.Configuration;
import com.jarhax.eerieentities.config.mob.MobConfigCursedArmor;
import com.jarhax.eerieentities.config.mob.MobConfigNetherKnight;
import com.jarhax.eerieentities.config.mob.MobConfigPumpkinSlime;
import com.jarhax.eerieentities.entities.EntityWisp;

import net.minecraft.entity.EnumCreatureType;

public class Config {
    
    public static Configuration cfg = new Configuration("eerieentities");
    
    public static MobConfig wisp = new MobConfig("will_o_wisp", EntityWisp.class, EnumCreatureType.AMBIENT, 1d, 0.2d, 0d, 0d, 4, 1, 1, 10, 1, "type=SWAMP");
    public static MobConfigPumpkinSlime pumpkinSlime = new MobConfigPumpkinSlime(10d, 0.4d, 0d, 4d, 4, 1, 1, 5, 3, "type=MAGICAL", "type=FOREST", "type=PLAINS");
    public static MobConfigNetherKnight netherKnight = new MobConfigNetherKnight(100d, 0.23d, 5d, 5d, 1, 1, 1, 1, 350, "type=NETHER");
    public static MobConfigCursedArmor cursedArmor = new MobConfigCursedArmor(1d, 0.2d, 0d, 1d, 1, 1, 1, 1, 15, "type=WASTELAND", "type=SPOOKY", "type=DEAD", "type=MAGICAL");
    
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