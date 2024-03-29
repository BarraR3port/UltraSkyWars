package io.github.Leonardo0013YT.UltraSkyWars.menus;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer;
import io.github.Leonardo0013YT.UltraSkyWars.game.GameData;
import io.github.Leonardo0013YT.UltraSkyWars.inventories.selectors.GameSelectorMenu;
import io.github.Leonardo0013YT.UltraSkyWars.objects.Level;
import io.github.Leonardo0013YT.UltraSkyWars.objects.PrestigeIcon;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Perk;
import io.github.Leonardo0013YT.UltraSkyWars.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraSkyWars.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraSkyWars.utils.Utils;
import io.github.Leonardo0013YT.UltraSkyWars.xseries.XMaterial;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GameMenu {

    private ConcurrentHashMap<UUID, String> views = new ConcurrentHashMap<>();
    private ArrayList<Integer> slots = new ArrayList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34));
    private int[] slotsLevel = {11, 12, 13, 14, 15}, kitsPreview = {4, 5, 6, 7, 8, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private UltraSkyWars plugin;

    public GameMenu(UltraSkyWars plugin) {
        this.plugin = plugin;
    }

    public void createPrestigeIcons(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.prestige.title"));
        SWPlayer sw = plugin.getDb().getSWPlayer(p);
        for (PrestigeIcon pi : plugin.getLvl().getPrestige().values()) {
            inv.setItem(pi.getSlot(), pi.getItemStack(p, sw));
        }
        ItemStack back = ItemBuilder.item(plugin.getCm().getBack(), plugin.getLang().get(p, "menus.back.nameItem"), plugin.getLang().get(p, "menus.back.loreItem"));
        ItemStack close = ItemBuilder.item(plugin.getCm().getCloseitem(), plugin.getLang().get(p, "menus.close.nameItem"), plugin.getLang().get(p, "menus.close.loreItem"));
        inv.setItem(48, back);
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createKitsMenu(Player p, ItemStack[] contents, ItemStack[] armor) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.preview.title"));
        ItemStack barrier = ItemBuilder.item(XMaterial.BARRIER, 1, "§7", "§7");
        ItemStack white = ItemBuilder.item(XMaterial.WHITE_STAINED_GLASS_PANE, 1, "§7", "§7");
        for (int i : kitsPreview) {
            inv.setItem(i, white);
        }
        ArrayUtils.reverse(armor);
        for (int i = 0; i < 4; i++) {
            if (armor.length <= i) continue;
            ItemStack item = armor[i];
            if (item == null || item.getType().equals(Material.AIR)) inv.setItem(i, barrier);
            else inv.setItem(i, item);
        }
        for (int i = 0; i < 36; i++) {
            if (contents.length <= i) continue;
            ItemStack item = contents[i];
            if (i < 9) {
                inv.setItem(45 + i, item);
            } else {
                inv.setItem(i, item);
            }
        }
        p.openInventory(inv);
    }

    public void createLevelMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.levels.title"));
        SWPlayer sw = plugin.getDb().getSWPlayer(p);
        Level level = plugin.getLvl().getLevel(p);
        Level nLevel = plugin.getLvl().getLevelByLevel(sw.getLevel() + 1);
        if (nLevel == null) {
            nLevel = level;
        }
        boolean max = nLevel.equals(level);
        ItemStack nowLevel = ItemBuilder.item(XMaterial.IRON_INGOT, 1, plugin.getLang().get(p, "menus.levels.nowLevel.nameItem").replaceAll("<level>", "" + level.getLevel()), plugin.getLang().get(p, "menus.levels.nowLevel.loreItem"));
        ItemStack glassYellow = ItemBuilder.item(XMaterial.YELLOW_STAINED_GLASS_PANE, 1, plugin.getLang().get(p, "menus.levels.glass.nameItem").replaceAll("<level>", (max) ? "§cMax" : "" + nLevel.getLevel()), plugin.getLang().get(p, "menus.levels.glass.loreItem"));
        ItemStack glassGreen = ItemBuilder.item(XMaterial.LIME_STAINED_GLASS_PANE, 1, plugin.getLang().get(p, "menus.levels.glass.nameItem").replaceAll("<level>", (max) ? "§cMax" : "" + nLevel.getLevel()), plugin.getLang().get(p, "menus.levels.glass.loreItem"));
        ItemStack nextLevel = ItemBuilder.item(XMaterial.IRON_INGOT, 1, plugin.getLang().get(p, "menus.levels.nextLevel.nameItem").replaceAll("<level>", (max) ? "§cMax" : "" + nLevel.getLevel()), plugin.getLang().get(p, "menus.levels.nextLevel.loreItem"));
        ItemStack prestige = ItemBuilder.item(XMaterial.NETHER_STAR, 1, plugin.getLang().get(p, "menus.levels.prestige.nameItem"), plugin.getLang().get(p, "menus.levels.prestige.loreItem").replaceAll("<player>", p.getName()));
        ItemStack hide = ItemBuilder.item(XMaterial.LIME_DYE, 1, plugin.getLang().get(p, "menus.levels.hide.nameItem"), plugin.getLang().get(p, "menus.levels.hide.loreItem"));
        ItemStack show = ItemBuilder.item(XMaterial.GRAY_DYE, 1, plugin.getLang().get(p, "menus.levels.show.nameItem"), plugin.getLang().get(p, "menus.levels.show.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.BARRIER, 1, plugin.getLang().get(p, "menus.close.nameItem"), plugin.getLang().get(p, "menus.close.loreItem"));
        inv.setItem(10, nowLevel);
        double perGlass = (level.getLevelUp() - level.getXp()) * 0.2;
        double counter = 0.0;
        double progress = sw.getXp() - level.getXp();
        int i = 0;
        while (counter < level.getLevelUp()) {
            if (i >= slotsLevel.length) break;
            if (progress > counter) {
                inv.setItem(slotsLevel[i], glassGreen);
            } else {
                inv.setItem(slotsLevel[i], glassYellow);
            }
            counter += perGlass;
            i++;
        }
        inv.setItem(16, nextLevel);
        inv.setItem(31, prestige);
        inv.setItem(49, close);
        if (sw.isShowLevel()) {
            inv.setItem(50, hide);
        } else {
            inv.setItem(50, show);
        }
        p.openInventory(inv);
    }

    public void createSelectorMenu(Player p, String ignore, String type) {
        views.put(p.getUniqueId(), type);
        int page = plugin.getUim().getPages().get(p.getUniqueId());
        GameSelectorMenu gss = (GameSelectorMenu) plugin.getUim().getMenus("game" + type + "selector");
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.selector.title").replaceAll("<type>", plugin.getLang().get(p, "selector." + type)));
        fillInventory(p, inv, ignore, page, gss, type);
        p.openInventory(inv);
    }

    public void updateGameMenu(Player p, Inventory inv, String ignore, String type) {
        inv.clear();
        int page = plugin.getUim().getPages().getOrDefault(p.getUniqueId(), 1);
        GameSelectorMenu gss = (GameSelectorMenu) plugin.getUim().getMenus("game" + type + "selector");
        fillInventory(p, inv, ignore, page, gss, type);
    }

    private void fillInventory(Player p, Inventory inv, String ignore, int page, GameSelectorMenu gss, String type) {
        for (int s : gss.getExtra()) {
            inv.setItem(s, gss.getContents().get(s));
        }
        ArrayList<Integer> gs = gss.getGameSlots();
        List<GameData> games = (!type.equals("all") ? plugin.getGm().getGameData().values().stream().filter(d -> d.getType().equals(type)).collect(Collectors.toList()) : new ArrayList<>(plugin.getGm().getGameData().values()));
        int i = 0, counter = 0;
        for (GameData game : games) {
            if (!ignore.equals("none") && game.getMap().equals(ignore)) {
                continue;
            }
            counter++;
            if (counter <= (page - 1) * Math.max(gs.size(), 1)) {
                continue;
            }
            if (gs.size() <= i) {
                continue;
            }
            boolean fav = plugin.getDb().getSWPlayer(p).getFavorites().contains(game.getMap());
            ItemStack g = getFirework((fav) ? "FAVORITE" : game.getState(), ((fav) ? "§b" : "§e") + game.getMap(), plugin.getLang().get(p, "menus.selector.loreItem").replaceAll("<state>", plugin.getLang().get(p, "selector." + game.getState().toLowerCase())).replaceAll("<players>", String.valueOf(game.getPlayers())).replaceAll("<max>", String.valueOf(game.getMax())).replaceAll("<mode>", plugin.getLang().get(p, "selector." + game.getType().toLowerCase())));
            inv.setItem(gs.get(i), NBTEditor.set(g, game.getServer(), "ITEM_SW_JOIN_SERVER"));
            i++;
        }
        int slot = gss.getSlot("{RANDOM}");
        int slot2 = gss.getSlot("{FAVORITES}");
        int slot3 = gss.getSlot("{CLOSE}");
        int slot4 = gss.getSlot("{LAST}");
        int slot5 = gss.getSlot("{NEXT}");
        Map<Integer, ItemStack> contents = gss.getContents();
        if (slot > -1 && slot < 54) {
            inv.setItem(slot, contents.get(slot));
        }
        if (slot2 > -1 && slot2 < 54) {
            inv.setItem(slot2, contents.get(slot2));
        }
        if (slot3 > -1 && slot3 < 54) {
            inv.setItem(slot3, contents.get(slot3));
        }
        if (page > 1) {
            if (slot4 > -1 && slot4 < 54) {
                inv.setItem(slot4, contents.get(slot4));
            }
        }
        if (page < Utils.getMaxPages(games.size(), gs.size())) {
            if (slot5 > -1 && slot5 < 54) {
                inv.setItem(slot5, contents.get(slot5));
            }
        }
    }

    public void createPerksMenu(Player p, String type) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.perks.title"));
        SWPlayer sw = plugin.getDb().getSWPlayer(p);
        for (Perk perk : plugin.getIjm().getPerks().getPem().getPerks().values()) {
            if (!perk.getGameTypes().contains(type)) continue;
            inv.setItem(perk.getSlot(), perk.toIcon(p, sw));
        }
        ItemStack close = ItemBuilder.item(plugin.getCm().getBack(), plugin.getLang().get(p, "menus.back.nameItem"), plugin.getLang().get(p, "menus.back.loreItem"));
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createCubeletsAnimationMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, plugin.getLang().get(p, "menus.cubelets.title"));
        ItemStack firework = ItemBuilder.item(XMaterial.FIREWORK_ROCKET, 1, plugin.getLang().get(p, "menus.cubelets.fireworks.nameItem"), plugin.getLang().get(p, "menus.cubelets.fireworks.loreItem").replaceAll("<status>", getState(p, plugin.getIjm().getCubelets().getCubelets().getInt("animations.fireworks.id"), plugin.getIjm().getCubelets().getCubelets().get(null, "animations.fireworks.perm"))));
        ItemStack head = ItemBuilder.createSkull(plugin.getLang().get(p, "menus.cubelets.head.nameItem"), plugin.getLang().get(p, "menus.cubelets.head.loreItem").replaceAll("<status>", getState(p, plugin.getIjm().getCubelets().getCubelets().getInt("animations.head.id"), plugin.getIjm().getCubelets().getCubelets().get(null, "animations.head.perm"))), plugin.getCm().getUrl());
        ItemStack circle = ItemBuilder.item(XMaterial.LAVA_BUCKET, plugin.getLang().get(p, "menus.cubelets.flames.nameItem"), plugin.getLang().get(p, "menus.cubelets.flames.loreItem").replaceAll("<status>", getState(p, plugin.getIjm().getCubelets().getCubelets().getInt("animations.flames.id"), plugin.getIjm().getCubelets().getCubelets().get(null, "animations.flames.perm"))));
        inv.setItem(10, firework);
        inv.setItem(11, head);
        inv.setItem(12, circle);
        p.openInventory(inv);
    }

    public void createSettingsMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get(p, "menus.soulwellsettings.title"));
        SWPlayer sw = plugin.getDb().getSWPlayer(p);
        ItemStack add = ItemBuilder.item(XMaterial.ARROW, plugin.getLang().get(p, "menus.soulwellsettings.add.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.add.loreItem"));
        ItemStack remove = ItemBuilder.item(XMaterial.ARROW, plugin.getLang().get(p, "menus.soulwellsettings.remove.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.remove.loreItem"));
        ItemStack s1y = ItemBuilder.item(XMaterial.TERRACOTTA, plugin.getLang().get(p, "menus.soulwellsettings.yesHas.nameItem").replaceAll("<rows>", "1"), plugin.getLang().get(p, "menus.soulwellsettings.yesHas.loreItem").replaceAll("<rows>", "1"));
        ItemStack s2y = ItemBuilder.item(XMaterial.TERRACOTTA, plugin.getLang().get(p, "menus.soulwellsettings.yesHas.nameItem").replaceAll("<rows>", "2"), plugin.getLang().get(p, "menus.soulwellsettings.yesHas.loreItem").replaceAll("<rows>", "2"));
        ItemStack s3y = ItemBuilder.item(XMaterial.TERRACOTTA, plugin.getLang().get(p, "menus.soulwellsettings.yesHas.nameItem").replaceAll("<rows>", "3"), plugin.getLang().get(p, "menus.soulwellsettings.yesHas.loreItem").replaceAll("<rows>", "3"));
        ItemStack s4y = ItemBuilder.item(XMaterial.TERRACOTTA, plugin.getLang().get(p, "menus.soulwellsettings.yesHas.nameItem").replaceAll("<rows>", "4"), plugin.getLang().get(p, "menus.soulwellsettings.yesHas.loreItem").replaceAll("<rows>", "4"));
        ItemStack s5y = ItemBuilder.item(XMaterial.TERRACOTTA, plugin.getLang().get(p, "menus.soulwellsettings.yesHas.nameItem").replaceAll("<rows>", "5"), plugin.getLang().get(p, "menus.soulwellsettings.yesHas.loreItem").replaceAll("<rows>", "5"));
        ItemStack s2n = ItemBuilder.item(XMaterial.GRAY_TERRACOTTA, 1, plugin.getLang().get(p, "menus.soulwellsettings.notHas.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.notHas.loreItem"));
        ItemStack s3n = ItemBuilder.item(XMaterial.GRAY_TERRACOTTA, 1, plugin.getLang().get(p, "menus.soulwellsettings.notHas.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.notHas.loreItem"));
        ItemStack s4n = ItemBuilder.item(XMaterial.GRAY_TERRACOTTA, 1, plugin.getLang().get(p, "menus.soulwellsettings.notHas.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.notHas.loreItem"));
        ItemStack s5n = ItemBuilder.item(XMaterial.GRAY_TERRACOTTA, 1, plugin.getLang().get(p, "menus.soulwellsettings.notHas.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.notHas.loreItem"));
        ItemStack normal = ItemBuilder.item(XMaterial.GREEN_STAINED_GLASS, plugin.getLang().get(p, "menus.soulwellsettings.normal.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.normal.loreItem").replaceAll("<status>", (sw.getSoulanimation() == 0) ? plugin.getLang().get(p, "menus.soulwellsettings.status.selected") : plugin.getLang().get(p, "menus.soulwellsettings.status.select")));
        ItemStack blaze = ItemBuilder.item(XMaterial.BLAZE_POWDER, plugin.getLang().get(p, "menus.soulwellsettings.blaze.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.blaze.loreItem").replaceAll("<status>", (sw.getSoulanimation() == 1) ? plugin.getLang().get(p, "menus.soulwellsettings.status.selected") : plugin.getLang().get(p, "menus.soulwellsettings.status.select")));
        ItemStack d3 = ItemBuilder.item(XMaterial.ARMOR_STAND, plugin.getLang().get(p, "menus.soulwellsettings.3d.nameItem"), plugin.getLang().get(p, "menus.soulwellsettings.3d.loreItem").replaceAll("<status>", (sw.getSoulanimation() == 2) ? plugin.getLang().get(p, "menus.soulwellsettings.status.selected") : plugin.getLang().get(p, "menus.soulwellsettings.status.select")));
        inv.setItem(12, normal);
        inv.setItem(13, blaze);
        inv.setItem(14, d3);
        inv.setItem(28, remove);
        inv.setItem(29, s1y);
        if (sw.getRows() > 1) {
            inv.setItem(30, s2y);
        } else {
            inv.setItem(30, s2n);
        }
        if (sw.getRows() > 2) {
            inv.setItem(31, s3y);
        } else {
            inv.setItem(31, s3n);
        }
        if (sw.getRows() > 3) {
            inv.setItem(32, s4y);
        } else {
            inv.setItem(32, s4n);
        }
        if (sw.getRows() > 4) {
            inv.setItem(33, s5y);
        } else {
            inv.setItem(33, s5n);
        }
        inv.setItem(34, add);
        p.openInventory(inv);
    }

    public String getState(Player p, int id, String perm) {
        SWPlayer sw = plugin.getDb().getSWPlayer(p);
        if (!p.hasPermission(perm)) {
            return plugin.getLang().get(p, "menus.cubelets.noPerm");
        }
        if (sw.getCubeAnimation() == id) {
            return plugin.getLang().get(p, "menus.cubelets.selected");
        }
        return plugin.getLang().get(p, "menus.cubelets.select");
    }

    public int getSize(int games) {
        if (games > 45) {
            return 54;
        }
        if (games > 36) {
            return 45;
        }
        if (games > 27) {
            return 36;
        }
        if (games > 18) {
            return 27;
        }
        if (games > 9) {
            return 18;
        }
        return 9;
    }

    public ConcurrentHashMap<UUID, String> getViews() {
        return views;
    }

    public ItemStack getFirework(String state, String display, String lore) {
        ItemStack star = new ItemStack(XMaterial.FIREWORK_STAR.parseMaterial(), 1);
        FireworkEffectMeta metaFw = (FireworkEffectMeta) star.getItemMeta();
        metaFw.setDisplayName(display);
        metaFw.setLore(lore.isEmpty() ? new ArrayList<>() : Arrays.asList(lore.split("\\n")));
        FireworkEffect effect;
        switch (state.toUpperCase()) {
            case "WAITING":
                effect = FireworkEffect.builder().withColor(Color.LIME).build();
                break;
            case "STARTING":
                effect = FireworkEffect.builder().withColor(Color.YELLOW).build();
                break;
            case "PREGAME":
                effect = FireworkEffect.builder().withColor(Color.FUCHSIA).build();
                break;
            case "GAME":
                effect = FireworkEffect.builder().withColor(Color.RED).build();
                break;
            case "EMPTY":
                effect = FireworkEffect.builder().withColor(Color.WHITE).build();
                break;
            case "FINISH":
                effect = FireworkEffect.builder().withColor(Color.BLUE).build();
                break;
            case "RESTARTING":
                effect = FireworkEffect.builder().withColor(Color.PURPLE).build();
                break;
            default:
                effect = FireworkEffect.builder().withColor(Color.AQUA).build();
                break;
        }
        metaFw.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        metaFw.setEffect(effect);
        star.setItemMeta(metaFw);
        return star;
    }

    public void updateInventories(String type, String ignore) {
        /*switch (type.toUpperCase()) {
            case "ALL": {
                plugin.getGem().getAllView().removeIf(p -> p == null || !p.isOnline() || p.getOpenInventory() == null || p.getOpenInventory().getTopInventory() == null);
                plugin.getGem().getAllView().removeIf(p -> !p.getOpenInventory().getTitle().equals(plugin.getLang().get(p, "menus.selector.title").replaceAll("<type>", plugin.getLang().get(p, "selector.all"))));
                for (Player p : plugin.getGem().getAllView()) {
                    InventoryView inv = p.getOpenInventory();
                    plugin.getGem().updateGameAllMenu(p, inv.getTopInventory(), ignore);
                }
                break;
            }
            case "SOLO": {
                plugin.getGem().getSoloView().removeIf(p -> p == null || !p.isOnline() || p.getOpenInventory() == null || p.getOpenInventory().getTopInventory() == null);
                plugin.getGem().getSoloView().removeIf(p -> !p.getOpenInventory().getTitle().equals(plugin.getLang().get(p, "menus.selector.title").replaceAll("<type>", plugin.getLang().get(p, "selector.normal"))));
                for (Player p : plugin.getGem().getSoloView()) {
                    InventoryView inv = p.getOpenInventory();
                    plugin.getGem().updateGameNormalMenu(p, inv.getTopInventory(), ignore);
                }
                break;
            }
            case "TEAM": {
                plugin.getGem().getTeamView().removeIf(p -> p == null || !p.isOnline() || p.getOpenInventory() == null || p.getOpenInventory().getTopInventory() == null);
                plugin.getGem().getTeamView().removeIf(p -> !p.getOpenInventory().getTitle().equals(plugin.getLang().get(p, "menus.selector.title").replaceAll("<type>", plugin.getLang().get(p, "selector.team"))));
                for (Player p : plugin.getGem().getTeamView()) {
                    InventoryView inv = p.getOpenInventory();
                    plugin.getGem().updateGameTeamMenu(p, inv.getTopInventory(), ignore);
                }
                break;
            }
            case "RANKED":
                plugin.getGem().getRankedView().removeIf(p -> p == null || !p.isOnline() || p.getOpenInventory() == null || p.getOpenInventory().getTopInventory() == null);
                plugin.getGem().getRankedView().removeIf(p -> !p.getOpenInventory().getTitle().equals(plugin.getLang().get(p, "menus.selector.title").replaceAll("<type>", plugin.getLang().get(p, "selector.ranked"))));
                for (Player p : plugin.getGem().getRankedView()) {
                    InventoryView inv = p.getOpenInventory();
                    plugin.getGem().updateGameRankedMenu(p, inv.getTopInventory(), ignore);
                }
                break;
        }*/
    }

}