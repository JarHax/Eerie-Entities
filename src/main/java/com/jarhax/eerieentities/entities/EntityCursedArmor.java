package com.jarhax.eerieentities.entities;

import javax.annotation.Nullable;

import com.jarhax.eerieentities.config.Config;

import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityCursedArmor extends EntityMob {

    private static Item[][] equipment = {
            {Items.IRON_BOOTS, Items.IRON_LEGGINGS, Items.IRON_CHESTPLATE, Items.IRON_HELMET, Items.IRON_SWORD},
            {Items.GOLDEN_BOOTS, Items.GOLDEN_LEGGINGS, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_HELMET, Items.GOLDEN_SWORD},
            {Items.CHAINMAIL_BOOTS, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET, Items.STONE_SWORD},
            {Items.LEATHER_BOOTS, Items.LEATHER_LEGGINGS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET, Items.WOODEN_SWORD}
            };
    
    public EntityCursedArmor(World worldIn) {
        
        super(worldIn);
    }  
    
    protected void initEntityAI() {
        
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
    
    @Override
    public void applyEntityAttributes () {
        
        super.applyEntityAttributes();
        Config.cursedArmor.apply(this);
        this.experienceValue = Config.cursedArmor.getBaseEXP();
    }
    
    @Override
    public int getMaxSpawnedInChunk () {
        
        return Config.cursedArmor.getMaxInChunk();
    }
    
    @Override
    public boolean attackEntityFrom (DamageSource source, float amount) {
        
        if (this.getTotalArmorValue() > 0) {
            
            if (!source.isFireDamage() && !source.isMagicDamage()) {

                IAttributeInstance armor = this.getEntityAttribute(SharedMonsterAttributes.ARMOR);
                armor.setBaseValue(armor.getBaseValue() - MathHelper.nextFloat(Constants.RANDOM, 0f, 3f));
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ITEM_BREAK, this.getSoundCategory(), 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
            }
            
            return false;
        }
        
        return super.attackEntityFrom(source, amount);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn (DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        Item[] items = equipment[Constants.RANDOM.nextInt(equipment.length)];
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(items[3]));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(items[2]));
        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(items[1]));
        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(items[0]));
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(items[4]));
        return livingdata;
    }
}