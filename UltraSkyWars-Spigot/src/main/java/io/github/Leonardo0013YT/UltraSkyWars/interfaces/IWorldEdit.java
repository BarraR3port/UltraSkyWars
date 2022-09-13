package io.github.Leonardo0013YT.UltraSkyWars.interfaces;

import io.github.Leonardo0013YT.UltraSkyWars.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraSkyWars.objects.GlassBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public interface IWorldEdit {
    
    void paste(Location loc, String schematic, boolean air, CallBackAPI<Boolean> done);
    
    HashMap<Vector, GlassBlock> getBlocks(String schematic);
    
    void changeSchematicColors(Player p, String schematic, boolean team);
}
