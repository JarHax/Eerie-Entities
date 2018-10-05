package com.jarhax.spooky.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.jarhax.spooky.SpookyMod;

import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityPumpkinSlime extends EntitySlime implements IEntityOwnable {
    
    private static final DataParameter<Boolean> IS_BLOCK = EntityDataManager.<Boolean> createKey(EntityPumpkinSlime.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer> createKey(EntityPumpkinSlime.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.<Optional<UUID>> createKey(EntityPumpkinSlime.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    
    private float rotation = 0f;
    private boolean sitting = false;
    
    public EntityPumpkinSlime(World worldIn) {
        
        super(worldIn);
    }
    
    public int getType () {
        
        return this.dataManager.get(TYPE).intValue();
    }
    
    public void setType (int value) {
        
        this.dataManager.set(TYPE, value);
    }
    
    public void setBlock (boolean value) {
        
        this.dataManager.set(IS_BLOCK, value);
    }
    
    public boolean isBlock () {
        
        return this.dataManager.get(IS_BLOCK).booleanValue();
    }
    
    @Override
    public void entityInit () {
        
        super.entityInit();
        this.dataManager.register(IS_BLOCK, false);
        this.dataManager.register(TYPE, Constants.RANDOM.nextInt(6));
        this.dataManager.register(OWNER, Optional.absent());
    }
    
    @Override
    public void onLivingUpdate () {
        
        super.onLivingUpdate();
        
        if (this.isServerWorld()) {
            
            if (this.getAttackTarget() == null && this.getOwnerId() == null || this.isSitting()) {
                this.transformToBlock();
            }
            
            else if (this.isBlock() && this.getAttackTarget() != null) {
                
                this.transformToSlime();
            }
            
            // The pumpkin slime dies if it's day and it's not in block form.
            if (this.world.isDaytime()) {
                
                // TODO make this configurable
                // Slime has a chance to turn into a real pumpkin.
                if (MathsUtils.tryPercentage(0.30)) {
                    this.world.setBlockState(this.getPosition(), Blocks.PUMPKIN.getDefaultState().withProperty(BlockHorizontal.FACING, this.getHorizontalFacing()));
                }
                
                this.setDead();
                this.spawnExplosionParticle();
            }
        }
    }
    
    @Override
    public void move (MoverType type, double x, double y, double z) {
        
        if (!this.isBlock()) {
            
            super.move(type, x, y, z);
        }
    }
    
    private void transformToSlime () {
        
        this.setBlock(false);
        
        // While in entity form, the chase range is increased to 24 blocks.
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16d);
    }
    
    private void transformToBlock () {
        
        // This should not be called after it has turned into a block.
        if (!this.isBlock()) {
            
            // Align position to the block grid.
            this.posX = Math.floor(this.posX) + 0.5D;
            this.posZ = Math.floor(this.posZ) + 0.5D;
            this.setPosition(this.posX, this.posY, this.posZ);
            
            // Loop downwards to find an non-air block.
            final MutableBlockPos pos = new MutableBlockPos(this.getPosition());
            
            while (this.world.isAirBlock(pos)) {
                
                pos.move(EnumFacing.DOWN);
            }
            
            // Set the position to be one above the first non-air block.
            this.setPosition(pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f);
            
            // Get a rotation that fits into one of the cardinal directions.
            this.rotation = Math.round(this.rotationYaw / 90.0F) * 90.0F;
            
            // Ensure there are no targets.
            this.setAttackTarget(null);
            
            // While in block form, the chase range is 4.5 blocks, same as player reach.
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(4.5d);
            
            this.setBlock(true);
        }
        
        // Force mob to look at the rotation direction.
        this.setRotation(this.rotation, 0f);
        this.prevRotationYaw = this.rotation;
        this.rotationYawHead = this.rotation;
        this.renderYawOffset = this.rotation;
        
        // Clear all motion to prevent it building up.
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        
        // Clear all movement to make extra sure it wont move.
        this.setMoveForward(0f);
        this.setMoveStrafing(0f);
        this.setMoveVertical(0f);
    }
    
    @Override
    public void writeEntityToNBT (NBTTagCompound compound) {
        
        super.writeEntityToNBT(compound);
        compound.setBoolean("IsBlockForm", this.isBlock());
        compound.setInteger("Type", this.getType());
        compound.setFloat("LookRotation", this.rotation);
        
        if (this.getOwnerId() != null) {
            
            compound.setUniqueId("Owner", this.getOwnerId());
            compound.setBoolean("Sitting", this.isSitting());
        }
    }
    
    @Override
    public void readEntityFromNBT (NBTTagCompound compound) {
        
        super.readEntityFromNBT(compound);
        this.setBlock(compound.getBoolean("IsBlockForm"));
        this.setType(compound.getInteger("Type"));
        this.rotation = compound.getFloat("LookRotation");
        
        if (compound.hasKey("Owner")) {
            
            this.setOwnerId(compound.getUniqueId("Owner"));
            this.setSitting(compound.getBoolean("Sitting"));
        }
    }
    
    @Override
    public IEntityLivingData onInitialSpawn (DifficultyInstance difficulty, IEntityLivingData livingdata) {
        
        super.onInitialSpawn(difficulty, livingdata);
        this.setSlimeSize(2, true);
        return livingdata;
    }
    
    @Override
    public ResourceLocation getLootTable() {
        
        return SpookyMod.LOOT_PUMPKIN_SLIME;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox () {
        
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }
    
    @Override
    public void applyEntityCollision (Entity entity) {
        
        // No collision please
    }
    
    @Override
    public float getCollisionBorderSize () {
        
        return 0.0F;
    }
    
    @Override
    protected Item getDropItem () {
        
        // This override is to prevent the default slime ball dropping.
        return null;
    }
    
    @Override
    public int getMaxSpawnedInChunk () {
        
        // TODO make this configurable.
        return 4;
    }
    
    @Override
    protected void setSize (float width, float height) {
        
        super.setSize(0.99f, 0.99f);
    }
    
    @Override
    public void setDead () {
        
        // This override is to prevent more slimes from spawning when this one is
        // killed.
        this.isDead = true;
    }
    
    public void setOwnerId (@Nullable UUID ownerId) {
        
        this.dataManager.set(OWNER, Optional.fromNullable(ownerId));
    }
    
    @Override
    public UUID getOwnerId () {
        
        return this.dataManager.get(OWNER).orNull();
    }
    
    @Override
    public Entity getOwner () {
        
        return this.getOwnerId() != null ? this.world.getPlayerEntityByUUID(this.getOwnerId()) : null;
    }
    
    @Override
    public boolean canDamagePlayer () {
        
        return this.getOwner() == null;
    }
    
    @Override
    public boolean spawnCustomParticles () {
        
        final int size = 2;
        
        for (int indx = 0; indx < size * 8; indx++) {
            
            float randomDegree = this.rand.nextFloat() * ((float) Math.PI * 2F);
            float randomOffset = this.rand.nextFloat() * 0.5F + 0.5F;
            float offsetX = MathHelper.sin(randomDegree) * (float) size * 0.5F * randomOffset;
            float offsetZ = MathHelper.cos(randomDegree) * (float) size * 0.5F * randomOffset;
            double particleX = this.posX + (double) offsetX;
            double particleY = this.posZ + (double) offsetZ;
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, particleX, this.getEntityBoundingBox().minY, particleY, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.PUMPKIN.getDefaultState()));
        }
        
        return true;
    }
    
    @Override
    public boolean processInteract (EntityPlayer player, EnumHand hand) {
        
        if (this.isOwner(player)) {
            
            final boolean shouldSit = !this.isSitting();
            this.setSitting(shouldSit);
            
            if (!shouldSit) {
                
                this.transformToSlime();
            }
            
            return true;
        }
        
        else {
            
            final ItemStack heldItem = player.getHeldItem(hand);
            
            if (heldItem.getItem() == Items.PUMPKIN_PIE) {
                
                heldItem.shrink(1);
                this.setOwnerId(player.getPersistentID());
                this.playTameEffect(true);
                return true;
            }
        }
        
        return super.processInteract(player, hand);
    }
    
    public boolean isSitting () {
        
        return this.sitting;
    }
    
    public void setSitting (boolean sit) {
        
        this.sitting = sit;
    }
    
    public boolean isOwner (EntityPlayer player) {
        
        return player != null && this.getOwnerId() != null && this.getOwnerId().equals(player.getPersistentID());
    }
    
    private void playTameEffect (boolean succeeded) {
        
        final EnumParticleTypes enumparticletypes = succeeded ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
        
        for (int i = 0; i < 7; ++i) {
            final double d0 = this.rand.nextGaussian() * 0.02D;
            final double d1 = this.rand.nextGaussian() * 0.02D;
            final double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(enumparticletypes, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
        }
    }
}