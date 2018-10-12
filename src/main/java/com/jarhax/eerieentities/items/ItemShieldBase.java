package com.jarhax.eerieentities.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

// TODO move this class to bookshelf.
public class ItemShieldBase extends Item {
    
    public ItemShieldBase() {
        
        this.maxStackSize = 1;
        this.addPropertyOverride(new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1f : 0f);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }
    
    @Override
    public EnumAction getItemUseAction (ItemStack stack) {
        
        return EnumAction.BLOCK;
    }
    
    @Override
    public int getMaxItemUseDuration (ItemStack stack) {
        
        return 72000;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick (World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        
        final ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public boolean isShield (ItemStack stack, EntityLivingBase entity) {
        
        return true;
    }
}