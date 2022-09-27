package io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.managers;

import io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.UltraSkyWarsTNTM;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class ItemManager {
    
    private final UltraSkyWarsTNTM plugin;
    private final ItemStack tntLaunchPad, instantBoom, normalTNT, tntMadnessBook;
    
    public ItemManager(UltraSkyWarsTNTM plugin){
        this.plugin = plugin;
        this.tntMadnessBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) tntMadnessBook.getItemMeta();
        bm.setAuthor(plugin.getTntLang().get("book.author"));
        bm.setTitle(plugin.getTntLang().get("book.title"));
        bm.setPages(plugin.getTntLang().getList("book.pages"));
        bm.setDisplayName(plugin.getTntLang().get("items.book.nameItem"));
        bm.setLore(plugin.getTntLang().getList("items.book.loreItem"));
        tntMadnessBook.setItemMeta(bm);
        this.tntLaunchPad = ItemBuilder.item(Material.REDSTONE_BLOCK, 1, plugin.getTntLang().get("items.tntLaunchpad.nameItem"), plugin.getTntLang().get("items.tntLaunchpad.loreItem"));
        this.instantBoom = ItemBuilder.item(Material.TNT, 1, plugin.getTntLang().get("items.instantBoom.nameItem"), plugin.getTntLang().get("items.instantBoom.loreItem"));
        ItemMeta im = instantBoom.getItemMeta();
        im.addEnchant(Enchantment.DIG_SPEED, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        instantBoom.setItemMeta(im);
        this.normalTNT = ItemBuilder.item(Material.TNT, 1, plugin.getTntLang().get("items.normalTNT.nameItem"), plugin.getTntLang().get("items.normalTNT.loreItem"));
        UltraSkyWars.get().getIm().getItems().add(tntMadnessBook);
    }
    
}