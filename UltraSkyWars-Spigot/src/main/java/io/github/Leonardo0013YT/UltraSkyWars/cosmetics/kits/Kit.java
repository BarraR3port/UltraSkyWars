package io.github.Leonardo0013YT.UltraSkyWars.cosmetics.kits;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.Map.Entry;

public class Kit {
    
    private final HashMap<Integer, KitLevel> levels = new HashMap<>();
    private final List<String> modes;
    private final int id;
    private final int slot;
    private final int page;
    private final String name;
    private final String permission;
    private ItemStack icon;
    
    public Kit(UltraSkyWars plugin, String path){
        this.id = plugin.getKits().getInt(path + ".id");
        this.slot = plugin.getKits().getInt(path + ".slot");
        this.page = plugin.getKits().getInt(path + ".page");
        this.name = plugin.getKits().get(path + ".name");
        this.permission = plugin.getKits().get(path + ".permission");
        this.modes = plugin.getKits().getListOrDefault(path + ".modes", Arrays.asList("SOLO", "TEAM", "RANKED"));
        if (plugin.getKits().getConfig().getConfigurationSection(path + ".levels") != null){
            for ( String level : plugin.getKits().getConfig().getConfigurationSection(path + ".levels").getKeys(false) ){
                int nivel = Integer.parseInt(level);
                if (this.icon == null){
                    this.icon = Utils.getIcon(plugin.getKits(), path + ".levels." + level);
                }
                levels.put(nivel, new KitLevel(plugin, path + ".levels." + level, this));
            }
        }
        plugin.getKm().setLastPage(page);
    }
    
    public int getId(){
        return id;
    }
    
    public KitLevel getKitLevelByItem(Player p, ItemStack item){
        for ( KitLevel l : getLevels().values() ){
            if (l.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())){
                return l;
            }
        }
        return null;
    }
    
    public HashMap<Integer, KitLevel> getLevels(){
        return levels;
    }
    
    public Entry<Integer, KitLevel> getFirstLevel(){
        NavigableMap<Integer, KitLevel> levels = new TreeMap<>(this.levels);
        return levels.firstEntry();
    }
    
    public List<String> getModes(){
        return modes;
    }
    
    public int getSlot(){
        return slot;
    }
    
    public int getPage(){
        return page;
    }
    
    public String getName(){
        return name;
    }
    
    public String getPermission(){
        return permission;
    }
    
    public ItemStack getIcon(){
        return icon;
    }
}