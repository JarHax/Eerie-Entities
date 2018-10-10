package com.jarhax.eerieentities.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import com.jarhax.eerieentities.EerieEntities;
import com.jarhax.eerieentities.config.Config;

import net.darkhax.bookshelf.data.AttributeOperation;
import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityNetherKnight extends EntityBlaze {
    
    private static final DataParameter<Integer> RUNE_WORD = EntityDataManager.<Integer> createKey(EntityNetherKnight.class, DataSerializers.VARINT);
    public static final char[][] WORDS = { { 68, 65, 82, 75 }, { 70, 73, 82, 69 }, { 71, 69, 71, 89 }, { 83, 65, 76, 84 }, { 67, 85, 78, 84 } };
    
    private static final AttributeModifier BUFF_ARMOR = new AttributeModifier(UUID.fromString("cb1a4e88-69d3-4ba4-a6de-ea98bc63114f"), "knight_buff_armor", Config.netherKnight.getBonusArmor(), AttributeOperation.ADDITIVE.ordinal());
    private static final AttributeModifier BUFF_HEALTH = new AttributeModifier(UUID.fromString("df154adf-523a-4523-bf02-08115e8a666f"), "knight_buff_health", Config.netherKnight.getBonusHealth(), AttributeOperation.ADDITIVE.ordinal());
    
    public EntityNetherKnight(World world) {
        
        super(world);
    }
    
    public int getRuneWord () {
        
        return this.dataManager.get(RUNE_WORD).intValue();
    }
    
    public void setRuneWord (int value) {
        
        this.dataManager.set(RUNE_WORD, value);
    }
       
    public char getRune (int index) {
        
        return index >= 0 && index < 4 ? WORDS[this.getRuneWord()][index] : 'X';
    }
    
    @Override
    public void entityInit () {
        
        super.entityInit();
        this.dataManager.register(RUNE_WORD, 0);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn (DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setRuneWord(Constants.RANDOM.nextInt(WORDS.length));
        return livingdata;
    }
    
    @Override
    public void writeEntityToNBT (NBTTagCompound compound) {
        
        super.writeEntityToNBT(compound);
        compound.setInteger("RuneWord", this.getRuneWord());
    }
    
    @Override
    public void readEntityFromNBT (NBTTagCompound compound) {
        
        super.readEntityFromNBT(compound);
        this.setRuneWord(compound.getInteger("RuneWord"));
    }
    
    @Override
    public float getEyeHeight() {
        
        return this.height * 1.15F;
    }
    
    @Override
    public void applyEntityAttributes () {
        
        super.applyEntityAttributes();
        Config.netherKnight.apply(this);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        
        if (super.attackEntityFrom(source, amount)) {
            
            EntityLivingBase target = this.getAttackTarget();
            
            // If the mob isn't targeting anyone, try to target the attacker.
            if (target == null && source.getTrueSource() instanceof EntityLivingBase) {
                
                target = (EntityLivingBase) source.getTrueSource();
            }
            
            // If conditions are right, try to spawn reinforcements
            if (target != null && MathsUtils.tryPercentage(Config.netherKnight.getReinforcementChance())) {
                
                for (int attempt = 0; attempt < 25; attempt++) {
                    
                    int[] range = Config.netherKnight.getSpawnRange();
                    final int spawnOffsetX = MathHelper.getInt(this.rand, range[0], range[1]) * MathHelper.getInt(this.rand, -1, 1);
                    final int spawnOffsetZ = MathHelper.getInt(this.rand, range[0], range[1]) * MathHelper.getInt(this.rand, -1, 1);                    
                    final BlockPos spawnPos = this.getPosition().add(spawnOffsetX, -1, spawnOffsetZ);
                    final IBlockState state = this.world.getBlockState(spawnPos);
                    
                    if (state != null && state.isSideSolid(this.world, spawnPos, EnumFacing.UP) && this.world.isAirBlock(spawnPos.up(2))) {
                        
                        try {
                            
                            EntityLiving reinforcement = (EntityLiving) EntityList.createEntityByIDFromName(Config.netherKnight.getReinforcementIDs()[Constants.RANDOM.nextInt(Config.netherKnight.getReinforcementIDs().length)], this.world);
                            reinforcement.setPositionAndUpdate(spawnPos.getX() + 0.5f, this.posY, spawnPos.getZ() + 0.5f);
                            reinforcement.setAttackTarget(target);
                            this.world.spawnEntity(reinforcement);
                            reinforcement.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(BUFF_ARMOR);
                            reinforcement.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(BUFF_HEALTH);
                            reinforcement.setHealth(reinforcement.getMaxHealth());
                        }
                        
                        catch (Exception e) {
                            
                            EerieEntities.LOG.catching(e);
                        }
                        
                        break;
                    }
                }
            }
            
            return true;
        }
        
        return false;
    }
}