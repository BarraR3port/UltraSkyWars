package io.github.Leonardo0013YT.UltraSkyWars.listeners;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.enums.DamageCauses;
import io.github.Leonardo0013YT.UltraSkyWars.enums.RewardType;
import io.github.Leonardo0013YT.UltraSkyWars.setup.*;
import io.github.Leonardo0013YT.UltraSkyWars.setup.cosmetics.*;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.UltraInventory;
import io.github.Leonardo0013YT.UltraSkyWars.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraSkyWars.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SetupListener implements Listener {
    
    private final UltraSkyWars plugin;
    
    public SetupListener(UltraSkyWars plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if (plugin.getSm().isSetup(p)){
            e.getDrops().clear();
        }
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (plugin.getSm().isSetup(p)){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player p = (Player) e.getDamager();
            Entity b = e.getEntity();
            if (plugin.getConfig().isSet("cubelets")){
                ConfigurationSection conf = plugin.getConfig().getConfigurationSection("cubelets");
                String now = "";
                for ( String id : conf.getKeys(false) ){
                    String loc = plugin.getConfig().getString("cubelets." + id + ".loc");
                    if (loc.equals(Utils.getLocationString(b.getLocation()))){
                        now = id;
                    }
                }
                if (!now.equals("")){
                    e.setCancelled(true);
                    plugin.getConfig().set("cubelets." + now, null);
                    plugin.saveConfig();
                    p.sendMessage(plugin.getLang().get(p, "messages.cubeletsRemove"));
                    plugin.getSm().removeCubeBlock(p);
                }
            }
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        Player p = e.getPlayer();
        if (plugin.getSm().isSetupCubeBlock(p)){
            Entity b = e.getRightClicked();
            int size;
            if (plugin.getConfig().isSet("cubelets")){
                ConfigurationSection conf = plugin.getConfig().getConfigurationSection("cubelets");
                size = conf.getKeys(false).size();
            } else {
                size = 0;
            }
            size = size + ThreadLocalRandom.current().nextInt(0, 20);
            plugin.getConfig().set("cubelets." + size + ".loc", Utils.getLocationString(b.getLocation()));
            plugin.saveConfig();
            p.sendMessage(plugin.getLang().get(p, "messages.cubelets"));
            plugin.getSm().removeCubeBlock(p);
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (plugin.getSm().isSetupSoulBlock(p)){
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Block b = e.getClickedBlock();
                int size;
                if (plugin.getConfig().isSet("soulwells")){
                    ConfigurationSection conf = plugin.getConfig().getConfigurationSection("soulwells");
                    size = conf.getKeys(false).size();
                } else {
                    size = 0;
                }
                size = size + ThreadLocalRandom.current().nextInt(0, 20);
                plugin.getConfig().set("soulwells." + size + ".loc", Utils.getLocationString(b.getLocation()));
                plugin.saveConfig();
                p.sendMessage(plugin.getLang().get(p, "messages.soulWell"));
                plugin.getSm().removeSoulBlock(p);
                if (plugin.getIjm().isSoulWellInjection()){
                    plugin.getIjm().getSoulwell().getSwm().loadSoulWells();
                }
            } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                Block b = e.getClickedBlock();
                if (plugin.getConfig().isSet("soulwells")){
                    ConfigurationSection conf = plugin.getConfig().getConfigurationSection("soulwells");
                    String now = "";
                    for ( String id : conf.getKeys(false) ){
                        String loc = plugin.getConfig().getString("soulwells." + id + ".loc");
                        if (loc.equals(Utils.getLocationString(b.getLocation()))){
                            now = id;
                        }
                    }
                    if (!now.equals("")){
                        e.setCancelled(true);
                        plugin.getConfig().set("soulwells." + now, null);
                        plugin.saveConfig();
                        p.sendMessage(plugin.getLang().get(p, "messages.soulWellRemove"));
                        plugin.getSm().removeSoulBlock(p);
                        if (plugin.getIjm().isSoulWellInjection()){
                            plugin.getIjm().getSoulwell().getSwm().loadSoulWells();
                        }
                    }
                }
            }
        }
        if (plugin.getSm().isSetupCubeBlock(p)){
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Block b = e.getClickedBlock();
                int size;
                if (plugin.getConfig().isSet("cubelets")){
                    ConfigurationSection conf = plugin.getConfig().getConfigurationSection("cubelets");
                    size = conf.getKeys(false).size();
                } else {
                    size = 0;
                }
                size = size + ThreadLocalRandom.current().nextInt(0, 20);
                plugin.getConfig().set("cubelets." + size + ".loc", Utils.getLocationString(b.getLocation()));
                plugin.saveConfig();
                p.sendMessage(plugin.getLang().get(p, "messages.cubelets"));
                plugin.getSm().removeCubeBlock(p);
            } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                Block b = e.getClickedBlock();
                if (plugin.getConfig().isSet("cubelets")){
                    ConfigurationSection conf = plugin.getConfig().getConfigurationSection("cubelets");
                    String now = "";
                    for ( String id : conf.getKeys(false) ){
                        String loc = plugin.getConfig().getString("cubelets." + id + ".loc");
                        if (loc.equals(Utils.getLocationString(b.getLocation()))){
                            now = id;
                        }
                    }
                    if (!now.equals("")){
                        e.setCancelled(true);
                        plugin.getConfig().set("cubelets." + now, null);
                        plugin.saveConfig();
                        p.sendMessage(plugin.getLang().get(p, "messages.cubeletsRemove"));
                        plugin.getSm().removeCubeBlock(p);
                    }
                }
            }
        }
        if (plugin.getSm().isSetup(p)){
            if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                return;
            }
            ItemStack item = p.getItemInHand();
            if (item.equals(plugin.getIm().getSetup())){
                e.setCancelled(true);
                ArenaSetup as = plugin.getSm().getSetup(p);
                plugin.getSem().createSetupArenaMenu(p, as);
                changeSlot(p);
            }
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                ArenaSetup as = plugin.getSm().getSetup(p);
                Block b = e.getClickedBlock();
                if (item.equals(plugin.getIm().getIsland())){
                    if (as.getActual() == null){
                        return;
                    }
                    IslandArenaSetup ias = as.getActual();
                    if (b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST) || b.getType().equals(Material.ENDER_CHEST)){
                        e.setCancelled(true);
                        if (ias.isChest(b.getLocation())){
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.alreadyAdded"));
                        } else {
                            ias.addChest(b.getLocation());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.addChest"));
                        }
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.noChest"));
                    }
                }
                if (item.equals(plugin.getIm().getCenter())){
                    if (b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST) || b.getType().equals(Material.ENDER_CHEST)){
                        e.setCancelled(true);
                        if (as.isCenter(b.getLocation())){
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.alreadyAdded"));
                        } else {
                            as.addCenter(b.getLocation());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.addChest"));
                        }
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.noChest"));
                    }
                }
            }
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                ArenaSetup as = plugin.getSm().getSetup(p);
                Block b = e.getClickedBlock();
                if (item.equals(plugin.getIm().getIsland())){
                    if (as.getActual() == null){
                        return;
                    }
                    IslandArenaSetup ias = as.getActual();
                    if (b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST) || b.getType().equals(Material.ENDER_CHEST)){
                        e.setCancelled(true);
                        if (!ias.isChest(b.getLocation())){
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetChest"));
                        } else {
                            ias.removeChest(b.getLocation());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.removeChest"));
                        }
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.noChest"));
                    }
                }
                if (item.equals(plugin.getIm().getCenter())){
                    if (b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST) || b.getType().equals(Material.ENDER_CHEST)){
                        e.setCancelled(true);
                        if (!as.isCenter(b.getLocation())){
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetChest"));
                        } else {
                            as.removeCenter(b.getLocation());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.removeChest"));
                        }
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.noChest"));
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if (plugin.getSm().isSetupGlass(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                GlassSetup gs = plugin.getSm().getSetupGlass(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("permission")){
                    gs.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createGlassMenu(p, gs));
                }
                if (type.equals("price")){
                    int price;
                    try {
                        price = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (price < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.glass.minPrice"));
                        return;
                    }
                    gs.setPrice(price);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createGlassMenu(p, gs));
                }
                if (type.equals("slot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.glass.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.glass.maxSlot"));
                        return;
                    }
                    gs.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createGlassMenu(p, gs));
                }
                if (type.equals("page")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.glass.minPage"));
                        return;
                    }
                    gs.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createGlassMenu(p, gs));
                }
            }
        }
        if (plugin.getSm().isSetup(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                ArenaSetup as = plugin.getSm().getSetup(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("min")){
                    int min;
                    try {
                        min = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (min < 2){
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minMin"));
                        return;
                    }
                    as.setMin(min);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupArenaMenu(p, as));
                }
                if (type.equals("teamsize")){
                    int teamsize;
                    try {
                        teamsize = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (teamsize < 1){
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minTeamSize"));
                        return;
                    }
                    as.setTeamSize(teamsize);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupArenaMenu(p, as));
                }
                if (type.equals("borderX")){
                    int borderX;
                    try {
                        borderX = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    as.setBorderX(borderX);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupArenaMenu(p, as));
                }
                if (type.equals("borderZ")){
                    int borderZ;
                    try {
                        borderZ = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    as.setBorderZ(borderZ);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupArenaMenu(p, as));
                }
                if (type.equals("color")){
                    ArrayList<String> colors = new ArrayList<>();
                    for ( ChatColor cc : ChatColor.values() ){
                        colors.add(cc.name());
                    }
                    if (!colors.contains(e.getMessage().toUpperCase())){
                        p.sendMessage(plugin.getLang().get("setup.arena.writeColor"));
                        return;
                    }
                    as.setColor(ChatColor.valueOf(e.getMessage().toUpperCase()));
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupArenaMenu(p, as));
                }
            }
        }
        if (plugin.getSm().isSetupKit(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                KitSetup ks = plugin.getSm().getSetupKit(p);
                String type = plugin.getSm().getSetupName(p);
                if (ks.getKls() != null){
                    if (type.equals("kitlevelslot")){
                        int slot;
                        try {
                            slot = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (slot < 0){
                            p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.minSlot"));
                            return;
                        }
                        if (slot > 53){
                            p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.maxSlot"));
                            return;
                        }
                        KitLevelSetup kls = ks.getKls();
                        kls.setSlot(slot);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKitLevelMenu(p, kls));
                    }
                    if (type.equals("kitlevelprice")){
                        int price;
                        try {
                            price = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (price < 0){
                            p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.minPrice"));
                            return;
                        }
                        KitLevelSetup kls = ks.getKls();
                        kls.setPrice(price);
                        plugin.getSm().removeName(p);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKitLevelMenu(p, kls));
                    }
                }
                if (type.equals("kitslot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.kits.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.kits.maxSlot"));
                        return;
                    }
                    ks.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKitMenu(p, ks));
                }
                if (type.equals("kitpage")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.kits.minPage"));
                        return;
                    }
                    ks.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKitMenu(p, ks));
                }
                if (type.equals("kitpermission")){
                    ks.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKitMenu(p, ks));
                }
            }
        }
        if (plugin.getSm().isSetupTaunt(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                String type = plugin.getSm().getSetupName(p);
                if (ts.getActual() != null){
                    TauntTypeSetup tts = ts.getActual();
                    if (type.equals("tauntstypeadd")){
                        tts.getMsg().add(e.getMessage());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntTypeMenu(p, tts));
                    }
                    return;
                }
                if (type.equals("tauntslot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.taunts.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.taunts.maxSlot"));
                        return;
                    }
                    ts.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("tauntpage")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.taunts.minPage"));
                        return;
                    }
                    ts.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("tauntprice")){
                    int price;
                    try {
                        price = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (price < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.taunts.minPrice"));
                        return;
                    }
                    ts.setPrice(price);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("tauntpermission")){
                    ts.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("taunttitle")){
                    ts.setTitle(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("tauntsubtitle")){
                    ts.setSubtitle(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("tauntplayer")){
                    ts.setPlayer(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
                if (type.equals("tauntnone")){
                    ts.setNone(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTauntMenu(p, ts));
                }
            }
        }
        if (plugin.getSm().isSetupChest(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                ChestSetup gs = plugin.getSm().getSetupChest(p);
                String type = plugin.getSm().getSetupName(p);
                if (gs.getActual() != null){
                    ChestLoteSetup cls = gs.getActual();
                    if (type.equals("chestMode")){
                        if (!plugin.getGm().getModes().contains(e.getMessage())){
                            p.sendMessage(plugin.getLang().get("setup.chests.noModeChest"));
                            return;
                        }
                        if (cls.getModes().contains(e.getMessage())){
                            p.sendMessage(plugin.getLang().get("setup.chests.alreadyMode"));
                            return;
                        }
                        cls.getModes().add(e.getMessage());
                        p.sendMessage(plugin.getLang().get("setup.chests.addedChestMode").replace("<mode>", e.getMessage()));
                        plugin.getSm().removeName(p);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupChestAddMenu(p, gs));
                    }
                    if (type.equals("chestchance")){
                        int chance;
                        try {
                            chance = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (chance < 0){
                            p.sendMessage(plugin.getLang().get(p, "setup.chests.minChance"));
                            return;
                        }
                        cls.setChance(chance);
                        plugin.getSm().removeName(p);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupChestAddMenu(p, gs));
                    }
                }
            }
        }
        if (plugin.getSm().isSetupTrail(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                TrailSetup ts = plugin.getSm().getSetupTrail(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("trailspeed")){
                    int speed;
                    try {
                        speed = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (speed < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minSpeed"));
                        return;
                    }
                    ts.setSpeed(speed);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailoffsetx")){
                    float offsetX;
                    try {
                        offsetX = Float.parseFloat(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (offsetX < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minoffsetX"));
                        return;
                    }
                    ts.setOffsetX(offsetX);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailoffsety")){
                    float offsetY;
                    try {
                        offsetY = Float.parseFloat(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (offsetY < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minoffsetY"));
                        return;
                    }
                    ts.setOffsetY(offsetY);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailoffsetz")){
                    float offsetZ;
                    try {
                        offsetZ = Float.parseFloat(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (offsetZ < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minoffsetZ"));
                        return;
                    }
                    ts.setOffsetZ(offsetZ);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailpermission")){
                    ts.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailamount")){
                    int amount;
                    try {
                        amount = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (amount < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minAmount"));
                        return;
                    }
                    ts.setAmount(amount);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailrange")){
                    double range;
                    try {
                        range = Double.parseDouble(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (range < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minRange"));
                        return;
                    }
                    ts.setRange(range);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailprice")){
                    int price;
                    try {
                        price = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (price < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minPrice"));
                        return;
                    }
                    ts.setPrice(price);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailslot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.maxSlot"));
                        return;
                    }
                    ts.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailpage")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.minPage"));
                        return;
                    }
                    ts.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailparticle")){
                    if (!plugin.getVc().getNMS().isParticle(e.getMessage().toUpperCase())){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.noParticle"));
                        return;
                    }
                    ts.setParticle(e.getMessage().toUpperCase());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
                if (type.equals("trailpermission")){
                    ts.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createTrailMenu(p, ts));
                }
            }
        }
        if (plugin.getSm().isSetupBalloon(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                BalloonSetup bs = plugin.getSm().getSetupBalloon(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("balloonsprice")){
                    int price;
                    try {
                        price = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (price < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.minPrice"));
                        return;
                    }
                    bs.setPrice(price);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupBalloonsMenu(p, bs));
                }
                if (type.equals("balloonsslot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.maxSlot"));
                        return;
                    }
                    bs.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupBalloonsMenu(p, bs));
                }
                if (type.equals("balloonspage")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.minPage"));
                        return;
                    }
                    bs.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupBalloonsMenu(p, bs));
                }
                if (type.equals("balloonspermission")){
                    bs.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSetupBalloonsMenu(p, bs));
                }
            }
        }
        if (plugin.getSm().isSetupKillSound(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                KillSoundSetup bs = plugin.getSm().getSetupKillSound(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("killsoundsprice")){
                    int price;
                    try {
                        price = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (price < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minPrice"));
                        return;
                    }
                    bs.setPrice(price);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
                if (type.equals("killsoundsslot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.maxSlot"));
                        return;
                    }
                    bs.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
                if (type.equals("killsoundspage")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.minPage"));
                        return;
                    }
                    bs.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
                if (type.equals("killsoundspermission")){
                    bs.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
                if (type.equals("killsoundssound")){
                    List<String> sounds = new ArrayList<>();
                    for ( Sound v : Sound.values() ){
                        sounds.add(v.name());
                    }
                    if (!sounds.contains(e.getMessage().toUpperCase())){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.noSound"));
                        return;
                    }
                    bs.setSound(Sound.valueOf(e.getMessage().toUpperCase()));
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
                if (type.equals("killsoundsvol1")){
                    float vol1;
                    try {
                        vol1 = Float.parseFloat(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (vol1 < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minVol1"));
                        return;
                    }
                    bs.setVol1(vol1);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
                if (type.equals("killsoundsvol2")){
                    float vol2;
                    try {
                        vol2 = Float.parseFloat(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (vol2 < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minVol2"));
                        return;
                    }
                    bs.setVol2(vol2);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createKillSoundMenu(p, bs));
                }
            }
        }
        if (plugin.getSm().isSetupParting(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                PartingSetup bs = plugin.getSm().getSetupParting(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("partingprice")){
                    int price;
                    try {
                        price = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (price < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.parting.minPrice"));
                        return;
                    }
                    bs.setPrice(price);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createPartingMenu(p, bs));
                }
                if (type.equals("partingslot")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.parting.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.parting.maxSlot"));
                        return;
                    }
                    bs.setSlot(slot);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createPartingMenu(p, bs));
                }
                if (type.equals("partingpage")){
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (page < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.minPage"));
                        return;
                    }
                    bs.setPage(page);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createPartingMenu(p, bs));
                }
                if (type.equals("partingpermission")){
                    bs.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createPartingMenu(p, bs));
                }
                if (type.equals("partingmessage")){
                    bs.getLines().add(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createPartingMenu(p, bs));
                }
            }
        }
        if (plugin.getSm().isSetupSoulWell(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                SoulWellSetup bs = plugin.getSm().getSetupSoulWell(p);
                SoulWellRewardSetup br = bs.getActual();
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("soulwellrewardrarity")){
                    List<String> rewards = new ArrayList<>();
                    for ( RewardType v : RewardType.values() ){
                        rewards.add(v.name());
                    }
                    if (!rewards.contains(e.getMessage().toUpperCase())){
                        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.noReward"));
                        return;
                    }
                    br.setType(RewardType.valueOf(e.getMessage().toUpperCase()));
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSoulWellRewardMenu(p, br));
                }
                if (type.equals("soulwellrewardrewards")){
                    br.getCmds().add(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSoulWellRewardMenu(p, br));
                }
                if (type.equals("soulwellrewardname")){
                    br.setName(e.getMessage());
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSoulWellRewardMenu(p, br));
                }
                if (type.equals("soulwellrewardchance")){
                    double chance;
                    try {
                        chance = Double.parseDouble(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (chance < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.minChance"));
                        return;
                    }
                    br.setChance(chance);
                    plugin.getSm().removeName(p);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createSoulWellRewardMenu(p, br));
                }
            }
        }
        if (plugin.getSm().isChestType(p)){
            if (plugin.getSm().isSetupName(p)){
                e.setCancelled(true);
                ChestTypeSetup cts = plugin.getSm().getChestType(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("chestsetupname")){
                    cts.setName(e.getMessage());
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.type").replaceAll("<name>", e.getMessage()));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createChestSetupMenu(p, cts));
                }
                if (type.equals("chestsetupkey")){
                    cts.setKey(e.getMessage());
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.type").replaceAll("<name>", e.getMessage()));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createChestSetupMenu(p, cts));
                }
                if (type.equals("chestsetupedit")){
                    cts.setEdit(e.getMessage());
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.type").replaceAll("<name>", e.getMessage()));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createChestSetupMenu(p, cts));
                }
                if (type.equals("chestsetupslotsetup")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.chesttype.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.chesttype.maxSlot"));
                        return;
                    }
                    cts.setSlotSetup(slot);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createChestSetupMenu(p, cts));
                }
                if (type.equals("chestsetupslotvotes")){
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (slot < 0){
                        p.sendMessage(plugin.getLang().get(p, "setup.chesttype.minSlot"));
                        return;
                    }
                    if (slot > 53){
                        p.sendMessage(plugin.getLang().get(p, "setup.chesttype.maxSlot"));
                        return;
                    }
                    cts.setSlotSetup(slot);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getSem().createChestSetupMenu(p, cts));
                }
            }
        }
    }
    
    @EventHandler
    public void onMenu(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (plugin.getSm().isSetupInventory(p)){
            return;
        }
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)){
            return;
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.setupPreview.title"))){
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()){
                return;
            }
            if (!item.getItemMeta().hasDisplayName()){
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            PreviewSetup ps = plugin.getSm().getSetupPreview(p);
            if (display.equals(plugin.getLang().get("menus.setupPreview.player.nameItem"))){
                ps.setPlayer(p.getLocation());
                p.sendMessage(plugin.getLang().get("setup.setPlayer"));
                plugin.getSem().createPreviewMenu(p, ps);
            }
            if (display.equals(plugin.getLang().get("menus.setupPreview.cosmetic.nameItem"))){
                ps.setCosmetic(p.getLocation());
                p.sendMessage(plugin.getLang().get("setup.setCosmetic"));
                plugin.getSem().createPreviewMenu(p, ps);
            }
            if (display.equals(plugin.getLang().get("menus.setupPreview.save.nameItem"))){
                if (ps.getPlayer() == null){
                    p.sendMessage(plugin.getLang().get("setup.noPlayerLocation"));
                    return;
                }
                if (ps.getCosmetic() == null){
                    p.sendMessage(plugin.getLang().get("setup.noCosmeticLocation"));
                    return;
                }
                ps.save();
                p.closeInventory();
                plugin.getSm().removeSetupPreview(p);
                p.sendMessage(plugin.getLang().get("setup.savePreview"));
            }
        }
        if (e.getView().getTitle().equals(plugin.getChestType().get("lang.chests.title"))){
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()){
                return;
            }
            if (!item.getItemMeta().hasDisplayName()){
                return;
            }
            ItemMeta im = item.getItemMeta();
            String type = NBTEditor.getString(item, "SETUPVOTECHEST");
            if (type != null){
                ChestSetup gs = new ChestSetup(plugin, type);
                if (plugin.getCtm().getChests().get(type).getChest() != null){
                    plugin.getCtm().getChests().get(type).getChest().getItems().forEach(t -> gs.addItem(new ItemSetup(t.getItem(), t.isCenter(), t.isRefill(), t.getPercent() / 100, t.getModes())));
                }
                plugin.getSm().setSetupChest(p, gs);
                plugin.getSem().createSetupChestTypeMenu(p, gs);
            }
        }
        if (plugin.getSm().isChestType(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.chestsetup.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                ChestTypeSetup cts = plugin.getSm().getChestType(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get("menus.chestsetup.armorAllTeams.nameItem"))){
                    cts.setArmorAllTeams(!cts.isArmorAllTeams());
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setArmorAllTeam").replace("<state>", Utils.parseBoolean(cts.isArmorAllTeams())));
                    plugin.getSem().createChestSetupMenu(p, cts);
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.refillChange.nameItem"))){
                    cts.setRefillChange(!cts.isRefillChange());
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setRefillChange").replace("<state>", Utils.parseBoolean(cts.isRefillChange())));
                    plugin.getSem().createChestSetupMenu(p, cts);
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.name.nameItem"))){
                    plugin.getSm().setSetupName(p, "chestsetupname");
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setType"));
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.key.nameItem"))){
                    plugin.getSm().setSetupName(p, "chestsetupkey");
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setType"));
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.edit.nameItem"))){
                    plugin.getSm().setSetupName(p, "chestsetupedit");
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setType"));
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.slotSetup.nameItem"))){
                    plugin.getSm().setSetupName(p, "chestsetupslotsetup");
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setSlot"));
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.slotVotes.nameItem"))){
                    plugin.getSm().setSetupName(p, "chestsetupslotvotes");
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.setSlot"));
                }
                if (display.equals(plugin.getLang().get("menus.chestsetup.save.nameItem"))){
                    p.closeInventory();
                    cts.save();
                    p.sendMessage(plugin.getLang().get(p, "setup.chesttype.saved"));
                }
            }
        }
        if (plugin.getSm().isSetupSoulWell(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.soulwell.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                SoulWellSetup bs = plugin.getSm().getSetupSoulWell(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.soulwell.rewards.nameItem"))){
                    if (e.getClick().equals(ClickType.RIGHT)){
                        if (bs.getRewards().size() == 0){
                            p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.noLast"));
                            return;
                        }
                        bs.getRewards().remove(bs.getRewards().size() - 1);
                        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.removeReward"));
                    } else {
                        if (bs.getActual() == null){
                            bs.setActual(new SoulWellRewardSetup("Default", RewardType.COMMON, 25));
                        }
                        SoulWellRewardSetup swrs = bs.getActual();
                        plugin.getSem().createSoulWellRewardMenu(p, swrs);
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.soulwell.save.nameItem"))){
                    bs.saveSoulWell(p);
                    p.closeInventory();
                    plugin.getSm().removeSoulWell(p);
                }
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.soulwellreward.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                SoulWellSetup bs = plugin.getSm().getSetupSoulWell(p);
                SoulWellRewardSetup brs = bs.getActual();
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.soulwellreward.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    brs.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.soulwellreward.rarity.nameItem"))){
                    plugin.getSm().setSetupName(p, "soulwellrewardrarity");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.setRarity"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.soulwellreward.rewards.nameItem"))){
                    if (e.getClick().equals(ClickType.RIGHT)){
                        if (brs.getCmds().size() == 0){
                            p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.noLast"));
                            return;
                        }
                        brs.getCmds().remove(brs.getCmds().size() - 1);
                        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.removeCMD"));
                    } else {
                        plugin.getSm().setSetupName(p, "soulwellrewardrewards");
                        p.closeInventory();
                        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.setRewards"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.soulwellreward.chance.nameItem"))){
                    plugin.getSm().setSetupName(p, "soulwellrewardchance");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.setChance"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.soulwellreward.name.nameItem"))){
                    plugin.getSm().setSetupName(p, "soulwellrewardname");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.setName"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.soulwellreward.save.nameItem"))){
                    bs.saveSoulWellReward(p);
                    plugin.getSem().createSoulWellMenu(p);
                }
            }
        }
        if (plugin.getSm().isSetupParting(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.parting.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                PartingSetup bs = plugin.getSm().getSetupParting(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.parting.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.parting.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    bs.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.parting.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "partingprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.parting.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "partingslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.parting.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "partingpage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.parting.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "partingpermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.parting.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.message.nameItem"))){
                    if (e.getClick().equals(ClickType.LEFT)){
                        plugin.getSm().setSetupName(p, "partingmessage");
                        p.closeInventory();
                        p.sendMessage(plugin.getLang().get(p, "setup.parting.addMessage"));
                    } else {
                        bs.getLines().remove(bs.getLines().size() - 1);
                        plugin.getSem().createPartingMenu(p, bs);
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.isBuy.nameItem"))){
                    bs.setBuy(!bs.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.parting.setBuy").replace("<state>", Utils.parseBoolean(bs.isBuy())));
                    plugin.getSem().createPartingMenu(p, bs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.parting.save.nameItem"))){
                    bs.saveParting(p);
                    p.closeInventory();
                    plugin.getSm().removeParting(p);
                }
            }
        }
        if (plugin.getSm().isSetupKillSound(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.killsounds.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                KillSoundSetup bs = plugin.getSm().getSetupKillSound(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    bs.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundsprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundsslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundspage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundspermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.sound.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundssound");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setSound"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.vol1.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundsvol1");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setVol1"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.vol2.nameItem"))){
                    plugin.getSm().setSetupName(p, "killsoundsvol2");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setVol2"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.isBuy.nameItem"))){
                    bs.setBuy(!bs.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setBuy").replace("<state>", Utils.parseBoolean(bs.isBuy())));
                    plugin.getSem().createKillSoundMenu(p, bs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.save.nameItem"))){
                    bs.saveKillSound(p);
                    p.closeInventory();
                    plugin.getSm().removeKillSound(p);
                }
            }
        }
        if (plugin.getSm().isSetupBalloon(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.balloons.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                BalloonSetup bs = plugin.getSm().getSetupBalloon(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.balloons.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.balloons.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    bs.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.balloons.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.balloons.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "balloonsprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.balloons.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.balloons.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "balloonsslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.balloons.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.balloons.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "balloonspage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.balloons.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.balloons.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "balloonspermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.balloons.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.balloons.isBuy.nameItem"))){
                    bs.setBuy(!bs.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.balloons.setBuy").replace("<state>", Utils.parseBoolean(bs.isBuy())));
                    plugin.getSem().createSetupBalloonsMenu(p, bs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.balloons.save.nameItem"))){
                    bs.saveBalloon(p);
                    p.closeInventory();
                    plugin.getSm().removeBalloon(p);
                }
            }
        }
        if (plugin.getSm().isSetupTrail(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.trails.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                TrailSetup ts = plugin.getSm().getSetupTrail(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.trails.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.trails.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    ts.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.speed.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailspeed");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setSpeed"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.offsetX.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailoffsetx");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setoffsetX"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.offsetY.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailoffsety");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setoffsetY"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.offsetZ.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailoffsetz");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setoffsetZ"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.amount.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailamount");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setAmount"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.range.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailrange");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setRange"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailpage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.particle.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailparticle");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setParticle"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "trailpermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.isBuy.nameItem"))){
                    ts.setBuy(!ts.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setBuy").replace("<state>", Utils.parseBoolean(ts.isBuy())));
                    plugin.getSem().createTrailMenu(p, ts);
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.save.nameItem"))){
                    ts.saveTrail(p);
                    p.closeInventory();
                    plugin.getSm().removeTrail(p);
                }
            }
        }
        if (plugin.getSm().isSetupChest(p)){
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                return;
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.chestsremove.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ChestSetup gs = plugin.getSm().getSetupChest(p);
                ItemStack item = e.getCurrentItem();
                if (item.hasItemMeta()){
                    if (item.getItemMeta().hasDisplayName()){
                        ItemMeta im = item.getItemMeta();
                        String display = im.getDisplayName();
                        if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))){
                            plugin.getUim().addPage(p);
                            plugin.getSem().createSetupChestRemoveMenu(p, gs);
                            return;
                        }
                        if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))){
                            plugin.getUim().removePage(p);
                            plugin.getSem().createSetupChestRemoveMenu(p, gs);
                            return;
                        }
                        if (display.equals(plugin.getLang().get(p, "menus.chestsremove.save.nameItem"))){
                            plugin.getSem().createSetupChestTypeMenu(p, gs);
                            return;
                        }
                    }
                }
                gs.removeItem(item);
                plugin.getSem().createSetupChestRemoveMenu(p, gs);
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.chestsadd.title"))){
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ChestSetup gs = plugin.getSm().getSetupChest(p);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                String display = item.getItemMeta().getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))){
                    plugin.getUim().addPage(p);
                    plugin.getSem().createSetupChestAddMenu(p, gs);
                    return;
                }
                if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))){
                    plugin.getUim().removePage(p);
                    plugin.getSem().createSetupChestAddMenu(p, gs);
                    return;
                }
                e.setCancelled(true);
                ChestLoteSetup cls = gs.getActual();
                if (display.equals(plugin.getLang().get(p, "menus.chestsadd.chance.nameItem"))){
                    ArrayList<ItemStack> items = new ArrayList<>();
                    Inventory inv = e.getInventory();
                    for ( int i = 0; i < 45; i++ ){
                        if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)){
                            continue;
                        }
                        items.add(inv.getItem(i));
                    }
                    gs.setTemporarily(items);
                    plugin.getSm().setSetupName(p, "chestchance");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.chests.setChance"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.chestsadd.center.nameItem"))){
                    ArrayList<ItemStack> items = new ArrayList<>();
                    Inventory inv = e.getInventory();
                    for ( int i = 0; i < 45; i++ ){
                        if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)){
                            continue;
                        }
                        items.add(inv.getItem(i));
                    }
                    gs.setTemporarily(items);
                    cls.setCenter(!cls.isCenter());
                    p.sendMessage(plugin.getLang().get(p, "setup.chests.setCenter").replace("<state>", Utils.parseBoolean(cls.isCenter())));
                    plugin.getSem().createSetupChestAddMenu(p, gs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.chestsadd.refill.nameItem"))){
                    ArrayList<ItemStack> items = new ArrayList<>();
                    Inventory inv = e.getInventory();
                    for ( int i = 0; i < 45; i++ ){
                        if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)){
                            continue;
                        }
                        items.add(inv.getItem(i));
                    }
                    gs.setTemporarily(items);
                    cls.setRefill(!cls.isRefill());
                    p.sendMessage(plugin.getLang().get(p, "setup.chests.setRefill").replace("<state>", Utils.parseBoolean(cls.isRefill())));
                    plugin.getSem().createSetupChestAddMenu(p, gs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.chestsadd.modes.nameItem"))){
                    if (e.getClick().isRightClick()){
                        if (cls.getModes().isEmpty()){
                            p.sendMessage(plugin.getLang().get("setup.chests.noLastChest"));
                            return;
                        }
                        cls.getModes().remove(cls.getModes().size() - 1);
                        p.sendMessage(plugin.getLang().get("setup.chests.removedChestMode"));
                        plugin.getSem().createSetupChestAddMenu(p, gs);
                    } else {
                        ArrayList<ItemStack> items = new ArrayList<>();
                        Inventory inv = e.getInventory();
                        for ( int i = 0; i < 45; i++ ){
                            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)){
                                continue;
                            }
                            items.add(inv.getItem(i));
                        }
                        gs.setTemporarily(items);
                        plugin.getSm().setSetupName(p, "chestMode");
                        p.closeInventory();
                        p.sendMessage(plugin.getLang().get("setup.chests.writeChestMode"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.chestsadd.save.nameItem"))){
                    gs.saveChestLote(e.getInventory());
                    plugin.getSem().createSetupChestTypeMenu(p, gs);
                }
            }
            if (e.getView().getTitle().equals(plugin.getChestType().get("lang.chests.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ChestSetup gs = plugin.getSm().getSetupChest(p);
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                String display = item.getItemMeta().getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))){
                    plugin.getUim().addPage(p);
                    plugin.getSem().createSetupChestTypeMenu(p, gs);
                    return;
                }
                if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))){
                    plugin.getUim().removePage(p);
                    plugin.getSem().createSetupChestTypeMenu(p, gs);
                    return;
                }
                if (display.equals(plugin.getLang().get(p, "menus.cheststype.filter.nameItem"))){
                    switch(gs.getFilter()){
                        case "NONE":
                            gs.setFilter("REFILL");
                            break;
                        case "REFILL":
                            gs.setFilter("CENTER");
                            break;
                        case "CENTER":
                            gs.setFilter("CHESTPLATE");
                            break;
                        case "CHESTPLATE":
                            gs.setFilter("LEGGINGS");
                            break;
                        case "LEGGINGS":
                            gs.setFilter("BOOTS");
                            break;
                        case "BOOTS":
                            gs.setFilter("HELMET");
                            break;
                        case "HELMET":
                            gs.setFilter("SWORD");
                            break;
                        case "SWORD":
                            gs.setFilter("NONE");
                            break;
                    }
                    plugin.getSem().createSetupChestTypeMenu(p, gs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.cheststype.add.nameItem"))){
                    if (gs.getActual() == null){
                        gs.setActual(new ChestLoteSetup(25, false, false, new ArrayList<>(Collections.singletonList("ALL"))));
                    }
                    plugin.getSem().createSetupChestAddMenu(p, gs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.cheststype.remove.nameItem"))){
                    plugin.getSem().createSetupChestRemoveMenu(p, gs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.cheststype.save.nameItem"))){
                    gs.saveChest(p);
                    p.closeInventory();
                    plugin.getSm().removeChest(p);
                }
            }
        }
        if (plugin.getSm().isSetupTaunt(p)){
            
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                return;
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.tauntstype.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.tauntstype.add.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntstypeadd");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.tauntstype.setMessage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.tauntstype.save.nameItem"))){
                    ts.saveTauntType(p);
                    plugin.getSem().createTauntMenu(p, ts);
                }
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.taunts.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.taunts.slot.nameItem"))){
                    if (p.getItemOnCursor() == null){
                        p.sendMessage(plugin.getLang().get(p, "setup.taunts.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    ts.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.setplayer.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntplayer");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPlayer"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.setnone.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntnone");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setNone"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.settitle.nameItem"))){
                    plugin.getSm().setSetupName(p, "taunttitle");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setTitle"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.setsubtitle.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntsubtitle");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setSubTitle"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntpage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "tauntpermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.isBuy.nameItem"))){
                    ts.setBuy(!ts.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setBuy").replace("<state>", Utils.parseBoolean(ts.isBuy())));
                    plugin.getSem().createTauntMenu(p, ts);
                }
                List<String> s = Arrays.asList(DamageCauses.valueOf(plugin.getVc().getVersion()).getCauses());
                String d = display.replace("§e", "");
                if (s.contains(d)){
                    if (ts.getActual() != null){
                        plugin.getSem().createTauntTypeMenu(p, ts.getActual());
                    } else {
                        TauntTypeSetup tts = ts.getTaunts().get(d);
                        plugin.getSem().createTauntTypeMenu(p, tts);
                        ts.setActual(tts);
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.save.nameItem"))){
                    ts.saveTaunt(p);
                    plugin.getSm().removeTaunt(p);
                    p.closeInventory();
                }
            }
        }
        if (plugin.getSm().isSetupKit(p)){
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                return;
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.kitlevel.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                KitSetup ks = plugin.getSm().getSetupKit(p);
                KitLevelSetup kls = ks.getKls();
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.kitlevel.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    kls.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.kitlevel.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "kitlevelslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.kitlevel.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "kitlevelprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.kitlevel.items.nameItem"))){
                    if (e.getClick().equals(ClickType.RIGHT)){
                        plugin.getGem().createKitsMenu(p, kls.getInv(), kls.getArmors());
                    } else {
                        kls.setArmors(p.getInventory().getArmorContents());
                        kls.setInv(p.getInventory().getContents());
                        p.sendMessage(plugin.getLang().get(p, "setup.kitlevel.setItems"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.kitlevel.isBuy.nameItem"))){
                    kls.setBuy(!kls.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.setBuy").replace("<state>", Utils.parseBoolean(kls.isBuy())));
                    plugin.getSem().createKitLevelMenu(p, kls);
                }
                if (display.equals(plugin.getLang().get(p, "menus.kitlevel.save.nameItem"))){
                    ks.saveKitLevel(p);
                    plugin.getSem().createKitMenu(p, ks);
                }
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.kits.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                KitSetup ks = plugin.getSm().getSetupKit(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.kits.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "kitslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.kits.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "kitpage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.kits.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "kitpermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.kits.levels.nameItem"))){
                    KitLevelSetup kls;
                    if (ks.getKls() != null){
                        kls = ks.getKls();
                    } else {
                        kls = new KitLevelSetup(p);
                        ks.setKls(kls);
                    }
                    plugin.getSem().createKitLevelMenu(p, kls);
                }
                if (display.equals(plugin.getLang().get(p, "menus.kits.save.nameItem"))){
                    if (ks.getLevels().size() < 1){
                        p.sendMessage(plugin.getLang().get(p, "setup.kits.notSet.noLevels"));
                        return;
                    }
                    ks.saveKit(p);
                    plugin.getSm().removeKit(p);
                    p.closeInventory();
                }
            }
        }
        if (plugin.getSm().isSetupEvent(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.event.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                ArenaSetup as = plugin.getSm().getSetup(p);
                EventSetup es = as.getEactual();
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.event.add1.nameItem"))){
                    es.addSeconds(1);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.addSeconds").replaceAll("<value>", "1"));
                    plugin.getSem().createEventMenu(p, es);
                }
                if (display.equals(plugin.getLang().get(p, "menus.event.add5.nameItem"))){
                    es.addSeconds(5);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.addSeconds").replaceAll("<value>", "5"));
                    plugin.getSem().createEventMenu(p, es);
                }
                if (display.equals(plugin.getLang().get(p, "menus.event.remove5.nameItem"))){
                    es.removeSeconds(5);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.removeSeconds").replaceAll("<value>", "5"));
                    plugin.getSem().createEventMenu(p, es);
                }
                if (display.equals(plugin.getLang().get(p, "menus.event.remove1.nameItem"))){
                    es.removeSeconds(1);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.removeSeconds").replaceAll("<value>", "1"));
                    plugin.getSem().createEventMenu(p, es);
                }
                if (display.equals(plugin.getLang().get(p, "menus.event.save.nameItem"))){
                    as.saveEvent(p);
                    plugin.getSem().createEventsMenu(p, as);
                    plugin.getSm().removeEvent(p);
                }
            }
        }
        if (plugin.getSm().isSetupGlass(p)){
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                return;
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.glass.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                GlassSetup gs = plugin.getSm().getSetupGlass(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.glass.permission.nameItem"))){
                    plugin.getSm().setSetupName(p, "permission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.glass.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.glass.price.nameItem"))){
                    plugin.getSm().setSetupName(p, "price");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.glass.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.glass.slot.nameItem"))){
                    plugin.getSm().setSetupName(p, "slot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.glass.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.glass.page.nameItem"))){
                    plugin.getSm().setSetupName(p, "page");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.glass.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.glass.isBuy.nameItem"))){
                    gs.setBuy(!gs.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.glass.setBuy").replace("<state>", Utils.parseBoolean(gs.isBuy())));
                    plugin.getSem().createGlassMenu(p, gs);
                }
                if (display.equals(plugin.getLang().get(p, "menus.glass.icon.nameItem"))){
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
                        p.sendMessage(plugin.getLang().get(p, "setup.glass.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()){
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    gs.setItem(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.glass.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.glass.save.nameItem"))){
                    gs.save(plugin, p);
                    plugin.getSm().removeGlass(p);
                    p.closeInventory();
                }
            }
        }
        if (plugin.getSm().isSetup(p)){
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.events.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                ArenaSetup as = plugin.getSm().getSetup(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.events.refill.nameItem"))){
                    as.setEactual(new EventSetup("refill"));
                    plugin.getSm().setSetupEvent(p, as.getEactual());
                    EventSetup es = as.getEactual();
                    plugin.getSem().createEventMenu(p, es);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.addRefill"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.events.final.nameItem"))){
                    as.setEactual(new EventSetup("final"));
                    EventSetup es = as.getEactual();
                    plugin.getSm().setSetupEvent(p, as.getEactual());
                    plugin.getSem().createEventMenu(p, es);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.addFinal"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.events.remove.nameItem"))){
                    if (as.getEvents().isEmpty()){
                        p.sendMessage(plugin.getLang().get(p, "setup.event.noEvents"));
                        return;
                    }
                    as.getEvents().remove(as.getEvents().size() - 1);
                    p.sendMessage(plugin.getLang().get(p, "setup.event.removeLast"));
                    plugin.getSem().createEventsMenu(p, as);
                }
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.island.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                ArenaSetup as = plugin.getSm().getSetup(p);
                IslandArenaSetup ias = as.getActual();
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.island.spawn.nameItem"))){
                    if (e.isRightClick()){
                        if (ias.getSpawn() != null){
                            p.teleport(ias.getSpawn());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.teleportedTo").replace("<to>", "Spawn"));
                        } else {
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetLocation"));
                        }
                    } else {
                        ias.setSpawn(p.getLocation());
                        plugin.getSem().createIslandMenu(p, ias);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.setIslandSpawn"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.island.balloon.nameItem"))){
                    if (e.isRightClick()){
                        if (ias.getBalloon() != null){
                            p.teleport(ias.getBalloon());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.teleportedTo").replace("<to>", "Balloon"));
                        } else {
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetLocation"));
                        }
                    } else {
                        ias.setBalloon(p.getLocation());
                        plugin.getSem().createIslandMenu(p, ias);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.setIslandBalloon"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.island.fence.nameItem"))){
                    if (e.isRightClick()){
                        if (ias.getFence() != null){
                            p.teleport(ias.getFence());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.teleportedTo").replace("<to>", "Fence"));
                        } else {
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetLocation"));
                        }
                    } else {
                        ias.setFence(p.getLocation());
                        plugin.getSem().createIslandMenu(p, ias);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.setIslandFence"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.island.chests.nameItem"))){
                    p.closeInventory();
                    if (e.isRightClick()){
                        List<Block> chests = getNearbyBlocks(p.getLocation(), 3);
                        int i = 0;
                        for ( Block c : chests ){
                            if (!ias.isChest(c.getLocation())){
                                ias.addChest(c.getLocation());
                                i++;
                            }
                        }
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.detectedChest").replace("<amount>", String.valueOf(i)));
                    } else {
                        p.getInventory().remove(plugin.getIm().getIsland());
                        p.getInventory().addItem(plugin.getIm().getIsland());
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.setChest"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.island.save.nameItem"))){
                    as.saveIsland(p);
                    p.closeInventory();
                }
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.islands.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                ArenaSetup as = plugin.getSm().getSetup(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (NBTEditor.contains(item, "ULTRASKYWARS_ISLAND_ID")){
                    int id = NBTEditor.getInt(item, "ULTRASKYWARS_ISLAND_ID");
                    if (e.isRightClick()){
                        as.getIslands().remove(id);
                        p.sendMessage(plugin.getLang().get("setup.arena.removeIsland").replace("<#>", String.valueOf(id)));
                        plugin.getSem().createIslandsMenu(p, as);
                    } else {
                        IslandArenaSetup ias = as.getIslands().get(id);
                        as.setActual(ias);
                        plugin.getSem().createIslandMenu(p, ias);
                        p.sendMessage(plugin.getLang().get("setup.arena.editIsland").replace("<#>", String.valueOf(id)));
                    }
                    return;
                }
                if (display.equals(plugin.getLang().get("menus.islands.addIsland.nameItem"))){
                    as.setActual(new IslandArenaSetup(p, as.getLast()));
                    as.setLast(as.getLast() + 1);
                    IslandArenaSetup nias = as.getActual();
                    plugin.getSem().createIslandMenu(p, nias);
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.createdIsland"));
                }
            }
            if (e.getView().getTitle().equals(plugin.getLang().get("menus.setup.title"))){
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
                    return;
                }
                ItemStack item = e.getCurrentItem();
                if (!item.hasItemMeta()){
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()){
                    return;
                }
                ArenaSetup as = plugin.getSm().getSetup(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.setup.min.nameItem"))){
                    plugin.getSm().setSetupName(p, "min");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setMin"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.ranked.nameItem"))){
                    as.setRanked(!as.isRanked());
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setRanked").replace("<state>", Utils.parseBoolean(as.isRanked())));
                    plugin.getSem().createSetupArenaMenu(p, as);
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.votes.nameItem"))){
                    as.setVotes(!as.isVotes());
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setVotes").replace("<state>", Utils.parseBoolean(as.isVotes())));
                    plugin.getSem().createSetupArenaMenu(p, as);
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.teamSize.nameItem"))){
                    plugin.getSm().setSetupName(p, "teamsize");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setTeamSize"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.borderX.nameItem"))){
                    plugin.getSm().setSetupName(p, "borderX");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setBorderX"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.borderZ.nameItem"))){
                    plugin.getSm().setSetupName(p, "borderZ");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setBorderZ"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.color.nameItem"))){
                    plugin.getSm().setSetupName(p, "color");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.arena.setColor"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.center.nameItem"))){
                    p.closeInventory();
                    if (e.isRightClick()){
                        List<Block> chests = getNearbyBlocks(p.getLocation(), 3);
                        int i = 0;
                        for ( Block c : chests ){
                            if (!as.isCenter(c.getLocation())){
                                as.addCenter(c.getLocation());
                                i++;
                            }
                        }
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.detectedChest").replace("<amount>", String.valueOf(i)));
                    } else {
                        p.getInventory().remove(plugin.getIm().getCenter());
                        p.getInventory().addItem(plugin.getIm().getCenter());
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.giveWand"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.islands.nameItem"))){
                    IslandArenaSetup ias = as.getActual();
                    if (ias == null){
                        plugin.getSem().createIslandsMenu(p, as);
                    } else {
                        plugin.getSem().createIslandMenu(p, ias);
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.save.nameItem"))){
                    as.save(p, as.getName());
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.lobby.nameItem"))){
                    if (e.isRightClick()){
                        if (as.getLobby() != null){
                            p.teleport(as.getLobby());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.teleportedTo").replace("<to>", "Lobby"));
                        } else {
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetLocation"));
                        }
                    } else {
                        as.setLobby(p.getLocation());
                        plugin.getSem().createSetupArenaMenu(p, as);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.setLobby"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.spect.nameItem"))){
                    if (e.isRightClick()){
                        if (as.getSpectator() != null){
                            p.teleport(as.getSpectator());
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.teleportedTo").replace("<to>", "Lobby"));
                        } else {
                            p.sendMessage(plugin.getLang().get(p, "setup.arena.noSetLocation"));
                        }
                    } else {
                        as.setSpectator(p.getLocation());
                        plugin.getSem().createSetupArenaMenu(p, as);
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.setSpect"));
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.setup.events.nameItem"))){
                    if (as.getEactual() != null){
                        EventSetup es = as.getEactual();
                        plugin.getSem().createEventMenu(p, es);
                    } else {
                        plugin.getSem().createEventsMenu(p, as);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        if (plugin.getSm().isSetupInventory(p)){
            UltraInventory i = plugin.getSm().getSetupInventory(p);
            plugin.getUim().setInventory(i.getName(), e.getInventory());
            plugin.getSm().removeInventory(p);
            p.sendMessage(plugin.getLang().get(p, "setup.menus.finishEdit"));
        }
    }
    
    private void changeSlot(Player p){
        if (!plugin.getVc().is1_13to17()) return;
        int slot = p.getInventory().getHeldItemSlot();
        if (slot == 0){
            p.getInventory().setHeldItemSlot(slot + 1);
        }
        if (slot > 0){
            p.getInventory().setHeldItemSlot(slot - 1);
        }
    }
    
    private List<Block> getNearbyBlocks(Location location, int radius){
        List<Block> blocks = new ArrayList<>();
        for ( int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++ ){
            for ( int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++ ){
                for ( int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++ ){
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType() == Material.ENDER_CHEST){
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }
    
    
}