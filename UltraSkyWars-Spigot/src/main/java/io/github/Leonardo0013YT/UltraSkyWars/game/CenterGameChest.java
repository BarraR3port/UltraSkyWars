package io.github.Leonardo0013YT.UltraSkyWars.game;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.chests.ChestItem;
import io.github.Leonardo0013YT.UltraSkyWars.chests.ChestType;
import io.github.Leonardo0013YT.UltraSkyWars.chests.SWChest;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Game;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CenterGameChest extends GameChest {
    
    public CenterGameChest(List<Location> chests) {
        super(chests);
    }
    
    
    public void fill(Game game, boolean refill) {
        UltraSkyWars plugin = UltraSkyWars.get();
        for ( Location l : chests ){
            if(l.getBlock().getState() instanceof Chest){
                invs.put(l, new UltraGameChest(l));
            }
        }
        if(invs.isEmpty()){
            return;
        }
        int[][] secure = new int[invs.size()][(game.getTeamSize() * 6) / invs.size()];
        for ( int i = 0; i < secure.length; i++ ){
            for ( int x = 0; x < secure[i].length; x++ ){
                secure[i][x] = plugin.getRandomizer().getSelectors()[x];
            }
        }
        int i = 0;
        int s = 0;
        ChestType ct = plugin.getCtm().getChests().get(game.getChestType());
        SWChest sw = ct.getChest();
        ArrayList<ItemStack> selected = new ArrayList<>();
        for ( int t = 0; t < game.getTeamSize(); t++ ){
            selected.addAll(Arrays.asList(
                    sw.getRandomHelmet(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomChestPlate(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomLeggings(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomBoots(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomSword(ct.isRefillChange() && refill, game.getGameType()),
                    sw.getRandomBowItem(ct.isRefillChange() && refill, game.getGameType())));
            if(!ct.isArmorAllTeams()){
                break;
            }
        }
        Collections.shuffle(selected);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for ( UltraGameChest ugc : invs.values() ){
            Inventory inv = ugc.getInv();
            if(inv == null){
                continue;
            }
            inv.clear();
            Integer[] randomizer = plugin.getRandomizer().getRandomizer();
            int added = 0;
            for ( int r : randomizer ){
                ChestItem ci;
                if(added >= plugin.getCm().getMaxItemsChest()){
                    break;
                }
                if(ct.isRefillChange()){
                    ci = sw.getCenterItem(refill, game.getGameType());
                } else {
                    ci = sw.getCenterItem(false, game.getGameType());
                }
                
                if(ci != null && random.nextInt(0, 10000) <= ci.getPercent()){
                    inv.setItem(r, ci.getItem());
                    added++;
                } else {
                    if(ci != null && random.nextInt(0, 10000) <= ci.getPercent()){
                        inv.setItem(r, ci.getItem());
                        added++;
                    }
                }
            }
            i++;
        }
    }
    
}
