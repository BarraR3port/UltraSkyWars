package io.github.Leonardo0013YT.UltraSkyWars.team;

import lombok.Getter;

public class TeamStats {
    
    @Getter
    private final int teamSize, chests;
    @Getter
    private int potions, blocks, projectiles, others;
    
    public TeamStats(int teamSize, int chests) {
        this.teamSize = teamSize;
        this.chests = chests;
        potions = 0;
        blocks = 0;
        projectiles = 0;
        others = 0;
    }
    
    
    public void addProjectiles() {
        projectiles++;
    }
    
    public void addPotions() {
        potions++;
    }
    
    public void addBlocks() {
        blocks++;
    }
    
    public void addOthers() {
        others++;
    }
    
    public boolean areProjectilesAvailable() {
        if(teamSize == 0) return true;
        return projectiles <= (teamSize * chests);
    }
    
    public boolean arePotionsAvailable() {
        if(teamSize == 0) return true;
        return potions <= teamSize;
    }
    
    public boolean areBlocksAvailable() {
        if(teamSize == 0) return true;
        return blocks <= (teamSize * chests);
    }
    
    public boolean areOthersAvailable() {
        if(teamSize == 0) return true;
        return others <= (teamSize * 2 * chests);
    }
    
    
    public void reset() {
        potions = 0;
        blocks = 0;
        others = 0;
    }
    
    
}
