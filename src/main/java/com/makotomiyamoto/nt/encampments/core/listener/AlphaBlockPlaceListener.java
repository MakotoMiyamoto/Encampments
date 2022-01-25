package com.makotomiyamoto.nt.encampments.core.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.makotomiyamoto.nt.encampments.Encampments;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Random;

public class AlphaBlockPlaceListener implements Listener {
    @EventHandler
    public void onCampfirePlace(BlockPlaceEvent event) {
        if (event.getBlock().getBlockData() instanceof Campfire campfire) {
            System.out.println("NAG");
            campfire.setLit(false);
            event.getBlock().setBlockData(campfire);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Encampments.getCustomDrops().containsKey(event.getBlock().getType())) {
            ItemStack drop = Encampments.getCustomDrops().get(event.getBlock().getType());
            //event.getBlock().setType(Material.AIR);
            event.setDropItems(false);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
        }
    }

    @EventHandler
    public void onFlintBonkCampfire(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        PlayerInventory inventory = event.getPlayer().getInventory();
        // TODO performance note, check the block material instead of checking for an instance
        if (event.getClickedBlock().getBlockData() instanceof Campfire campfire && inventory.getItemInMainHand().getType().equals(Material.FLINT)) {
            if (campfire.isLit()) return;
            CraftPlayer p = (CraftPlayer) event.getPlayer();
            PacketContainer animation = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ANIMATION, false);
            animation.getEntityModifier(p.getWorld()).write(0, p);
            animation.getIntegers().write(1, 0);
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, animation);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (new Random().nextBoolean()) {
                event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_FIRE_AMBIENT, 2, 2f);
                campfire.setLit(true);
                event.getClickedBlock().setBlockData(campfire);
            } else {
                event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 2, 2f);
            }
            inventory.getItemInMainHand().setAmount(inventory.getItemInMainHand().getAmount() - 1);
        }
    }
}
