package io.github.Leonardo0013YT.UltraSkyWars.setup;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.cosmetics.kits.Kit;
import io.github.Leonardo0013YT.UltraSkyWars.enums.RewardType;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.Cosmetic;
import io.github.Leonardo0013YT.UltraSkyWars.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraSkyWars.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SoulWellSetup {
    
    private final UltraSkyWars plugin;
    private final ArrayList<SoulWellRewardSetup> rewards = new ArrayList<>();
    private SoulWellRewardSetup actual;
    
    public SoulWellSetup(UltraSkyWars plugin){
        this.plugin = plugin;
    }
    
    public SoulWellRewardSetup getActual(){
        return actual;
    }
    
    public void setActual(SoulWellRewardSetup actual){
        this.actual = actual;
    }
    
    public ArrayList<SoulWellRewardSetup> getRewards(){
        return rewards;
    }
    
    public void saveSoulWellReward(Player p){
        SoulWellRewardSetup tts = actual;
        rewards.add(tts);
        actual = null;
        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.save"));
    }
    
    public void saveSoulWell(Player p){
        int id = plugin.getLvl().getNextID();
        for ( SoulWellRewardSetup swrs : rewards ){
            plugin.getRewards().set("rewards." + id + ".name", swrs.getName());
            plugin.getRewards().set("rewards." + id + ".type", swrs.getType().name());
            plugin.getRewards().set("rewards." + id + ".chance", swrs.getChance());
            Material m = swrs.getIcon().getType();
            if (m.equals(XMaterial.PLAYER_HEAD.parseMaterial())){
                plugin.getRewards().set("rewards." + id + ".icon", NBTEditor.getTexture(swrs.getIcon()));
                plugin.getRewards().set("rewards." + id + ".iconName", (swrs.getIcon().getItemMeta().hasDisplayName()) ? swrs.getIcon().getItemMeta().getDisplayName() : "§aDefault Name");
                plugin.getRewards().set("rewards." + id + ".iconLore", (swrs.getIcon().getItemMeta().hasLore()) ? swrs.getIcon().getItemMeta().getLore() : new ArrayList<>());
            } else {
                plugin.getRewards().set("rewards." + id + ".icon", swrs.getIcon());
            }
            plugin.getRewards().set("rewards." + id + ".cmds", swrs.getCmds());
            id++;
        }
        plugin.getRewards().save();
        plugin.getLvl().reload();
        p.sendMessage(plugin.getLang().get(p, "setup.soulwellreward.saveC"));
    }
    
    public void autoCreateRewards(Player p){
        for ( Cosmetic cosmetic : UltraSkyWars.get().getCos().getCosmetics() ){
            if (cosmetic.getName().equalsIgnoreCase("default")) continue;
            RewardType type = RewardType.getRandom();
            SoulWellRewardSetup swrs = new SoulWellRewardSetup(cosmetic.getName(), type, type.getChance());
            swrs.setIcon(cosmetic.getIcon());
            swrs.getCmds().add("/sw " + cosmetic.getTypeFormatted() + " add <player> " + cosmetic.getId());
            rewards.add(swrs);
        }
        
        for ( Kit kit : plugin.getKm().getKits().values() ){
            RewardType type = RewardType.getRandom();
            SoulWellRewardSetup swrs = new SoulWellRewardSetup(kit.getName(), type, type.getChance());
            swrs.setIcon(kit.getIcon());
            swrs.getCmds().add("/sw kit add <player> " + kit.getId());
            rewards.add(swrs);
        }
        
        saveSoulWell(p);
    }
}