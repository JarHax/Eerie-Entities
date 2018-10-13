package com.jarhax.eerieentities.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// TODO move this class to bookshelf.
// TODO add many more hooks into Nautilus
@EventBusSubscriber
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
    
    public void blockDamage (EntityLivingBase user, DamageSource damageSource, float amount) {
        
        // Hook for when damage is blocked.
    }
    
    @SubscribeEvent
    public static void onAttack (LivingAttackEvent event) {
        
        if (event.getEntityLiving().getActiveItemStack().getItem() instanceof ItemShieldBase && !event.getSource().isUnblockable()) {
            
            final ItemShieldBase shield = (ItemShieldBase) event.getEntityLiving().getActiveItemStack().getItem();
            shield.blockDamage(event.getEntityLiving(), event.getSource(), event.getAmount());
        }
    }
}