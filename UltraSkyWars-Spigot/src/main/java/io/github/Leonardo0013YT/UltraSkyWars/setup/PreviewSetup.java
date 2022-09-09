package io.github.Leonardo0013YT.UltraSkyWars.setup;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.utils.Utils;
import org.bukkit.Location;

public class PreviewSetup {
    
    private final String type;
    private Location player, cosmetic;
    
    public PreviewSetup(String type) {
        this.type = type;
    }
    
    public void save() {
        UltraSkyWars plugin = UltraSkyWars.get();
        plugin.getConfig().set("previews." + type.toLowerCase() + ".player", Utils.getLocationString(player));
        plugin.getConfig().set("previews." + type.toLowerCase() + ".cosmetic", Utils.getLocationString(cosmetic));
        plugin.saveConfig();
        plugin.reload();
    }
    
    public String getType() {
        return type;
    }
    
    public Location getCosmetic() {
        return cosmetic;
    }
    
    public void setCosmetic(Location cosmetic) {
        this.cosmetic = cosmetic;
    }
    
    public Location getPlayer() {
        return player;
    }
    
    public void setPlayer(Location player) {
        this.player = player;
    }
}