package com.jarhax.eerieentities.items;

import com.jarhax.eerieentities.block.BlockCarvedPumpkin;
import com.jarhax.eerieentities.block.BlockCarvedPumpkin.PumpkinType;
import com.jarhax.eerieentities.client.ClientEvents;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockArmor extends ItemBlock {
    
    private final EntityEquipmentSlot slot;
    private final PumpkinType pumpkinType;
    
    public ItemBlockArmor(EntityEquipmentSlot slot, BlockCarvedPumpkin block, PumpkinType pumpkinType) {
        
        super(block);
        this.slot = slot;
        this.pumpkinType = pumpkinType;
    }
    
    @Override
    public EntityEquipmentSlot getEquipmentSlot (ItemStack stack) {
        
        return this.slot;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay (ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
        
        ClientEvents.renderPumpkinOverlay(resolution, this.pumpkinType.getOverlayTexture());
    }
}
