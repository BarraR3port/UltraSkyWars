package io.github.Leonardo0013YT.UltraSkyWars.chests;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import lombok.Getter;
import net.lymarket.lyapi.spigot.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SWChest {
    
    @Getter
    private final List<ChestItem> centerItems = new ArrayList<>(),
            chestItems = new ArrayList<>(),
            bowItems = new ArrayList<>(),
            projectiles = new ArrayList<>(),
            bootsItems = new ArrayList<>(),
            swordItems = new ArrayList<>(),
            axeItems = new ArrayList<>(),
            pickaxeItems = new ArrayList<>(),
            helmetItems = new ArrayList<>(),
            chestPlateItems = new ArrayList<>(),
            leggingsItems = new ArrayList<>(),
            blockItems = new ArrayList<>();
    private final UltraSkyWars plugin;
    private final String path;
    private int amount;
    
    public SWChest(UltraSkyWars plugin, String path){
        this.plugin = plugin;
        this.path = path;
        loadItems();
    }
    
    public void loadItems(){
        if (!plugin.getChests().getConfig().isConfigurationSection(path)){
            Bukkit.getConsoleSender().sendMessage("§4This chest no has items.");
            return;
        }
        ConfigurationSection conf = plugin.getChests().getConfig().getConfigurationSection(path);
        for ( String c : conf.getKeys(false) ){
            ItemStack item = plugin.getChests().getConfig().getItemStack(path + "." + c + ".item");
            if (item == null) continue;
            int chance = plugin.getChests().getConfig().getInt(path + "." + c + ".chance");
            boolean center = plugin.getChests().getConfig().getBoolean(path + "." + c + ".center");
            if (item.getType().name().endsWith("SWORD")){
                getSpecialItems(c, item, chance, center, swordItems);
            } else if (item.getType().name().endsWith("_AXE")){
                getSpecialItems(c, item, chance, center, axeItems);
            } else if (item.getType().name().endsWith("PICKAXE")){
                getSpecialItems(c, item, chance, center, pickaxeItems);
            } else if (item.getType().name().endsWith("HELMET")){
                getSpecialItems(c, item, chance, center, helmetItems);
            } else if (item.getType().name().endsWith("CHESTPLATE")){
                getSpecialItems(c, item, chance, center, chestPlateItems);
            } else if (item.getType().name().endsWith("LEGGINGS")){
                getSpecialItems(c, item, chance, center, leggingsItems);
            } else if (item.getType().name().endsWith("BOOTS")){
                getSpecialItems(c, item, chance, center, bootsItems);
            } else if (item.getType().name().contains("BOW")){
                getSpecialItems(c, item, chance, center, bowItems);
            } else if (item.getType().name().contains("TNT")){
                ItemStack tnt = new ItemBuilder(Material.TNT)
                        .setDisplayName("&cTNT Instantanea")
                        .addTag("tnt-prop", "insta")
                        .build();
                getSpecialItems(c, tnt, chance, center, chestItems);
            } else if (item.getType().name().contains("SNOW_BALL") || item.getType().name().contains("SNOWBALL") || item.getType().name().contains("EGG") || item.getType().name().contains("ARROW")){
                getSpecialItems(c, item, chance, center, projectiles);
            } else if (item.getType().isBlock()){
                getSpecialItems(c, item, chance, center, blockItems);
            } else {
                getSpecialItems(c, item, chance, center, chestItems);
            }
        }
        amount = swordItems.size() + bowItems.size() + projectiles.size() + bootsItems.size() + leggingsItems.size() + chestPlateItems.size() + helmetItems.size() + bootsItems.size() + axeItems.size() + pickaxeItems.size() + blockItems.size();
    }
    
    private void getSpecialItems(String c, ItemStack item, int chance, boolean center, List<ChestItem> projectiles){
        int min = plugin.getChests().getIntOrDefault(path + "." + c + ".min", item.getAmount());
        int max = plugin.getChests().getIntOrDefault(path + "." + c + ".max", item.getAmount());
        readItem(c, item, chance, center, min, max, projectiles);
    }
    
    private void readItem(String c, ItemStack item, int chance, boolean center, int min, int max, List<ChestItem> chestItems){
        boolean refill = plugin.getChests().getBooleanOrDefault(path + "." + c + ".refill", false);
        ArrayList<String> modes = new ArrayList<>(plugin.getChests().getListOrDefault(path + "." + c + ".modes", Collections.singletonList("ALL")));
        if (!center){
            chestItems.add(new ChestItem(item, chance, min, max, false, refill, modes));
        } else {
            centerItems.add(new ChestItem(item, chance, min, max, true, refill, modes));
        }
    }
    
    public int chestAmount(){
        return amount;
    }
    
    public ItemStack getRandomHelmet(boolean refill, String mode){
        return getItemWithRefill(refill, mode, helmetItems);
    }
    
    public ItemStack getRandomChestPlate(boolean refill, String mode){
        return getItemWithRefill(refill, mode, chestPlateItems);
    }
    
    public ItemStack getRandomSword(boolean refill, String mode){
        return getItemWithRefill(refill, mode, swordItems);
    }
    
    public ItemStack getRandomAxe(boolean refill, String mode){
        return getItemWithRefill(refill, mode, axeItems);
    }
    
    public ItemStack getRandomPickaxe(boolean refill, String mode){
        return getItemWithRefill(refill, mode, pickaxeItems);
    }
    
    public ChestItem getRandomBlock(boolean refill, String mode){
        return getChestItemRefill(refill, mode, blockItems);
    }
    
    public ItemStack getRandomLeggings(boolean refill, String mode){
        return getItemWithRefill(refill, mode, leggingsItems);
    }
    
    public ItemStack getRandomBoots(boolean refill, String mode){
        return getItemWithRefill(refill, mode, bootsItems);
    }
    
    public ItemStack getRandomBowItem(boolean refill, String mode){
        return getItemWithRefill(refill, mode, bowItems);
    }
    
    public ChestItem getRandomProjectileItem(boolean refill, String mode){
        return getChestItemRefill(refill, mode, projectiles);
    }
    
    public ChestItem getRandomItem(boolean refill, String mode){
        return getChestItemRefill(refill, mode, chestItems);
    }
    
    public ChestItem getCenterItem(boolean refill, String mode){
        return getChestItemRefill(refill, mode, centerItems);
    }
    
    @Nullable
    private ItemStack getItemWithRefill(boolean refill, String mode, List<ChestItem> helmetItems){
        Stream<ChestItem> stream = (refill) ? helmetItems.stream().filter(ChestItem::isRefill) : helmetItems.stream();
        List<ChestItem> temp = stream.filter(c -> c.getModes().contains(mode) || c.getModes().contains("ALL")).collect(Collectors.toList());
        if (temp.isEmpty()) return null;
        return temp.get(ThreadLocalRandom.current().nextInt(0, temp.size())).getItem();
    }
    
    @Nullable
    private ChestItem getChestItemRefill(boolean refill, String mode, List<ChestItem> helmetItems){
        Stream<ChestItem> stream = (refill) ? helmetItems.stream().filter(ChestItem::isRefill) : helmetItems.stream();
        List<ChestItem> temp = stream.filter(c -> c.getModes().contains(mode) || c.getModes().contains("ALL")).collect(Collectors.toList());
        if (temp.isEmpty()) return null;
        return temp.get(ThreadLocalRandom.current().nextInt(0, temp.size()));
    }
    
    
    public List<ChestItem> getItems(){
        List<ChestItem> items = new ArrayList<>();
        items.addAll(swordItems);
        items.addAll(axeItems);
        items.addAll(pickaxeItems);
        items.addAll(bowItems);
        items.addAll(bootsItems);
        items.addAll(helmetItems);
        items.addAll(chestPlateItems);
        items.addAll(centerItems);
        items.addAll(leggingsItems);
        items.addAll(chestItems);
        items.addAll(projectiles);
        items.addAll(blockItems);
        return items;
    }
}