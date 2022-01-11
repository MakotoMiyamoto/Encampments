package com.makotomiyamoto.nt.encampments.core.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.NTEUtils;
import com.makotomiyamoto.nt.encampments.core.desht.Cuboid;
import com.makotomiyamoto.nt.encampments.core.event.BlockBreakByNDEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;
import java.util.logging.Level;

public class BlockEventListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (NTEGlobals.getAdminPlayers().contains(event.getPlayer())) {
            if (NTEGlobals.playerIsAdminMode(event.getPlayer()) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
                if (!Objects.requireNonNull(event.getBlock()).getType().equals(Material.AIR)) {
                    NTEGlobals.Admin.setPos1(event.getBlock().getLocation());
                    NTEGlobals.Admin.getPos2().setWorld(NTEGlobals.Admin.getPos1().getWorld());
                    event.getPlayer().sendMessage(String.format("Pos1 set. (%d)", new Cuboid(NTEGlobals.Admin.getPos1(), NTEGlobals.Admin.getPos2()).getVolume()));
                    event.setCancelled(true);
                    return;
                }
            }
            var location = event.getBlock().getLocation();
            if (event.getBlock().getBlockData() instanceof Door door && door.getHalf().equals(Bisected.Half.TOP)) {
                location.subtract(0, 1, 0);
            }
            NTEGlobals.Admin.tempSaveAdminBlockToCache(location);
            return;
        }
        NTEUtils.setBlockToRestoreCache(event);
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent event) {
        for (BlockFace blockFace : BlockFace.values()) {
            Block relative = event.getBlock().getRelative(blockFace);
            Boolean changedByAdmin = NTEGlobals.Admin.getBlocksChangedByAdmins().get(relative.getLocation());
            if (changedByAdmin != null && changedByAdmin) {
                NTEGlobals.Admin.tempSaveAdminBlockToCache(event.getBlock().getLocation());
                return;
            }
        }
        NTEUtils.setBlockToRestoreCache(event);
    }

    @EventHandler
    public void onBlockND(BlockBreakByNDEvent event) {
        NTEUtils.setBlockToRestoreCache(event);
    }
}
