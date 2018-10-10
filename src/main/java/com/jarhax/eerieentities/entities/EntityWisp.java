package com.jarhax.eerieentities.entities;

import com.jarhax.eerieentities.EerieEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class EntityWisp extends EntityLiving {
    
    public EntityWisp(World worldIn) {
        
        super(worldIn);
        this.setSize(1, 1f);
    }
    
    @Override
    public ResourceLocation getLootTable() {
        
        return EerieEntities.LOOT_WISP;
    }
    
    private BlockPos spawnPosition;
    
    protected void entityInit() {
        super.entityInit();
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    protected void collideWithEntity(Entity entityIn) {
    }
    
    protected void collideWithNearbyEntities() {
    }
    
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.06;
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        if(this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
        }
        
        if(this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double) ((int) this.posX), (double) ((int) this.posY), (double) ((int) this.posZ)) < 4.0D) {
            this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }
        
        double d0 = (double) this.spawnPosition.getX() + 0.1D - this.posX;
        double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
        double d2 = (double) this.spawnPosition.getZ() + 0.1D - this.posZ;
        this.motionX += (Math.signum(d0) * 0.05D - this.motionX) * 0.05D;
        this.motionY += (Math.signum(d1) * 0.08D - this.motionY) * 0.08D;
        this.motionZ += (Math.signum(d2) * 0.05D - this.motionZ) * 0.05D;
        float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.05F;
        this.rotationYaw += f1;
    }
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public void fall(float distance, float damageMultiplier) {
    }
    
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }
    
    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }
    
    public float getEyeHeight() {
        return this.height / 2.0F;
    }
    
}
