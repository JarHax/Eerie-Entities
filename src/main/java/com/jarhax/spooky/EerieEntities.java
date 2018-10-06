package com.jarhax.spooky;

import com.jarhax.spooky.client.ClientEvents;
import com.jarhax.spooky.client.ShaderHandler;
import com.jarhax.spooky.client.renderer.entity.RenderPumpkinSlime;
import com.jarhax.spooky.client.renderer.entity.RenderWisp;
import com.jarhax.spooky.config.Config;
import com.jarhax.spooky.entities.EntityPumpkinSlime;
import com.jarhax.spooky.entities.EntityWisp;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.network.NetworkHandler;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = EerieEntities.MODID, name = EerieEntities.NAME, version = "@VERSION@", dependencies = "required-after:bookshelf@[2.3.566,);", certificateFingerprint = "@FINGERPRINT@", guiFactory = "com.jarhax.spooky.client.gui.GuiFactoryEerieEntities")
public class EerieEntities {
    
    public static final String MODID = "eerieentities";
    public static final String NAME = "Eerie Entities";
    
    public static final LoggingHelper LOG = new LoggingHelper(NAME);
    public static final RegistryHelper REGISTRY = new RegistryHelper(MODID).setTab(new CreativeTabEerieEntites()).enableAutoRegistration();
    public static final NetworkHandler NETWORK = new NetworkHandler(MODID);
    
    public static final ResourceLocation LOOT_WISP = REGISTRY.registerLootTable("entities/wisp");
    public static final ResourceLocation LOOT_PUMPKIN_SLIME = REGISTRY.registerLootTable("entities/pumpkin_slime");
    
    @EventHandler
    public void onPreInit (FMLPreInitializationEvent event) {
        
        Config.syncConfigData();
        
        REGISTRY.registerMob(EntityWisp.class, "wisp", 0, 0x00ffff, 0x33ccff);
        REGISTRY.registerMob(EntityPumpkinSlime.class, "pumpkin_slime", 1, 0xB67317, 0x804809);
    }
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void onClientPreInit (FMLPreInitializationEvent event) {
        
        ShaderHandler.registerShaders();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        RenderingRegistry.registerEntityRenderingHandler(EntityWisp.class, RenderWisp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPumpkinSlime.class, RenderPumpkinSlime::new);
    }
    
    @EventHandler
    public void onInit (FMLInitializationEvent event) {
        
    }
    
    @EventHandler
    public void onPostInit (FMLPostInitializationEvent event) {
        
        Config.updateSpawnEntries();
    }
}
