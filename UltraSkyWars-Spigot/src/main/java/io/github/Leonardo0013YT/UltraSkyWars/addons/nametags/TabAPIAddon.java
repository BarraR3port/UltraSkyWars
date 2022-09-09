package io.github.Leonardo0013YT.UltraSkyWars.addons.nametags;

import io.github.Leonardo0013YT.UltraSkyWars.interfaces.NametagAddon;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.TablistFormatManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TabAPIAddon implements NametagAddon {
    private final HashMap<UUID, String> prefix = new HashMap<>();
    private final HashMap<UUID, String> suffix = new HashMap<>();
    private final TablistFormatManager manager = TabAPI.getInstance().getTablistFormatManager();
    
    @Override
    public void addPlayerNameTag(Player p) {
        TabPlayer tab = TabAPI.getInstance().getPlayer(p.getUniqueId());
        TabAPI.getInstance().getTablistFormatManager().getCustomPrefix(tab);
        if(tab != null){
            prefix.put(p.getUniqueId(), manager.getCustomPrefix(tab));
            suffix.put(p.getUniqueId(), manager.getCustomSuffix(tab));
        }
    }

    @Override
    public void removePlayerNameTag(Player p) {
        prefix.remove(p.getUniqueId());
        suffix.remove(p.getUniqueId());
    }

    @Override
    public void resetPlayerNameTag(Player p) {
        TabPlayer tab = TabAPI.getInstance().getPlayer(p.getUniqueId());
        manager.setPrefix(tab, prefix.get(p.getUniqueId()));
        manager.setSuffix(tab, suffix.get(p.getUniqueId()));
        removePlayerNameTag(p);
    }

    @Override
    public String getPrefix(Player p) {
        TabPlayer tab = TabAPI.getInstance().getPlayer(p.getUniqueId());
        return manager.getCustomPrefix(tab);
    }

    @Override
    public String getSuffix(Player p) {
        TabPlayer tab = TabAPI.getInstance().getPlayer(p.getUniqueId());
        return manager.getCustomSuffix(tab);
    }

}