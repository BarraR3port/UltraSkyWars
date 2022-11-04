package io.github.Leonardo0013YT.UltraSkyWars.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum RewardType {
    
    COMMON(70),
    UNCOMMON(40),
    RARE(10),
    EPIC(1),
    LEGENDARY(0.1);
    private final double chance;
    
    RewardType(double chance){
        this.chance = chance;
    }
    
    public static RewardType getRandom(){
        return values()[ThreadLocalRandom.current().nextInt(values().length)];
    }
    
    public double getChance(){
        return ThreadLocalRandom.current().nextDouble(0, chance);
    }
}