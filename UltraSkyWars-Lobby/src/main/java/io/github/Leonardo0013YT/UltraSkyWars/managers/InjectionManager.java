package io.github.Leonardo0013YT.UltraSkyWars.managers;

import io.github.Leonardo0013YT.UltraSkyWars.LProtection.InjectionLProtection;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.challenges.InjectionChallenges;
import io.github.Leonardo0013YT.UltraSkyWars.soulwell.InjectionSoulWell;
import io.github.Leonardo0013YT.cubelets.InjectionCubelets;
import io.github.Leonardo0013YT.parties.InjectionParty;
import io.github.Leonardo0013YT.perks.InjectionPerks;
import io.github.Leonardo0013YT.pwt.InjectionPWT;
import io.github.Leonardo0013YT.ranks.InjectionEloRank;
import io.github.Leonardo0013YT.signs.InjectionSigns;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class InjectionManager {

    private UltraSkyWars plugin;
    private InjectionEloRank eloRank;
    private InjectionPerks perks;
    private InjectionCubelets cubelets;
    private InjectionSigns signs;
    private InjectionPWT pwts;
    private InjectionLProtection lProtection;
    private InjectionChallenges challenges;
    private InjectionParty party;
    private InjectionSoulWell soulwell;
    private File injections;

    public InjectionManager(UltraSkyWars plugin) {
        this.plugin = plugin;
        injections = new File(plugin.getDataFolder(), "injections");
        if (!injections.exists()) {
            injections.mkdirs();
        }
    }

    public void loadWEInjection() {
        File we;
        if (plugin.getVc().is1_13to16()) {
            we = new File(injections, "UltraSkyWars-WENew.jar");
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aWorldEdit §71.13 - 1.16 §eloaded correctly!");
        } else {
            we = new File(injections, "UltraSkyWars-WEOld.jar");
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aWorldEdit §71.8.8 - 1.12 §eloaded correctly!");
        }
        if (!we.exists()) {
            plugin.sendLogMessage("§cYou must put which version of WorldEdit Addon you'll be using,", "§cuse the UltraSkyWars-WEOld.jar injection for WorldEdit 1.8 through 1.12", "§cand UltraSkyWars-WENew.jar for WorldEdit 1.13 through 1.15.");
            Bukkit.getScheduler().cancelTasks(plugin);
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }
        loadJarFile(we);
    }

    public void loadInjections() {
        File lsoulwell = new File(injections, "UltraSkyWars-SoulWell.jar");
        if (lsoulwell.exists()) {
            loadJarFile(lsoulwell);
            soulwell = new InjectionSoulWell();
            soulwell.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aSoulWell§e loaded correctly!");
        }
        File lpartys = new File(injections, "UltraSkyWars-Parties.jar");
        if (lpartys.exists()) {
            loadJarFile(lpartys);
            party = new InjectionParty();
            party.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aParties§e loaded correctly!");
        }
        File lprotect = new File(injections, "UltraSkyWars-LobbyProtect.jar");
        if (lprotect.exists()) {
            loadJarFile(lprotect);
            lProtection = new InjectionLProtection();
            lProtection.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aLobbyProtection§e loaded correctly!");
        }
        File eloRanks = new File(injections, "UltraSkyWars-EloRanks.jar");
        if (eloRanks.exists()) {
            loadJarFile(eloRanks);
            eloRank = new InjectionEloRank();
            eloRank.loadInjection(plugin);
            if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                eloRank.loadPlaceholders();
            }
            if (plugin.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                eloRank.loadMVdWPlaceholders();
            }
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aEloRanks§e loaded correctly!");
        }
        File perk = new File(injections, "UltraSkyWars-Perks.jar");
        if (perk.exists()) {
            loadJarFile(perk);
            perks = new InjectionPerks();
            perks.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aPerks§e loaded correctly!");
        }
        File cubelet = new File(injections, "UltraSkyWars-Cubelets.jar");
        if (cubelet.exists()) {
            loadJarFile(cubelet);
            cubelets = new InjectionCubelets();
            cubelets.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aCubelets§e loaded correctly!");
        }
        File sign = new File(injections, "UltraSkyWars-Signs.jar");
        if (sign.exists()) {
            loadJarFile(sign);
            signs = new InjectionSigns();
            signs.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aSigns§e loaded correctly!");
        }
        File pwt = new File(injections, "UltraSkyWars-PWT.jar");
        if (pwt.exists()) {
            loadJarFile(pwt);
            pwts = new InjectionPWT();
            pwts.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraSkyWars §aPer World Tab§e loaded correctly!");
        }
        plugin.getCm().reloadInjections();
    }

    public void reload() {
        File lsoulwell = new File(injections, "UltraSkyWars-SoulWell.jar");
        if (soulwell == null) {
            if (lsoulwell.exists()) {
                loadJarFile(lsoulwell);
                soulwell = new InjectionSoulWell();
                soulwell.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aSoulWell §e loaded correctly!");
            }
        } else {
            if (!lsoulwell.exists()) {
                soulwell = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aSoulWell §c unloaded correctly!");
            } else {
                soulwell.reload();
            }
        }
        File lpartys = new File(injections, "UltraSkyWars-Parties.jar");
        if (party == null) {
            if (lpartys.exists()) {
                loadJarFile(lpartys);
                party = new InjectionParty();
                party.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aParties §e loaded correctly!");
            }
        } else {
            if (!lpartys.exists()) {
                party = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aParties §c unloaded correctly!");
            } else {
                party.reload();
            }
        }
        File lprotect = new File(injections, "UltraSkyWars-LobbyProtect.jar");
        if (lProtection == null) {
            if (lprotect.exists()) {
                loadJarFile(lprotect);
                lProtection = new InjectionLProtection();
                lProtection.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aLobbyProtection §e loaded correctly!");
            }
        } else {
            if (!lprotect.exists()) {
                lProtection = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aLobbyProtection §c unloaded correctly!");
            } else {
                lProtection.reload();
            }
        }
        File pwt = new File(injections, "UltraSkyWars-PWT.jar");
        if (pwts == null) {
            if (pwt.exists()) {
                loadJarFile(pwt);
                pwts = new InjectionPWT();
                pwts.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aPer World Tab§e loaded correctly!");
            }
        } else {
            if (!pwt.exists()) {
                pwts = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aPer World Tab§c unloaded correctly!");
            } else {
                pwts.reload();
            }
        }
        File sign = new File(injections, "UltraSkyWars-Signs.jar");
        if (signs == null) {
            if (sign.exists()) {
                loadJarFile(sign);
                signs = new InjectionSigns();
                signs.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aSigns§e loaded correctly!");
            }
        } else {
            if (!sign.exists()) {
                signs = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aSigns§c unloaded correctly!");
            } else {
                signs.reload();
            }
        }
        File eloRanks = new File(injections, "UltraSkyWars-EloRanks.jar");
        if (eloRank == null) {
            if (eloRanks.exists()) {
                loadJarFile(eloRanks);
                eloRank = new InjectionEloRank();
                eloRank.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aEloRanks§e loaded correctly!");
            }
        } else {
            if (!eloRanks.exists()) {
                eloRank = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aEloRanks§c unloaded correctly!");
            } else {
                eloRank.reload();
            }
        }
        File perk = new File(injections, "UltraSkyWars-Perks.jar");
        if (perks == null) {
            if (perk.exists()) {
                loadJarFile(perk);
                perks = new InjectionPerks();
                perks.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aPerks§e loaded correctly!");
            }
        } else {
            if (!perk.exists()) {
                perks = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aPerks§c unloaded correctly!");
            } else {
                perks.reload();
            }
        }
        File cubelet = new File(injections, "UltraSkyWars-Cubelets.jar");
        if (cubelets == null) {
            if (cubelet.exists()) {
                loadJarFile(cubelet);
                cubelets = new InjectionCubelets();
                cubelets.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraSkyWars §aCubelets§e loaded correctly!");
            }
        } else {
            if (!cubelet.exists()) {
                cubelets = null;
                plugin.sendLogMessage("§cInjection §bUltraSkyWars §aCubelets§c unloaded correctly!");
            } else {
                cubelets.reload();
            }
        }
        plugin.getCm().reloadInjections();
    }

    public boolean isSoulWellInjection() {
        return soulwell != null;
    }

    public boolean isEloRankInjection() {
        return eloRank != null;
    }

    public boolean isPerksInjection() {
        return perks != null;
    }

    public boolean isCubeletsInjection() {
        return cubelets != null;
    }

    public boolean isSignsInjection() {
        return signs != null;
    }

    public boolean isChallenges() {
        return challenges != null;
    }

    public boolean isParty() {
        return party != null;
    }

    public InjectionChallenges getChallenges() {
        return challenges;
    }

    public InjectionSigns getSigns() {
        return signs;
    }

    public InjectionCubelets getCubelets() {
        return cubelets;
    }

    public InjectionEloRank getEloRank() {
        return eloRank;
    }

    public InjectionPerks getPerks() {
        return perks;
    }

    public InjectionParty getParty() {
        return party;
    }

    public InjectionSoulWell getSoulwell() {
        return soulwell;
    }

    private void loadJarFile(File jar) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Class<?> getClass = classLoader.getClass();
            Method method = getClass.getSuperclass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, jar.toURI().toURL());
        } catch (NoSuchMethodException | MalformedURLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}