package io.github.Leonardo0013YT.UltraSkyWars.listeners;


import com.podcrash.commissions.yandere.core.spigot.Main;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class YandereListener implements Listener {
    
    
    @EventHandler
    public void onPluginLoad(PluginEnableEvent e) {
        if(e.getPlugin().getName().equals("YandereCore")){
            Main yandereCore = (Main) e.getPlugin();
            if(!yandereCore.isHookedIntoSkyWars()){
                try {
                    Main.getInstance().hookSkyWars();
                } catch(Exception var2_2) {
                    UltraSkyWars.get().getLogger().info("YandereCore not found, but no economy provider!");
                    Bukkit.shutdown();
                }
            }
        }
    }
}
