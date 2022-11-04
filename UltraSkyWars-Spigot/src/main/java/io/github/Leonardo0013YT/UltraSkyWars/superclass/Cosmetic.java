package io.github.Leonardo0013YT.UltraSkyWars.superclass;

import io.github.Leonardo0013YT.UltraSkyWars.config.Settings;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.Purchasable;
import org.bukkit.inventory.ItemStack;

public abstract class Cosmetic implements Purchasable {
    
    protected final boolean isBuy, needPermToBuy;
    protected final int id, slot, page, price;
    protected String name, permission, autoGivePermission;
    
    public Cosmetic(Settings config, String path, String type){
        this.name = config.get(path + ".name");
        this.id = config.getInt(path + ".id");
        this.slot = config.getInt(path + ".slot");
        this.page = config.getInt(path + ".page");
        this.price = config.getInt(path + ".price");
        this.permission = config.get(path + ".permission");
        this.autoGivePermission = config.getOrDefault(path + ".autoGivePermission", "ultraskywars." + type + ".autogive." + name);
        this.isBuy = config.getBoolean(path + ".isBuy");
        this.needPermToBuy = config.getBooleanOrDefault(path + ".needPermToBuy", false);
    }
    
    public abstract ItemStack getIcon();
    
    public abstract String getTypeFormatted();
    
    public abstract String getDisplayName();
    
    @Override
    public String getPermission(){
        return permission;
    }
    
    @Override
    public String getAutoGivePermission(){
        return autoGivePermission;
    }
    
    @Override
    public boolean isBuy(){
        return isBuy;
    }
    
    @Override
    public boolean needPermToBuy(){
        return needPermToBuy;
    }
    
    @Override
    public int getPrice(){
        return price;
    }
    
    public String getName(){
        return name;
    }
    
    public int getSlot(){
        return slot;
    }
    
    public int getPage(){
        return page;
    }
    
    public int getId(){
        return id;
    }
}