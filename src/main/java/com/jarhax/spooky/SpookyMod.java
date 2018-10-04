package com.jarhax.spooky;

import com.jarhax.spooky.client.*;
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
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.*;

@Mod(modid = SpookyMod.MODID, name = SpookyMod.NAME, version = "@VERSION@", dependencies = "required-after:bookshelf@[2.3.562,);", certificateFingerprint = "@FINGERPRINT@")
public class SpookyMod {

	//TODO change this before submission!
	public static final String MODID = "spooky";
	//TODO change this before submission!
	public static final String NAME = "Spooky Mod";
	
	public static final LoggingHelper LOG = new LoggingHelper(NAME);
	public static final RegistryHelper REGISTRY = new RegistryHelper(MODID).setTab(new CreativeTabSpooky()).enableAutoRegistration();
	public static final NetworkHandler NETWORK = new NetworkHandler(MODID);
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		
		REGISTRY.registerMob(EntityWisp.class, "wisp", 0, 0x00ffff, 0x33ccff);
	}
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void onClientPreInit(FMLPreInitializationEvent event) {
        ShaderHandler.registerShaders();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        RenderingRegistry.registerEntityRenderingHandler(EntityWisp.class, new RenderWisp.Factory());
	}
    
    @EventHandler
	public void onInit(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		
	}
}
