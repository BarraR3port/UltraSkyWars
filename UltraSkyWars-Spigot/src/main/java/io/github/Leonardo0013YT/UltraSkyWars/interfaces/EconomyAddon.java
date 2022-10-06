package io.github.Leonardo0013YT.UltraSkyWars.interfaces;

import com.podcrash.commissions.yandere.core.common.data.user.props.GainSource;
import org.bukkit.entity.Player;

public interface EconomyAddon {
    
    void setCoins(Player p, double amount, GainSource source);
    
    void addCoins(Player p, double amount, GainSource source);
    
    void removeCoins(Player p, double amount, GainSource source);
    
    double getCoins(Player p);
    
}