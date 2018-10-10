package com.jarhax.eerieentities;

import java.io.File;

public class Configuration extends net.minecraftforge.common.config.Configuration{
    
    public Configuration(String file) {
        
        super(new File("config/" + file + ".cfg"));
    }
    
    public int[] getRange(String name, String category, int min, int max, int lowest, int highest, String comment) {
        
        final int[] values = new int[2];
        values[0] = this.getInt("min" + name, category, min, lowest, highest, "The min value for " + comment);
        values[1] = this.getInt("max" + name, category, max, lowest, highest, "The max value for " + comment);
        return values;
    }
    
    public double getDouble(String name, String category, double defaultValue, double minValue, double maxValue, String comment) {
        
        return this.getFloat(name, category, (float) defaultValue, (float) minValue, (float) maxValue, comment);
    }
}