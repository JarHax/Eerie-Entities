package com.jarhax.eerieentities;

import com.jarhax.eerieentities.client.ClientEvents;
import com.jarhax.eerieentities.client.ShaderHandler;
import com.jarhax.eerieentities.client.gui.FontRendererRunelic;
import com.jarhax.eerieentities.client.renderer.entity.RenderNetherKnight;
import com.jarhax.eerieentities.client.renderer.entity.RenderPumpkinSlime;
import com.jarhax.eerieentities.client.renderer.entity.RenderWisp;
import com.jarhax.eerieentities.config.Config;
import com.jarhax.eerieentities.entities.EntityNetherKnight;
import com.jarhax.eerieentities.entities.EntityPumpkinSlime;
import com.jarhax.eerieentities.entities.EntityWisp;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.network.NetworkHandler;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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
    public static FontRenderer fontRunelic;
    
    @EventHandler
    public void onPreInit (FMLPreInitializationEvent event) {
        
        Config.syncConfigData();
        
        REGISTRY.registerMob(EntityWisp.class, "wisp", 0, 0x00ffff, 0x33ccff);
        REGISTRY.registerMob(EntityPumpkinSlime.class, "pumpkin_slime", 1, 0xB67317, 0x804809);
        REGISTRY.registerMob(EntityNetherKnight.class, "nether_knight", 2, 16775294, 16167425);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void onClientPreInit (FMLPreInitializationEvent event) {
        
        ShaderHandler.registerShaders();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        RenderingRegistry.registerEntityRenderingHandler(EntityWisp.class, RenderWisp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPumpkinSlime.class, RenderPumpkinSlime::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNetherKnight.class, RenderNetherKnight::new);
    }
    
    @SubscribeEvent
    public void onClient (TickEvent.RenderTickEvent evt) {
        
        fontRunelic.drawString("HELLO AGAIN FRIEND OF A FRIEND", 50, 50, 0xFFAABB);
    }
    
    @EventHandler
    public void onInit (FMLInitializationEvent event) {
        
        fontRunelic = new FontRendererRunelic();
    }
    
    @EventHandler
    public void onPostInit (FMLPostInitializationEvent event) {
        
        Config.updateSpawnEntries();
    }
}
