package com.jarhax.eerieentities.entities;

import net.darkhax.bookshelf.lib.WeightedSelector;

public enum WispType {
    
    BLUE(10, 0, 1, 1),
    GREEN(3, 0, 1, 0),
    YELLOW(2, 1, 1, 0),
    PURPLE(1, 1, 0, 1);
    
    public static WeightedSelector<WispType> selector = new WeightedSelector<>();
    
    static {
        
        for (WispType type : WispType.values()) {
            
            selector.addEntry(type, type.weight);
        }
    }
    
    private final int weight;
    private final int red;
    private final int green;
    private final int blue;
    
    WispType(int weight, int red, int green, int blue) {
        
        this.weight = weight;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed () {
        
        return red;
    }

    public int getGreen () {
        
        return green;
    }

    public int getBlue () {
        
        return blue;
    }
}