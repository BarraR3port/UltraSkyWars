package io.github.Leonardo0013YT.UltraSkyWars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.Leonardo0013YT.UltraSkyWars.data.DatabaseParser;
import io.github.Leonardo0013YT.UltraSkyWars.data.InterfaceAdapter;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.SWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UltraSkyWarsDataParser extends JavaPlugin {

    private DatabaseParser parser;
    private Gson gson;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        if (!getConfig().getBoolean("parsed")){
            parser = new DatabaseParser(this);
            this.gson = new GsonBuilder().registerTypeAdapter(SWPlayer.class, new InterfaceAdapter()).create();
            sendMessage("DATA PARSER ON... START IN A LITTLE...");
            new BukkitRunnable(){
                @Override
                public void run() {
                    parser.parse();
                }
            }.runTaskLater(this, 10 * 20);
            getConfig().set("parsed", true);
            saveConfig();
        } else {
            sendMessage("The data has already been transferred if you want to do so again by processing it in the settings.");
        }
    }

    @Override
    public void onDisable() {
    }

    public void sendMessage(String message){
        Bukkit.getConsoleSender().sendMessage("§bUltraSkyWars DParser §e- §a" + message);
    }

    public DatabaseParser getParser() {
        return parser;
    }

    public Gson getGson() {
        return gson;
    }
}