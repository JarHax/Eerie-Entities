package com.jarhax.eerieentities.entities;

import javax.annotation.Nullable;

import net.darkhax.bookshelf.lib.Constants;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityNetherKnight extends EntityBlaze {
    
    private static final DataParameter<Integer> RUNE_WORD = EntityDataManager.<Integer> createKey(EntityNetherKnight.class, DataSerializers.VARINT);
    public static final char[][] WORDS = {{68, 65, 82, 75}, {70, 73, 82, 69}, {71, 69, 71, 89}, {83, 65, 76, 84}, {67, 85, 78, 84}};
    
    public EntityNetherKnight(World world) {
        
        super(world);
    }
    
    public int getRuneWord () {
        
        return this.dataManager.get(RUNE_WORD).intValue();
    }
       
    public void setRuneWord (int value) {
        
        this.dataManager.set(RUNE_WORD, value);
    }
    
    @Override
    public void entityInit () {
        
        super.entityInit();
        this.dataManager.register(RUNE_WORD, 0);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        
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
    
    public char getRune(int index) {
        
        return index >= 0 && index < 4 ? WORDS[this.getRuneWord()][index] : 'X';
    }
}