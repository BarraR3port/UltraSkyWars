package io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.cmds;

import io.github.Leonardo0013YT.UltraSkyWars.TNTMadness.UltraSkyWarsTNTM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TNTMadnessCMD implements CommandExecutor {
    
    private final UltraSkyWarsTNTM plugin;
    
    public TNTMadnessCMD(UltraSkyWarsTNTM plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length < 1){
                sendHelp(sender);
                return true;
            }
            if ("give".equalsIgnoreCase(args[0])){
                if (args.length < 2){
                    sendHelp(sender);
                    return true;
                }
                switch(args[1].toLowerCase()){
                    case "instantboom":
                        p.getInventory().addItem(plugin.getIm().getInstantBoom());
                        p.sendMessage("§aSe te a entregado un InstantBoom de prueba.");
                        break;
                    case "launchpad":
                        p.getInventory().addItem(plugin.getIm().getTntLaunchPad());
                        p.sendMessage("§aSe te a entregado un Launchpad de prueba.");
                        break;
                    case "instanttnt":
                        p.getInventory().addItem(plugin.getIm().getNormalTNT());
                        p.sendMessage("§aSe te a entregado un TNT instantanea de prueba.");
                        break;
                    case "givebook":
                        p.getInventory().addItem(plugin.getIm().getTntMadnessBook());
                        p.sendMessage("§aSe te a entregado el libro de TNT Madness.");
                        break;
                    default:
                        sendHelp(sender);
                        break;
                }
            } else {
                sendHelp(sender);
            }
        }
        return false;
    }
    
    private void sendHelp(CommandSender s){
        s.sendMessage("§7§m--------------------------------------------");
        s.sendMessage("§e/uswt give instantboom/launchpad §7- §aEntregarte items tu mismo.");
        s.sendMessage("§7§m--------------------------------------------");
    }
    
}