package io.github.Leonardo0013YT.UltraSkyWars.addons;

import com.nametagedit.plugin.NametagEdit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class NametagEditAddon {

    private HashMap<UUID, String> prefix = new HashMap<>();
    private HashMap<UUID, String> suffix = new HashMap<>();

    public void addPlayerNameTag(Player p) {
        prefix.put(p.getUniqueId(), NametagEdit.getApi().getNametag(p).getPrefix());
        suffix.put(p.getUniqueId(), NametagEdit.getApi().getNametag(p).getSuffix());
    }

    public void removePlayerNameTag(Player p) {
        prefix.remove(p.getUniqueId());
        suffix.remove(p.getUniqueId());
    }

    public void resetPlayerNameTag(Player p) {
        NametagEdit.getApi().clearNametag(p);
        NametagEdit.getApi().reloadNametag(p);
        removePlayerNameTag(p);
    }

    public String getPrefix(Player p) {
        if (!prefix.containsKey(p.getUniqueId())) return "";
        return prefix.get(p.getUniqueId());
    }

    public String getSuffix(Player p) {
        if (!suffix.containsKey(p.getUniqueId())) return "";
        return suffix.get(p.getUniqueId());
    }

}