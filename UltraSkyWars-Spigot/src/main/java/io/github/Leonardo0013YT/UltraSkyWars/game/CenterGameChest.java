package io.github.Leonardo0013YT.UltraSkyWars.game;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.chests.ChestItem;
import io.github.Leonardo0013YT.UltraSkyWars.chests.ChestType;
import io.github.Leonardo0013YT.UltraSkyWars.chests.SWChest;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Game;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CenterGameChest extends GameChest {
    
    public CenterGameChest(List<Location> chests) {
        super(chests);
    }
    
    
    public void fill(Game game, boolean refill){
        UltraSkyWars plugin = UltraSkyWars.get();
        for ( Location l : chests ){
            if (l.getBlock().getState() instanceof Chest){
                invs.put(l, new UltraGameChest(l));
            }
        }
        if (invs.isEmpty()) return;
    
        LinkedList<PerChestItem> tail = new LinkedList<>();
        ChestType ct = plugin.getCtm().getChests().get(game.getChestType());
        SWChest sw = ct.getChest();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        ArrayList<UltraGameChest> ugcList = new ArrayList<>(invs.values());
        for ( int index = 0; index < ugcList.size(); index++ ){
            UltraGameChest ugc = ugcList.get(index);
            Inventory inv = ugc.getInv();
            if (inv == null) continue;
            inv.clear();
            Integer[] randomizer = plugin.getRandomizer().getRandomizer();
            int added = 0;
            for ( int r : randomizer ){
                ChestItem ci;
                if (added >= plugin.getCm().getMaxItemsChest()) break;
                if (ct.isRefillChange()){
                    ci = sw.getCenterItem(refill, game.getGameType());
                } else {
                    ci = sw.getCenterItem(false, game.getGameType());
                }
                
                if(ci != null && random.nextInt(0, 10000) <= ci.getPercent()){
                    if (inv.getItem(r) == null){
                        inv.setItem(r, ci.getItem());
                    } else {
                        tail.add(new PerChestItem(index, 0, ci.getItem()));
                    }
                    added++;
                } else {
                    if (ci != null && random.nextInt(0, 10000) <= ci.getPercent()){
                        if (inv.getItem(r) == null){
                            inv.setItem(r, ci.getItem());
                        } else {
                            tail.add(new PerChestItem(index, 0, ci.getItem()));
                        }
                        added++;
                    }
                }
            }
        }
        for ( PerChestItem pci : tail ){
            UltraGameChest ugc = ugcList.get(pci.getChest());
            Inventory inv = ugc.getInv();
            if (inv == null) continue;
            if (inv.getContents().length >= plugin.getCm().getMaxItemsChest()) continue;
            List<Integer> available = IntStream.range(0, inv.getSize()).filter(i -> inv.getItem(i) == null).boxed().collect(Collectors.toList());
            if (available.isEmpty()) continue;
            int slot = available.get(random.nextInt(0, available.size()));
            inv.setItem(slot, pci.getItem());
        }
    }
    
}
