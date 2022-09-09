package io.github.Leonardo0013YT.UltraSkyWars.addons.nametags;

import be.maximvdw.animatednames.api.AnimatedNamesAPI;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.NametagAddon;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AnimatedNamesAddon implements NametagAddon {

    private final HashMap<UUID, String> playerTeam = new HashMap<>();

    @Override
    public void addPlayerNameTag(Player p) {
        playerTeam.put(p.getUniqueId(), AnimatedNamesAPI.getTeamName(p));
        AnimatedNamesAPI.resetDefaultTeam(p);
    }

    @Override
    public void removePlayerNameTag(Player p) {
        playerTeam.remove(p.getUniqueId());
    }

    @Override
    public void resetPlayerNameTag(Player p) {
        if (playerTeam.containsKey(p.getUniqueId())) {
            AnimatedNamesAPI.removeTeamOverride(p, playerTeam.get(p.getUniqueId()));
            AnimatedNamesAPI.resetDefaultTeam(p);
            removePlayerNameTag(p);
        }
    }

    @Override
    public String getPrefix(Player p) {
        return "";
    }

    @Override
    public String getSuffix(Player p) {
        return "";
    }

}
