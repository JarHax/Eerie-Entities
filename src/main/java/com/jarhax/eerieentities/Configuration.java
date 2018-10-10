package com.jarhax.eerieentities;

import java.io.File;

import net.minecraft.util.ResourceLocation;

public class Configuration extends net.minecraftforge.common.config.Configuration {
    
    public Configuration(String file) {
        
        super(new File("config/" + file + ".cfg"));
    }
    
    public int[] getRange (String name, String category, int min, int max, int lowest, int highest, String comment) {
        
        final int[] values = new int[2];
        values[0] = this.getInt("min" + name, category, min, lowest, highest, "The min value for " + comment);
        values[1] = this.getInt("max" + name, category, max, lowest, highest, "The max value for " + comment);
        return values;
    }
    
    public double getDouble (String name, String category, double defaultValue, double minValue, double maxValue, String comment) {
        
        return this.getFloat(name, category, (float) defaultValue, (float) minValue, (float) maxValue, comment);
    }
    
    public ResourceLocation getResourceLocation (String name, String category, String defaultValue, String comment) {
        
        return new ResourceLocation(this.getString(name, category, defaultValue, comment));
    }
    
    public ResourceLocation[] getResourceLocationList (String name, String category, String[] defaultValues, String comment) {
        
        final String[] entries = this.getStringList(name, category, defaultValues, comment);
        final ResourceLocation[] locations = new ResourceLocation[entries.length];
        
        for (int i = 0; i < entries.length; i++) {
            
            locations[i] = new ResourceLocation(entries[i]);
        }
        
        return locations;
    }
}