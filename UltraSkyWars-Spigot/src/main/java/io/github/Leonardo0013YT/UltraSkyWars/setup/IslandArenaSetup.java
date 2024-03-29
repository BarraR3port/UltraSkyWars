package io.github.Leonardo0013YT.UltraSkyWars.setup;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IslandArenaSetup {
    
    private final Player p;
    private final int id;
    private final ArrayList<Location> chests = new ArrayList<>();
    private Location spawn, balloon, fence;
    
    public IslandArenaSetup(Player p, int id){
        this.p = p;
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public Player getP(){
        return p;
    }
    
    public Location getSpawn(){
        return spawn;
    }
    
    public void setSpawn(Location spawn){
        this.spawn = spawn;
    }
    
    public Location getFence(){
        return fence;
    }
    
    public void setFence(Location fence){
        this.fence = fence;
    }
    
    public Location getBalloon(){
        return balloon;
    }
    
    public void setBalloon(Location balloon){
        this.balloon = balloon;
    }
    
    public ArrayList<Location> getChests(){
        return chests;
    }
    
    public boolean isChest(Location l){
        return chests.contains(l);
    }
    
    public void addChest(Location l){
        chests.add(l);
    }
    
    public void removeChest(Location l){
        chests.remove(l);
    }
    
}