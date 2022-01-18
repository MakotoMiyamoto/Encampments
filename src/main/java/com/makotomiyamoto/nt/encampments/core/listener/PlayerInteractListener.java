package com.makotomiyamoto.nt.encampments.core.listener;

import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.core.desht.Cuboid;
import com.makotomiyamoto.nt.encampments.util.GsonManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;
import java.util.logging.Level;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;

        if (NTEGlobals.isPlayerInAdminMode(event.getPlayer()) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
            if (!Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.AIR)) {
                NTEGlobals.Admin.setPos2(event.getClickedBlock().getLocation());
                NTEGlobals.Admin.getPos1().setWorld(NTEGlobals.Admin.getPos2().getWorld());
                event.getPlayer().sendMessage(String.format("Pos2 set. (%d)", new Cuboid(NTEGlobals.Admin.getPos1(), NTEGlobals.Admin.getPos2()).getVolume()));
                //Encampments.getInstance().getLogger().log(Level.WARNING, GsonManager.getGson().toJson(event.getClickedBlock().getBlockData()));
                //Encampments.getInstance().getLogger().log(Level.WARNING, GsonManager.getGson().toJson(event.getClickedBlock().getLocation()));
                Encampments.getInstance().getLogger().log(Level.WARNING, GsonManager.getGson().toJson(event.getClickedBlock().getLocation().getChunk()));
                //String string = GsonManager.getGson().toJson(SerializableBlockFactory.createSerializableBlock(event.getClickedBlock().getBlockData(), event.getClickedBlock().getLocation(), event.getClickedBlock().getType()));
                //Encampments.getInstance().getLogger().log(Level.WARNING, string);
            }
        }
    }
}
