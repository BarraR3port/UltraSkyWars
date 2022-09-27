package io.github.Leonardo0013YT.UltraSkyWars.worldedit;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.config.BBC;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.object.schematic.StructureFormat;
import com.boydti.fawe.util.MainUtil;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.util.io.file.FilenameException;
import com.sk89q.worldedit.world.World;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.IWorldEdit;
import io.github.Leonardo0013YT.UltraSkyWars.objects.GlassBlock;
import io.github.Leonardo0013YT.UltraSkyWars.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class WorldEditUtils_Old implements IWorldEdit {
    
    private final HashMap<String, Schematic> cache = new HashMap<>();
    private final String path;
    private final WorldEdit worldEdit;
    
    public WorldEditUtils_Old(UltraSkyWars plugin){
        this.path = Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics";
        this.worldEdit = WorldEdit.getInstance();
    }
    
    @Override
    public void paste(Location loc, String schematic, boolean air, CallBackAPI<Boolean> done){
        String s = schematic.replaceAll(".schematic", "");
        Vector v = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        World w = FaweAPI.getWorld(loc.getWorld().getName());
        if (!cache.containsKey(s)){
            File file = new File(path, s + ".schematic");
            ClipboardFormat cf = ClipboardFormat.findByFile(file);
            try {
                if (cf != null){
                    cache.put(s, cf.load(file));
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
        if (cache.containsKey(s)){
            Schematic sh = cache.get(s);
            EditSession editSession = sh.paste(w, v, false, air, null);
            editSession.flushQueue();
            done.done(true);
        }
    }
    
    @Override
    public HashMap<org.bukkit.util.Vector, GlassBlock> getBlocks(String schematic){
        HashMap<org.bukkit.util.Vector, GlassBlock> blocks = new HashMap<>();
        String s = schematic.replaceAll(".schematic", "");
        if (!cache.containsKey(s)){
            File file = new File(path, s + ".schematic");
            ClipboardFormat cf = ClipboardFormat.findByFile(file);
            try {
                if (cf != null){
                    cache.put(s, cf.load(file));
                }
            } catch (IOException ignored) {
            }
        }
        if (cache.containsKey(s)){
            Schematic sh = cache.get(s);
            Clipboard clipboard = sh.getClipboard();
            for ( int x2 = clipboard.getMinimumPoint().getBlockX(); x2 <= clipboard.getMaximumPoint().getBlockX(); x2++ ){
                for ( int y2 = clipboard.getMinimumPoint().getBlockY(); y2 <= clipboard.getMaximumPoint().getBlockY(); y2++ ){
                    for ( int z2 = clipboard.getMinimumPoint().getBlockZ(); z2 <= clipboard.getMaximumPoint().getBlockZ(); z2++ ){
                        int x = x2 - clipboard.getMinimumPoint().getBlockX() - 2;
                        int y = y2 - clipboard.getMinimumPoint().getBlockY() - 1;
                        int z = z2 - clipboard.getMinimumPoint().getBlockZ() - 2;
                        BaseBlock block = clipboard.getBlock(new Vector(x2, y2, z2));
                        if (!block.isAir()){
                            blocks.put(new org.bukkit.util.Vector(x, y, z), new GlassBlock(Material.getMaterial(block.getType()), block.getData()));
                        }
                    }
                }
            }
        }
        return blocks;
    }
    
    @Override
    public void changeSchematicColors(Player p, String schematic, boolean team){
        String s = schematic.replaceAll(".schematic", "");
        File file = new File(path, s + ".schematic");
        ClipboardFormat cf = ClipboardFormat.findByFile(file);
        WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        try {
            if (cf != null){
                Schematic sh = cf.load(file);
                Clipboard clipboard = sh.getClipboard();
                for ( int color = 0; color <= 16; color++ ){
                    Clipboard colorClipboard = sh.getClipboard();
                    for ( int x2 = clipboard.getMinimumPoint().getBlockX(); x2 <= clipboard.getMaximumPoint().getBlockX(); x2++ ){
                        for ( int y2 = clipboard.getMinimumPoint().getBlockY(); y2 <= clipboard.getMaximumPoint().getBlockY(); y2++ ){
                            for ( int z2 = clipboard.getMinimumPoint().getBlockZ(); z2 <= clipboard.getMaximumPoint().getBlockZ(); z2++ ){
                                BaseBlock block = clipboard.getBlock(new Vector(x2, y2, z2));
                                if (!block.isAir()){
                                    if (block.getType() == 95){
                                        try {
                                            colorClipboard.setBlock(new Vector(x2, y2, z2), color != 16 ? (new BaseBlock(95, color, block.getNbtData())) : new BaseBlock(0));
                                        } catch (WorldEditException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                
                            }
                        }
                    }
                    Schematic schem = new Schematic(colorClipboard);
                    try {
                        com.sk89q.worldedit.entity.Player player = new BukkitPlayer(worldEditPlugin, null, p);
                        save(player, schem, (color != 16 ? "sw_simple_cage_" + (team ? "team_" : "") + XMaterial.matchXMaterial(95, color).name() : "air").toLowerCase());
                    } catch (FilenameException | NullPointerException |
                             NoSuchElementException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
        
        
    }
    
    
    private void save(com.sk89q.worldedit.entity.Player player, Schematic schematic, String fileName) throws FilenameException{
        final LocalConfiguration config = this.worldEdit.getConfiguration();
        File dir = this.worldEdit.getWorkingDirectoryFile(config.saveDir);
        ClipboardFormat format = ClipboardFormat.findByAlias("schematic");
        File f = this.worldEdit.getSafeSaveFile(player, dir, fileName, format.getExtension(), format.getExtension());
        if (f.getName().replaceAll("." + format.getExtension(), "").isEmpty()){
            File directory = f.getParentFile();
            if (directory.exists()){
                int max = MainUtil.getMaxFileId(directory);
                f = new File(directory, max + "." + format.getExtension());
            } else {
                f = new File(directory, "1." + format.getExtension());
            }
        }
        final File parent = f.getParentFile();
        if ((parent != null) && !parent.exists()){
            if (!parent.mkdirs()){
                try {
                    Files.createDirectories(parent.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Could not create folder for schematics!");
                    return;
                }
            }
        }
        try {
            if (!f.exists()){
                f.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(f)) {
                //final ClipboardHolder holder = session.getClipboard();
                final Clipboard clipboard = schematic.getClipboard();
                
                //if(holder instanceof URIClipboardHolder) uri = ((URIClipboardHolder) holder).getURI(clipboard);
                try (ClipboardWriter writer = format.getWriter(fos)) {
                    if (writer instanceof StructureFormat){
                        ((StructureFormat) writer).write(clipboard, null, player.getName());
                    } else {
                        writer.write(clipboard, null);
                    }
                    System.out.println(player.getName() + " saved " + f.getCanonicalPath());
                    BBC.SCHEMATIC_SAVED.send(player, fileName);
                }
                
            }
        } catch (IllegalArgumentException e) {
            player.printError("Unknown filename: " + fileName);
            e.printStackTrace();
        } catch (IOException e) {
            player.printError("Schematic could not written: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}