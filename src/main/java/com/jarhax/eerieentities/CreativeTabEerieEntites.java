package com.jarhax.eerieentities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabEerieEntites extends CreativeTabs {
    
    public CreativeTabEerieEntites() {
        
        super(EerieEntities.MODID);
    }
    
    @Override
    public ItemStack createIcon () {
        
        return new ItemStack(Blocks.LIT_PUMPKIN);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems (NonNullList<ItemStack> itemList) {
        
        for (final ResourceLocation id : EerieEntities.REGISTRY.getEntityIds()) {
            
            final ItemStack spawner = new ItemStack(Items.SPAWN_EGG);
            ItemMonsterPlacer.applyEntityIdToItemStack(spawner, id);
            itemList.add(spawner);
        }
        
        super.displayAllRelevantItems(itemList);
    }
}