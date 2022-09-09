package io.github.Leonardo0013YT.UltraSkyWars.TNTMadness;

import io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.cmds.TNTMadnessCMD;
import io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.config.Settings;
import io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.listeners.TNTListener;
import io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.managers.ItemManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UltraSkyWarsTNTM extends JavaPlugin {

    private static UltraSkyWarsTNTM instance;
    private Settings tntLang;
    private ItemManager im;

    @Override
    public void onEnable() {
        instance = this;
        this.tntLang = new Settings(this, "tntLang", true, false);
        this.im = new ItemManager(this);
        getCommand("uswt").setExecutor(new TNTMadnessCMD(this));
        getServer().getPluginManager().registerEvents(new TNTListener(this), this);
    }

    @Override
    public void onDisable() {
        
    }
}