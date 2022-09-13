package io.github.Leonardo0013YT.UltraSkyWars.game;

import io.github.Leonardo0013YT.UltraSkyWars.superclass.Game;
import org.bukkit.Location;
import org.bukkit.block.Chest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameChest {
    
    protected final Map<Location, UltraGameChest> invs = new HashMap<>();
    protected final List<Location> chests;
    
    public GameChest(List<Location> chests) {
        this.chests = chests;
        for ( Location l : chests ){
            if(l.getBlock().getState() instanceof Chest){
                invs.put(l.getBlock().getLocation(), new UltraGameChest(l));
            }
        }
    }
    
    public List<Location> getChests() {
        return chests;
    }
    
    public Map<Location, UltraGameChest> getInvs() {
        return invs;
    }
    
    public abstract void fill(Game game, boolean refill);
    
}