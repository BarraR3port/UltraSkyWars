package io.github.Leonardo0013YT.UltraSkyWars;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.wrappers.WorldWrapper;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import io.github.Leonardo0013YT.UltraSkyWars.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.WorldEdit;
import io.github.Leonardo0013YT.UltraSkyWars.objects.GlassBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WorldEditUtils_New implements WorldEdit {

    private final HashMap<String, Clipboard> cache = new HashMap<>();
    private final String path;
    private final UltraSkyWars plugin;

    public WorldEditUtils_New(UltraSkyWars plugin) {
        this.plugin = plugin;
        this.path = Bukkit.getWorldContainer() + "/plugins/FastAsyncWorldEdit/schematics";
    }

    @Override
    public void paste(Location loc, String schematic, boolean air, CallBackAPI<Boolean> done){
        if (loc == null || loc.getWorld() == null) return;
        String s = schematic.replaceAll(".schematic", "").replaceAll(".schem", "");
        BlockVector3 v = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        World w = FaweAPI.getWorld(loc.getWorld().getName());
        if (!cache.containsKey(s)) {
            File file = new File(path, s + ".schem");
            ClipboardFormat cf = ClipboardFormats.findByFile(file);
            try {
                if (cf != null) {
                    cache.put(s, cf.load(file));
                }
            } catch (IOException ignored) {
            }
        }
        if (cache.containsKey(s)){
            Clipboard sh = cache.get(s);
            EditSession editSession = sh.paste(w, v, false, air, false,null);
            editSession.flushSession();
            done.done(true);
        }
    }

    @Override
    public HashMap<Vector, GlassBlock> getBlocks(String schematic){
        HashMap<Vector, GlassBlock> blocks = new HashMap<>();
        String s = schematic.replaceAll(".schematic", "").replaceAll(".schem", "");
        if (!cache.containsKey(s)) {
            File file = new File(path, s + ".schem");
            ClipboardFormat cf = ClipboardFormats.findByFile(file);
            try {
                if (cf != null) {
                    cache.put(s, cf.load(file));
                }
            } catch (IOException ignored) {
            }
        }
        if (cache.containsKey(s)){
            Clipboard clipboard = cache.get(s);
            for (int x2 = clipboard.getMinimumPoint().getBlockX(); x2 <= clipboard.getMaximumPoint().getBlockX(); x2++) {
                for (int y2 = clipboard.getMinimumPoint().getBlockY(); y2 <= clipboard.getMaximumPoint().getBlockY(); y2++) {
                    for (int z2 = clipboard.getMinimumPoint().getBlockZ(); z2 <= clipboard.getMaximumPoint().getBlockZ(); z2++) {
                        int x = x2 - clipboard.getMinimumPoint().getBlockX();
                        int y = y2 - clipboard.getMinimumPoint().getBlockY();
                        int z = z2 - clipboard.getMinimumPoint().getBlockZ();
                        BaseBlock block = clipboard.getFullBlock(BlockVector3.at(x2, y2, z2));
                        if (!block.getMaterial().isAir()){
                            blocks.put(new Vector(x, y, z), new GlassBlock(Material.valueOf(block.getBlockType().getId().replaceFirst("minecraft:", "").toUpperCase()), 0));
                        }
                    }
                }
            }
        }
        return blocks;
    }

}