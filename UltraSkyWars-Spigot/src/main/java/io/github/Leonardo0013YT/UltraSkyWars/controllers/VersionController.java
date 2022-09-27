package io.github.Leonardo0013YT.UltraSkyWars.controllers;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.NMS;
import io.github.Leonardo0013YT.UltraSkyWars.nms.NMSReflectionOld;
import io.github.Leonardo0013YT.UltraSkyWars.nms.NMS_v1_8_r3;
import io.github.Leonardo0013YT.UltraSkyWars.nms.nametags.Nametags;
import io.github.Leonardo0013YT.UltraSkyWars.superclass.NMSReflection;
import org.bukkit.Bukkit;

public class VersionController {
    
    private final UltraSkyWars plugin;
    private final NMSReflection reflection;
    private final boolean is1_13to17 = false;
    private final boolean is1_9to17 = false;
    private final boolean is1_12 = false;
    private String version;
    private NMS nms;
    
    public VersionController(UltraSkyWars plugin){
        this.plugin = plugin;
        setupVersion();
        this.reflection = new NMSReflectionOld();
        
    }
    
    private void setupVersion(){
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if ("v1_8_R3".equals(version)){
                nms = new NMS_v1_8_r3();
            } else {
                plugin.sendLogMessage("§cYou have an outdated version §e1.8§c, please use version §a1.8.8§c.");
                disable();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    public void disable(){
        Bukkit.getScheduler().cancelTasks(plugin);
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
    
    public NMS getNMS(){
        return nms;
    }
    
    public Nametags getNameTag(String name, String display, String prefix){
        return new Nametags(name, display, prefix);
    }
    
    public NMSReflection getReflection(){
        return reflection;
    }
    
    public String getVersion(){
        return version;
    }
    
    public boolean is1_12(){
        return is1_12;
    }
    
    public boolean is1_9to17(){
        return is1_9to17;
    }
    
    public boolean is1_13to17(){
        return is1_13to17;
    }
}