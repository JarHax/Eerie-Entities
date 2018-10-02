package com.jarhax.spooky;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTabSpooky extends CreativeTabs {

	public CreativeTabSpooky() {
		
		super(SpookyMod.MODID);
	}

	@Override
	public ItemStack createIcon() {

		return new ItemStack(Blocks.LIT_PUMPKIN);
	}
}