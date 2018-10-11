package com.jarhax.eerieentities.entities;

import com.jarhax.eerieentities.EerieEntities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWisp extends EntityLiving {
    
    public EntityWisp(World worldIn) {
        
        super(worldIn);
        this.setSize(1, 1f);
    }
    
    @Override
    public ResourceLocation getLootTable () {
        
        return EerieEntities.LOOT_WISP;
    }
    
    private BlockPos spawnPosition;
    
    @Override
    protected void entityInit () {
        
        super.entityInit();
    }
    
    @Override
    public boolean canBePushed () {
        
        return false;
    }
    
    @Override
    protected void collideWithEntity (Entity entityIn) {
        
    }
    
    @Override
    protected void collideWithNearbyEntities () {
        
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
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used
     * for spiders and wolves to prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking () {
        
        return false;
    }
    
    @Override
    public void fall (float distance, float damageMultiplier) {
        
    }
    
    @Override
    protected void updateFallState (double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        
    }
    
    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    @Override
    public boolean doesEntityNotTriggerPressurePlate () {
        
        return true;
    }
    
    @Override
    public float getEyeHeight () {
        
        return this.height / 2.0F;
    }
    
}
