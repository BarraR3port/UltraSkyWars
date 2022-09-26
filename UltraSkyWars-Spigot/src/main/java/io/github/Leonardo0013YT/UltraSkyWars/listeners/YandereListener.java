package io.github.Leonardo0013YT.UltraSkyWars.listeners;


import com.podcrash.commissions.yandere.core.spigot.Main;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.Objects;

public class YandereListener implements Listener {
    
    private final UltraSkyWars plugin;
    
    public YandereListener(UltraSkyWars plugin){
        this.plugin = plugin;
    }
    
    
    @EventHandler
    public void onPluginLoad(PluginEnableEvent e){
        if (e.getPlugin().getName().equals("YandereCore")){
            Main yandereCore = (Main) e.getPlugin();
            if (!yandereCore.isHookedIntoSkyWars()){
                try {
                    Main.getInstance().hookSkyWars();
                } catch (Exception var2_2) {
                    plugin.getLogger().info("YandereCore not found, but no economy provider!");
                    Bukkit.shutdown();
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerPlaceBlockEvent(BlockPlaceEvent e){
        if (e.getBlock().getType() == Material.TNT){
            if (check(e.getPlayer())){
                e.getBlockPlaced().setType(Material.AIR);
                TNTPrimed tnt = Objects.requireNonNull(e.getBlock().getLocation().getWorld()).spawn(e.getBlock().getLocation().add(0.5, 0, 0.5), TNTPrimed.class);
                tnt.setFuseTicks(60);
                plugin.getVc().getNMS().setSource(tnt, e.getPlayer());
            }
        }
    }
    
    @EventHandler
    public void onPlayerPlaceBlockEvent(EntityDamageByEntityEvent e){
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        Player target = (Player) e.getEntity();
        Player p = (Player) e.getDamager();
        if (check(target)){
            if (target.getHealth() - e.getFinalDamage() <= 0){
                p.setHealth(p.getMaxHealth());
                p.setFoodLevel(20);
                p.setSaturation(20);
            }
        }
    }
    
    
    private boolean check(Player p){
        if (p == null) return true;
        World w = p.getWorld();
        if (w == null) return false;
        if (plugin.getCm().getLobbyWorld() == null) return true;
        return !plugin.getCm().getLobbyWorld().equals(w.getName());
    }
}
