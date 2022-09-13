package io.github.Leonardo0013YT.UltraSkyWars.game;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.chests.ChestItem;
import io.github.Leonardo0013YT.UltraSkyWars.chests.ChestType;
import io.github.Leonardo0013YT.UltraSkyWars.chests.SWChest;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Game;
import io.github.Leonardo0013YT.UltraSkyWars.team.Team;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TeamGameChest extends GameChest {
    
    private final Team team;
    
    public TeamGameChest(List<Location> chests, Team team) {
        super(chests);
        this.team = team;
    }
    
    public void fill(Game game, boolean refill) {
        UltraSkyWars plugin = UltraSkyWars.get();
        for ( Location l : chests ){
            if(l.getBlock().getState() instanceof Chest){
                invs.put(l, new UltraGameChest(l));
            }
        }
        if(invs.isEmpty()) return;
        ChestType ct = plugin.getCtm().getChests().get(game.getChestType());
        SWChest sw = ct.getChest();
        LinkedList<ItemStack> required = new LinkedList<>();
        // Its required to add at least 1 item of each type for a normal game.
        for ( int t = 0; t < game.getTeamSize(); t++ ){
            required.addAll(Arrays.asList(
                    sw.getRandomHelmet(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomChestPlate(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomLeggings(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomBoots(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomSword(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomPickaxe(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomAxe(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomBlock(ct.isRefillChange() && refill, game.getGameType()).getItem(),
                    sw.getRandomBlock(ct.isRefillChange() && refill, game.getGameType()).getItem(),
                    sw.getRandomBlock(ct.isRefillChange() && refill, game.getGameType()).getItem(),
                    sw.getRandomBowItem(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomProjectileItem(ct.isRefillChange() && refill, game.getGameType()).getItem(),
                    sw.getRandomProjectileItem(ct.isRefillChange() && refill, game.getGameType()).getItem()));
        }
        Collections.shuffle(required);
        
        
        LinkedList<PerChestItem> selected = new LinkedList<>();
        Integer[] perChestRandomizer = plugin.getRandomizer().getRandomizer();
        for ( int a = 0; a < required.size(); a++ ){
            selected.add(new PerChestItem(ThreadLocalRandom.current().nextInt(0, invs.size()), perChestRandomizer[a], required.get(a)));
        }
        
        ArrayList<UltraGameChest> ugcList = new ArrayList<>(invs.values());
        
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for ( int index = 0; index < ugcList.size(); index++ ){
            UltraGameChest ugc = ugcList.get(index);
            Inventory inv = ugc.getInv();
            if(inv == null) continue;
            inv.clear();
            Integer[] randomizer = plugin.getRandomizer().getRandomizer();
            int added = 0;
            for ( int r : randomizer ){
                ChestItem ci;
                if(added >= plugin.getCm().getMaxItemsChest()){
                    break;
                }
                if(ct.isRefillChange()){
                    ci = getRandomItem(sw.getRandomItem(refill, game.getGameType()));
                } else {
                    ci = getRandomItem(sw.getRandomItem(false, game.getGameType()));
                }
                
                if(ci != null && random.nextInt(0, 10000) <= ci.getPercent()){
                    if(inv.contains(ci.getItem().getType()) && !(random.nextInt(0, 10000) <= 700)) continue;
                    if(inv.getItem(r) == null){
                        inv.setItem(r, ci.getItem());
                    } else {
                        inv.addItem(ci.getItem());
                    }
                    added++;
                } else {
                    if(ci != null && random.nextInt(0, 10000) <= ci.getPercent()){
                        if(inv.contains(ci.getItem().getType()) && !(random.nextInt(0, 10000) <= 700)) continue;
                        if(inv.getItem(r) == null){
                            inv.setItem(r, ci.getItem());
                        } else {
                            inv.addItem(ci.getItem());
                        }
                        added++;
                    } else {
                        if(game.getProjectileType().isAppear()){
                            ChestItem ci2 = getRandomProjectileItem(sw.getRandomProjectileItem(ct.isRefillChange() && refill, game.getGameType()));
                            if(ci2 != null && random.nextInt(0, 50000) <= ci2.getPercent()){
                                if(inv.contains(ci2.getItem().getType()) && !(random.nextInt(0, 10000) <= 700))
                                    continue;
                                if(inv.getItem(r) == null){
                                    inv.setItem(r, ci2.getItem());
                                } else {
                                    inv.addItem(ci2.getItem());
                                }
                                added++;
                            }
                        }
                    }
                }
            }
            for ( PerChestItem pci : selected ){
                if(pci.chest == index){
                    if(inv.getItem(pci.getSlot()) == null){
                        inv.setItem(pci.getSlot(), pci.getItem());
                    } else {
                        inv.addItem(pci.getItem());
                    }
                }
            }
        }
        
    }
    
    private ChestItem getRandomProjectileItem(ChestItem item) {
        if(item == null) return null;
        if(!team.getStats().areProjectilesAvailable()) return null;
        team.getStats().addProjectiles();
        return item;
    }
    
    private ChestItem getRandomItem(ChestItem item) {
        if(item == null) return null;
        if(!team.getStats().areOthersAvailable()) return null;
        team.getStats().addOthers();
        return item;
    }
    
    
    private static class PerChestItem {
        @Getter
        private final int chest, slot;
        @Getter
        private final ItemStack item;
        
        public PerChestItem(int chest, int slot, ItemStack item) {
            this.chest = chest;
            this.slot = slot;
            this.item = item;
        }
    }
    
    
}
