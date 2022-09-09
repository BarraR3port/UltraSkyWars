package io.github.Leonardo0013YT.UltraSkyWars.addons;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class MVdWPlaceholderAPIAddon {

    public String parsePlaceholders(Player p, String value) {
        return PlaceholderAPI.replacePlaceholders(p, value);
    }

}