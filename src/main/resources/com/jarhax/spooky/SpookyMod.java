package com.jarhax.spooky;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.network.NetworkHandler;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
		
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		
	}
}
