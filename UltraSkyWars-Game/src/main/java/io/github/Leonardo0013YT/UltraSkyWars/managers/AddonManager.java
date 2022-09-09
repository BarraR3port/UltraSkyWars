package io.github.Leonardo0013YT.UltraSkyWars.managers;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.addons.*;
import io.github.Leonardo0013YT.UltraSkyWars.addons.holograms.HologramsAddon;
import io.github.Leonardo0013YT.UltraSkyWars.addons.holograms.HolographicDisplaysAddon;
import io.github.Leonardo0013YT.UltraSkyWars.addons.holograms.TrHologramAddon;
import io.github.Leonardo0013YT.UltraSkyWars.addons.nametags.AnimatedNamesAddon;
import io.github.Leonardo0013YT.UltraSkyWars.addons.nametags.NametagEditAddon;
import io.github.Leonardo0013YT.UltraSkyWars.addons.nametags.TabAPI;
import io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.HologramAddon;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.NametagAddon;
import io.github.Leonardo0013YT.UltraSkyWars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AddonManager {

    private UltraSkyWars plugin;
    private CoinsAddon coins;
    private VaultAddon vault;
    private PlayerPointsAddon points;
    private HologramAddon ha;
    private NametagAddon tag;
    private PartiesAddon parties;
    private PlaceholderAPIAddon placeholder;
    private PAFAddon paf;
    private SlimeWorldManagerAddon slime;
    private MVdWPlaceholderAPIAddon mvdw;
    private boolean balancerRegistered = false;

    public AddonManager(UltraSkyWars plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        if (plugin.getCm().isPlayerBalancerEnabled()) {
            balancerRegistered = true;
            plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "playerbalancer:main");
            plugin.sendLogMessage("Hooked into §aPlayerBalancer§e!");
        }
        if (plugin.getCm().isPartyandfriends()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PartyAndFriendsGUI") || Bukkit.getPluginManager().isPluginEnabled("PartyAndFriends")) {
                paf = new PAFAddon();
                plugin.sendLogMessage("Hooked into §aPartyAndFriends§e!");
            } else {
                plugin.getConfig().set("addons.partyandfriends", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isSlimeworldmanager()) {
            if (Bukkit.getPluginManager().isPluginEnabled("SlimeWorldManager")) {
                slime = new SlimeWorldManagerAddon(plugin);
                plugin.sendLogMessage("Hooked into §aSlimeWorldManager§e!");
            } else {
                plugin.getConfig().set("addons.slimeworldmanager.enabled", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isMVdWPlaceholderAPI()) {
            if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                mvdw = new MVdWPlaceholderAPIAddon();
                plugin.sendLogMessage("Hooked into §aMVdWPlaceholderAPI§e!");
            } else {
                plugin.getConfig().set("addons.MVdWPlaceholderAPI", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isPlaceholdersAPI()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholder = new PlaceholderAPIAddon();
                plugin.sendLogMessage("Hooked into §aPlaceholderAPI§e!");
            } else {
                plugin.getConfig().set("addons.placeholdersAPI", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isAnimatedNames()) {
            if (Bukkit.getPluginManager().isPluginEnabled("AnimatedNames")) {
                tag = new AnimatedNamesAddon();
                plugin.sendLogMessage("Hooked into §aAnimatedNames§e!");
            } else {
                plugin.getConfig().set("addons.AnimatedNames", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isTabapi()) {
            if (Bukkit.getPluginManager().isPluginEnabled("TAB")) {
                tag = new TabAPI();
                plugin.sendLogMessage("Hooked into §aTAB§e!");
            } else {
                plugin.getConfig().set("addons.tabapi", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isParties()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Parties")) {
                parties = new PartiesAddon();
                plugin.sendLogMessage("Hooked into §aParties§e!");
            } else {
                plugin.getConfig().set("addons.parties", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isNametagedit()) {
            if (Bukkit.getPluginManager().isPluginEnabled("NametagEdit")) {
                tag = new NametagEditAddon();
                plugin.sendLogMessage("Hooked into §aNametagEdit§e!");
            } else {
                plugin.getConfig().set("addons.nametagedit", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isVault()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                vault = new VaultAddon();
                plugin.sendLogMessage("Hooked into §aVault§e!");
            } else {
                plugin.getConfig().set("addons.vault", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isPlayerpoints()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
                points = new PlayerPointsAddon();
                plugin.sendLogMessage("Hooked into §aPlayerPoints§e!");
            } else {
                plugin.getConfig().set("addons.playerpoints", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isCoins()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Coins")) {
                coins = new CoinsAddon();
                plugin.sendLogMessage("Hooked into §aCoins§e!");
            } else {
                plugin.getConfig().set("addons.coins", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.getCm().isTrholograms()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("TrHologram")) {
                        remove();
                        ha = new TrHologramAddon();
                        plugin.sendLogMessage("Hooked into §aTrHologram§e!");
                    } else {
                        plugin.getConfig().set("addons.trholograms", false);
                        plugin.saveConfig();
                        plugin.getCm().reload();
                    }
                    reloadHologram();
                }
                if (plugin.getCm().isHolograms()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("Holograms")) {
                        remove();
                        ha = new HologramsAddon();
                        plugin.sendLogMessage("Hooked into §aHolograms§e!");
                    } else {
                        plugin.getConfig().set("addons.holograms", false);
                        plugin.saveConfig();
                        plugin.getCm().reload();
                    }
                    reloadHologram();
                }
                if (plugin.getCm().isHolographicdisplays()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
                        remove();
                        ha = new HolographicDisplaysAddon();
                        plugin.sendLogMessage("Hooked into §aHolographicDisplays§e!");
                    } else {
                        plugin.getConfig().set("addons.holographicdisplays", false);
                        plugin.saveConfig();
                        plugin.getCm().reload();
                    }
                    reloadHologram();
                }
            }
        }.runTaskLater(plugin, 80);
    }

    public void reloadHologram() {
        if (plugin.getIjm().isSoulWellInjection()) {
            plugin.getIjm().getSoulwell().getSwm().reload();
        }
        if (plugin.getIjm().isCubeletsInjection()) {
            plugin.getIjm().getCubelets().getCbm().reload();
        }
    }

    public PAFAddon getPaf() {
        return paf;
    }

    public PartiesAddon getParties() {
        return parties;
    }

    public void addCoins(Player p, double amount) {
        if (plugin.getCm().isVault()) {
            vault.addCoins(p, amount);
        } else if (plugin.getCm().isPlayerpoints()) {
            points.addCoins(p, amount);
        } else if (plugin.getCm().isCoins()) {
            coins.addCoins(p, amount);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            if (sw == null) return;
            sw.addCoins((int) amount);
        }
        Utils.updateSB(p);
    }

    public void setCoins(Player p, double amount) {
        if (plugin.getCm().isVault()) {
            vault.setCoins(p, amount);
        } else if (plugin.getCm().isPlayerpoints()) {
            points.setCoins(p, amount);
        } else if (plugin.getCm().isCoins()) {
            coins.setCoins(p, amount);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            if (sw == null) return;
            sw.setCoins((int) amount);
        }
        Utils.updateSB(p);
    }

    public void removeCoins(Player p, double amount) {
        if (plugin.getCm().isVault()) {
            vault.removeCoins(p, amount);
        } else if (plugin.getCm().isPlayerpoints()) {
            points.removeCoins(p, amount);
        } else if (plugin.getCm().isCoins()) {
            coins.removeCoins(p, amount);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            if (sw == null) return;
            sw.removeCoins((int) amount);
        }
        Utils.updateSB(p);
    }

    public double getCoins(Player p) {
        if (plugin.getCm().isVault()) {
            return vault.getCoins(p);
        } else if (plugin.getCm().isPlayerpoints()) {
            return points.getCoins(p);
        } else if (plugin.getCm().isCoins()) {
            return coins.getCoins(p);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            if (sw == null) {
                return 0;
            }
            return sw.getCoins();
        }
    }

    public void createHologram(Location spawn, List<String> lines) {
        if (ha != null) {
            ha.createHologram(spawn, lines);
        }
    }

    public void createHologram(Location spawn, List<String> lines, ItemStack item) {
        if (ha != null) {
            ha.createHologram(spawn, lines, item);
        }
    }

    public void remove() {
        if (ha != null) {
            ha.remove();
        }
    }

    public void deleteHologram(Location spawn) {
        if (ha != null) {
            ha.deleteHologram(spawn);
        }
    }

    public boolean hasHologram(Location spawn) {
        if (ha != null) {
            return ha.hasHologram(spawn);
        }
        return false;
    }

    public void addPlayerNameTag(Player p) {
        if (tag != null) {
            tag.addPlayerNameTag(p);
        }
    }

    public void resetPlayerNameTag(Player p) {
        if (tag != null) {
            tag.resetPlayerNameTag(p);
        }
    }

    public String getPlayerPrefix(Player p) {
        if (tag != null) {
            return tag.getPrefix(p);
        }
        return "";
    }

    public String getPlayerSuffix(Player p) {
        if (tag != null) {
            return tag.getSuffix(p);
        }
        return "";
    }

    public String parsePlaceholders(Player p, String value) {
        if (plugin.getCm().isPlaceholdersAPI()) {
            return placeholder.parsePlaceholders(p, value);
        }
        if (plugin.getCm().isMVdWPlaceholderAPI()) {
            return mvdw.parsePlaceholders(p, value);
        }
        return value;
    }

    public SlimeWorldManagerAddon getSlime() {
        return slime;
    }

    public boolean isBalancerRegistered() {
        return balancerRegistered;
    }

    public boolean hasHologramPlugin() {
        return !(ha == null);
    }

}