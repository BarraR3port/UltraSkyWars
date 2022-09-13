package io.github.Leonardo0013YT.UltraSkyWars.chests;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class ChestItem {
    
    private final ItemStack item;
    @Getter
    private final boolean center, refill;
    private final ArrayList<String> modes;
    private final int percent;
    @Getter
    private final int min, max;
    
    public ChestItem(ItemStack item, int percent, int min, int max, boolean center, boolean refill, ArrayList<String> modes) {
        this.item = item;
        this.min = min;
        this.max = max;
        this.center = center;
        this.percent = percent;
        this.refill = refill;
        this.modes = modes;
    }
    
    public ArrayList<String> getModes() {
        return modes;
    }
    
    public int getPercent() {
        return percent * 100;
    }
    
    public ItemStack getItem() {
        ItemStack item = this.item.clone();
        if(min != max){
            int now = new Random().nextInt(this.max - this.min + 1) + this.min;
            item.setAmount(now);
            return item;
        }
        return item;
    }
    
}