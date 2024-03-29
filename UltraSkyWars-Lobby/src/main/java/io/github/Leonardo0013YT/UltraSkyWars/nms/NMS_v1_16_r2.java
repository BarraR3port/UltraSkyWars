package io.github.Leonardo0013YT.UltraSkyWars.nms;

import com.mojang.datafixers.util.Pair;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.NMS;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class NMS_v1_16_r2 implements NMS {

    @Override
    public Collection<Integer> spawn(Player p, Location loc, ItemStack head) {
        EntityGiantZombie ev = new EntityGiantZombie(EntityTypes.GIANT, ((CraftWorld) loc.getWorld()).getHandle());
        EntityArmorStand ar = new EntityArmorStand(EntityTypes.ARMOR_STAND, ((CraftWorld) loc.getWorld()).getHandle());
        EntityBat bat = new EntityBat(EntityTypes.BAT, ((CraftWorld) loc.getWorld()).getHandle());
        Location l = loc.clone().add(-2, 9, 3.5);
        ar.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        ev.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        bat.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
        ar.setInvisible(true);
        ev.setInvisible(true);
        bat.setInvisible(true);
        List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R2.ItemStack>> equipmentList = new ArrayList<>();
        PacketPlayOutEntityEquipment equipment = new PacketPlayOutEntityEquipment(ev.getId(), equipmentList);
        PacketPlayOutSpawnEntityLiving ent = new PacketPlayOutSpawnEntityLiving(ev);
        PacketPlayOutSpawnEntityLiving ent2 = new PacketPlayOutSpawnEntityLiving(ar);
        PacketPlayOutSpawnEntityLiving ent3 = new PacketPlayOutSpawnEntityLiving(bat);
        PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity(bat, ar);
        PlayerConnection pc = ((CraftPlayer) p).getHandle().playerConnection;
        pc.sendPacket(ent);
        pc.sendPacket(ent2);
        pc.sendPacket(ent3);
        pc.sendPacket(attach);
        pc.sendPacket(equipment);
        return Arrays.asList(ev.getId(), ar.getId(), bat.getId());
    }

    @Override
    public void destroy(Player p, Collection<Integer> id) {
        for (int i : id) {
            PacketPlayOutEntityDestroy ent = new PacketPlayOutEntityDestroy(i);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ent);
        }
    }

    @Override
    public void displayParticle(Player p, Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount) {
        if (location.getWorld() == null) return;
        location.getWorld().spawnParticle(org.bukkit.Particle.valueOf(enumParticle), location, amount, offsetX, offsetY, offsetZ, speed);
    }

    @Override
    public void broadcastParticle(Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount, double range) {
        if (location.getWorld() == null) return;
        location.getWorld().spawnParticle(Particle.valueOf(enumParticle), location, amount, offsetX, offsetY, offsetZ, speed);
    }

}