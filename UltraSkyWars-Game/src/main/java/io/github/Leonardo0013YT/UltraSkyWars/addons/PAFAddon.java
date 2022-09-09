package io.github.Leonardo0013YT.UltraSkyWars.addons;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PAFAddon {

    public boolean isInParty(Player p) {
        PAFPlayer paf = PAFPlayerManager.getInstance().getPlayer(p.getUniqueId());
        if (paf != null) {
            PartyManager pm = PartyManager.getInstance();
            return pm.getParty(paf) != null;
        }
        return false;
    }

    public boolean isPartyLeader(Player p) {
        PAFPlayer paf = PAFPlayerManager.getInstance().getPlayer(p.getUniqueId());
        if (paf != null) {
            PartyManager pm = PartyManager.getInstance();
            if (pm.getParty(paf) != null) {
                return pm.getParty(paf).isLeader(paf);
            }
        }
        return false;
    }

    public List<Player> getPlayersParty(Player leader, String server) {
        List<Player> online = new ArrayList<>();
        PAFPlayer paf = PAFPlayerManager.getInstance().getPlayer(leader.getUniqueId());
        if (paf != null) {
            PartyManager pm = PartyManager.getInstance();
            if (pm.getParty(paf) != null) {
                PlayerParty pp = pm.getParty(paf);
                for (PAFPlayer pa : pp.getAllPlayers()) {
                    if (UltraSkyWars.get().getCm().isBungeeModeEnabled()) {
                        UltraSkyWars.get().getBm().sendMessage("usw:party", "SEND:" + pa.getUniqueId().toString() + ":" + server);
                    } else {
                        Player on = Bukkit.getPlayer(pa.getUniqueId());
                        if (on != null) {
                            online.add(on);
                        }
                    }
                }
            }
        }
        return online;
    }

}