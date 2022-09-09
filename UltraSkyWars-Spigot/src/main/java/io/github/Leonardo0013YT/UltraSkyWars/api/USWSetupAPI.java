package io.github.Leonardo0013YT.UltraSkyWars.api;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import org.bukkit.entity.Player;

public class USWSetupAPI {
    
    public static boolean isLobbySetup(Player p) {
        return UltraSkyWars.get().getSm().isSetupInventory(p);
    }
    
}