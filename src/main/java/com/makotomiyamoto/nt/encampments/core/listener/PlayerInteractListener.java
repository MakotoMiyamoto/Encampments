package com.makotomiyamoto.nt.encampments.core.listener;

import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.core.desht.Cuboid;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;

        if (NTEGlobals.isPlayerAdminMode(event.getPlayer()) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
            if (!Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.AIR)) {
                NTEGlobals.Admin.setPos2(event.getClickedBlock().getLocation());
                NTEGlobals.Admin.getPos1().setWorld(NTEGlobals.Admin.getPos2().getWorld());
                event.getPlayer().sendMessage(String.format("Pos2 set. (%d)", new Cuboid(NTEGlobals.Admin.getPos1(), NTEGlobals.Admin.getPos2()).getVolume()));
            }
        }
    }
}
