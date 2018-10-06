package com.jarhax.spooky.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MobConfig {
    
    public static final List<MobConfig> mobConfigs = new ArrayList<>();
    private static final Map<String, Type> byName = ReflectionHelper.getPrivateValue(BiomeDictionary.Type.class, null, "byName");
    
    private final String name;
    private final Class<? extends EntityLiving> entClass;
    private final EnumCreatureType mobType;
    
    private final double maxHealthDefault;
    private final double speedDefault;
    private final double armorDefault;
    private final double attackDefault;
    private final int maxInChunkDefault;
    private final int minPackSizeDefault;
    private final int maxPackSizeDefault;
    private final int spawnWeightDefault;
    private final String[] biomesDefault;
    
    private double maxHealth;
    private double speed;
    private double armor;
    private double attack;
    private int maxInChunk;
    private int minPackSize;
    private int maxPackSize;
    private int spawnWeight;
    private String[] biomes;
    
    public MobConfig(String name, Class<? extends EntityLiving> entClass, EnumCreatureType mobType, double maxHealthDefault, double speedDefault, double armorDefault, double attackDefault, int maxInChunkDefault, int minPackSizeDefault, int maxPackSizeDefault, int spawnWeightDefault, String... biomesDefault) {
        
        this.name = name;
        this.entClass = entClass;
        this.mobType = mobType;
        
        this.maxHealthDefault = maxHealthDefault;
        this.speedDefault = speedDefault;
        this.armorDefault = armorDefault;
        this.attackDefault = attackDefault;
        this.maxInChunkDefault = maxInChunkDefault;
        this.minPackSizeDefault = minPackSizeDefault;
        this.maxPackSizeDefault = maxPackSizeDefault;
        this.spawnWeightDefault = spawnWeightDefault;
        this.biomesDefault = biomesDefault;
        
        mobConfigs.add(this);
    }
    
    public void apply (EntityLivingBase entity) {
        
        this.setAttribute(entity, SharedMonsterAttributes.MAX_HEALTH, this.getMaxHealth());
        this.setAttribute(entity, SharedMonsterAttributes.MOVEMENT_SPEED, this.getSpeed());
        this.setAttribute(entity, SharedMonsterAttributes.ARMOR, this.getArmor());
        this.setAttribute(entity, SharedMonsterAttributes.ATTACK_DAMAGE, this.getAttack());
    }
    
    private void setAttribute (EntityLivingBase entity, IAttribute type, double amount) {
        
        IAttributeInstance attribute = entity.getEntityAttribute(type);
        
        if (attribute == null) {
            
            entity.getAttributeMap().registerAttribute(type);
            attribute = entity.getEntityAttribute(type);
        }
        
        if (attribute != null) {
            
            attribute.setBaseValue(amount);
        }
    }
    
    public void insertSpawns () {
        
        if (this.biomesDefault.length > 0 && this.getSpawnWeight() != 0) {
            
            for (final String biomeKey : this.biomes) {
                
                // This is a biome dictionary entry.
                if (biomeKey.startsWith("type=")) {
                    
                    final Type biomeType = byName.get(biomeKey.substring(5));
                    
                    if (biomeType != null) {
                        
                        for (final Biome biome : BiomeDictionary.getBiomes(biomeType)) {
                            
                            this.addSpawnToBiome(biome);
                        }
                    }
                }
                
                else {
                    
                    final Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeKey));
                    
                    if (biome != null) {
                        
                        this.addSpawnToBiome(biome);
                    }
                }
            }
        }
    }
    
    private void addSpawnToBiome (Biome biome) {
        
        biome.getSpawnableList(this.mobType).add(new SpawnListEntry(this.entClass, this.getSpawnWeight(), this.getMinPackSize(), this.getMaxPackSize()));
    }
    
    public void syncConfig (Configuration config) {
        
        this.maxHealth = config.getFloat("health", this.name, (float) this.maxHealthDefault, 0f, 1024f, "The maximum health for this mob.");
        this.speed = config.getFloat("speed", this.name, (float) this.speedDefault, 0f, 1024f, "The general movement speed for this mob.");
        this.armor = config.getFloat("armor", this.name, (float) this.armorDefault, 0f, 1024f, "The amount of armor protection this mob has by defualt.");
        this.attack = config.getFloat("attack", this.name, (float) this.attackDefault, 0f, 1034f, "The base amount of attack damage for this mob.");
        
        this.maxInChunk = config.getInt("maxInChunk", this.name, this.maxInChunkDefault, 0, 1024, "The maximum amount of this mob to spawn in one chunk.");
        this.minPackSize = config.getInt("minPackSize", this.name, this.minPackSizeDefault, 1, 128, "The minimum amount to spawn in one pack.");
        this.maxPackSize = config.getInt("maxPackSize", this.name, this.maxPackSizeDefault, 1, 128, "The maximum amount to spawn in one pack.");
        this.spawnWeight = config.getInt("spawnWeight", this.name, this.spawnWeightDefault, 0, 1024, "The spawning weight of this mob.");
        
        this.biomes = config.getStringList("biomes", this.name, this.biomesDefault, "The biomes this mob can spawn in. Use biome ID such as minecraft:ocean for specific biomes, and type=TYPE for biome dict support.");
    }
    
    public String getName () {
        
        return this.name;
    }
    
    public double getMaxHealth () {
        
        return this.maxHealth;
    }
    
    public double getSpeed () {
        
        return this.speed;
    }
    
    public double getArmor () {
        
        return this.armor;
    }
    
    public double getAttack () {
        
        return this.attack;
    }
    
    public int getMaxInChunk () {
        
        return this.maxInChunk;
    }
    
    public int getMinPackSize () {
        
        return this.minPackSize;
    }
    
    public int getMaxPackSize () {
        
        return this.maxPackSize;
    }
    
    public int getSpawnWeight () {
        
        return this.spawnWeight;
    }
}