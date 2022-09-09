package io.github.Leonardo0013YT.UltraSkyWars.addons;

import me.neznamy.tab.api.EnumProperty;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TabAPI {

    private HashMap<UUID, HashMap<EnumProperty, String>> nametags = new HashMap<>();

    public void addPlayerNameTag(Player p) {
        for (EnumProperty ep : EnumProperty.values()) {
            nametags.putIfAbsent(p.getUniqueId(), new HashMap<>());
            if (TABAPI.hasTemporaryValue(p.getUniqueId(), ep)) {
                String temp = TABAPI.getTemporaryValue(p.getUniqueId(), ep);
                if (temp != null) {
                    nametags.get(p.getUniqueId()).put(ep, temp);
                    TABAPI.setValueTemporarily(p.getUniqueId(), ep, "");
                }
            }
        }
    }

    public void removePlayerNameTag(Player p) {
        nametags.remove(p.getUniqueId());
    }

    public void resetPlayerNameTag(Player p) {
        if (nametags.containsKey(p.getUniqueId())) {
            for (EnumProperty ep : nametags.get(p.getUniqueId()).keySet()) {
                TABAPI.setValueTemporarily(p.getUniqueId(), ep, nametags.get(p.getUniqueId()).get(ep));
            }
        }
        removePlayerNameTag(p);
    }

    public String getPrefix(Player p) {
        if (nametags.containsKey(p.getUniqueId())) {
            if (nametags.get(p.getUniqueId()).containsKey(EnumProperty.TABPREFIX)) {
                return nametags.get(p.getUniqueId()).get(EnumProperty.TABPREFIX);
            }
        }
        return "";
    }

    public String getSuffix(Player p) {
        if (nametags.containsKey(p.getUniqueId())) {
            if (nametags.get(p.getUniqueId()).containsKey(EnumProperty.TABSUFFIX)) {
                return nametags.get(p.getUniqueId()).get(EnumProperty.TABSUFFIX);
            }
        }
        return "";
    }

}