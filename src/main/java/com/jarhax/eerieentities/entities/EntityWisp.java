package com.jarhax.eerieentities.entities;

import javax.annotation.Nullable;

import com.jarhax.eerieentities.EerieEntities;
import com.jarhax.eerieentities.config.Config;

import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.lib.WeightedSelector;
import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityWisp extends EntityLiving {
    
    private BlockPos spawnPosition;
    private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer> createKey(EntityWisp.class, DataSerializers.VARINT);
    
    public EntityWisp(World worldIn) {
        
        super(worldIn);
        this.setSize(1, 1f);
    }
    
    @Override
    public void applyEntityAttributes () {
        
        super.applyEntityAttributes();
        Config.wisp.apply(this);
        this.experienceValue = Config.wisp.getBaseEXP();
    }
    
    public int getType () {
        
        return this.dataManager.get(TYPE).intValue();
    }
    
    public void setType (int value) {
        
        this.dataManager.set(TYPE, value);
    }
    
    @Override
    protected void entityInit () {
        
        super.entityInit();
        this.dataManager.register(TYPE, 0);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn (DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setType(WispType.selector.getRandomEntry().getEntry().ordinal());
        return livingdata;
    }
    
    @Override
    public void readEntityFromNBT (NBTTagCompound compound) {
        
        super.readEntityFromNBT(compound);
        this.setType(compound.getInteger("Type"));
    }
    
    @Override
    public void writeEntityToNBT (NBTTagCompound compound) {
        
        super.writeEntityToNBT(compound);
        compound.setInteger("Type", this.getType());
    }
    
    @Override
    public int getMaxSpawnedInChunk () {
        
        return Config.wisp.getMaxInChunk();
    }
    
    @Override
    public ResourceLocation getLootTable () {
        
        return EerieEntities.LOOT_WISP;
    }
    
    @Override
    public boolean canBePushed () {
        
        return false;
    }
    
    @Override
    protected void collideWithEntity (Entity entityIn) {
        
        if (entityIn instanceof EntityLivingBase) {
            
            entityIn.setFire(30);
        }
        
        // No entity collision
    }
    
    @Override
    public boolean getCanSpawnHere () {
        
        return super.getCanSpawnHere() && this.isValidLightLevel() && !this.world.isRaining();
    }
    
    protected boolean isValidLightLevel () {
        
        final BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        
        if (MathsUtils.tryPercentage(0.45)) {
            
            return false;
        }
        
        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
            
            return false;
        }
        
        else {
            
            return this.world.getLightFromNeighbors(blockpos) <= this.rand.nextInt(8);
        }
    }
    
    @Override
    public void onUpdate () {
        
        super.onUpdate();
        this.motionY *= 0.06;
    }
    
    @Override
    protected void updateAITasks () {
        
        super.updateAITasks();
        if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
        }
        
        if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0D) {
            this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }
        
        final double d0 = this.spawnPosition.getX() + 0.1D - this.posX;
        final double d1 = this.spawnPosition.getY() + 0.1D - this.posY;
        final double d2 = this.spawnPosition.getZ() + 0.1D - this.posZ;
        this.motionX += (Math.signum(d0) * 0.05D - this.motionX) * 0.05D;
        this.motionY += (Math.signum(d1) * 0.08D - this.motionY) * 0.08D;
        this.motionZ += (Math.signum(d2) * 0.05D - this.motionZ) * 0.05D;
        final float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        final float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.05F;
        this.rotationYaw += f1;
    }
    
    @Override
    protected boolean canTriggerWalking () {
        
        return false;
    }
    
    @Override
    public void fall (float distance, float damageMultiplier) {
        
        // No fall damage
    }
    
    @Override
    protected void updateFallState (double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        
        // No falling
    }
    
    @Override
    public boolean doesEntityNotTriggerPressurePlate () {
        
        return true;
    }
    
    @Override
    public float getEyeHeight () {
        
        return this.height / 2.0F;
    }
}
