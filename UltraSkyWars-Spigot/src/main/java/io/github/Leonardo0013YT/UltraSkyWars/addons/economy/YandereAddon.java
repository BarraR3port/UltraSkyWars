package io.github.Leonardo0013YT.UltraSkyWars.addons.economy;

import com.podcrash.commissions.yandere.core.common.data.server.ServerType;
import com.podcrash.commissions.yandere.core.common.data.user.User;
import com.podcrash.commissions.yandere.core.common.data.user.props.GainSource;
import com.podcrash.commissions.yandere.core.spigot.Main;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.EconomyAddon;
import org.bukkit.entity.Player;

public class YandereAddon implements EconomyAddon {
    
    private final Main yandereCore;
    
    public YandereAddon(Main plugin){
        this.yandereCore = plugin;
    }
    
    @Override
    public void setCoins(Player p, double amount, GainSource source){
        User user = yandereCore.getPlayers().getCachedPlayer(p.getUniqueId());
        yandereCore.getPlayers().setCoins(user, (long) amount, GainSource.COMMAND, ServerType.SKY_WARS);
    }
    
    @Override
    public void addCoins(Player p, double amount, GainSource source){
        User user = yandereCore.getPlayers().getCachedPlayer(p.getUniqueId());
        yandereCore.getPlayers().addCoins(user, (long) amount, GainSource.COMMAND, ServerType.SKY_WARS);
    }
    
    @Override
    public void removeCoins(Player p, double amount, GainSource source){
        User user = yandereCore.getPlayers().getCachedPlayer(p.getUniqueId());
        yandereCore.getPlayers().removeCoins(user, (long) amount, GainSource.COMMAND, ServerType.SKY_WARS);
    }
    
    @Override
    public double getCoins(Player p){
        return yandereCore.getPlayers().getCachedPlayer(p.getUniqueId()).getCoins();
    }
}
