package com.makotomiyamoto.nt.encampments.core.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.NTEUtils;
import com.makotomiyamoto.nt.encampments.core.desht.Cuboid;
import com.makotomiyamoto.nt.encampments.core.event.BlockBreakByNDEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.Objects;
import java.util.logging.Level;

public class BlockEventListener implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled() || NTEGlobals.isBlockCached(event.getBlock())) {
            return;
        }

        if (NTEGlobals.isPlayerInAdminMode(event.getPlayer())) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
                if (!Objects.requireNonNull(event.getBlock()).getType().equals(Material.AIR)) {
                    NTEGlobals.Admin.setPos1(event.getBlock().getLocation());
                    NTEGlobals.Admin.getPos2().setWorld(NTEGlobals.Admin.getPos1().getWorld());
                    event.getPlayer().sendMessage(String.format("Pos1 set. (%d)", new Cuboid(NTEGlobals.Admin.getPos1(), NTEGlobals.Admin.getPos2()).getVolume()));
                    event.setCancelled(true);
                    return;
                }
            }
            var location = event.getBlock().getLocation();
            if (event.getBlock().getBlockData() instanceof Bisected bisected && bisected.getHalf().equals(Bisected.Half.TOP)) {
                location.subtract(0, 1, 0);
            }
            NTEGlobals.Admin.tempSaveAdminBlockToCache(location);
            return;
        }
        NTEUtils.setBlockToRestoreCache(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockDestroy(BlockDestroyEvent event) {
        if (event.isCancelled() || NTEGlobals.isBlockCached(event.getBlock())) {
            return;
        }

        if (NTEGlobals.getRecentlyReplacedChunks().containsKey(event.getBlock().getChunk())) {
            var nteChunk = NTEGlobals.getRecentlyReplacedChunks().get(event.getBlock().getChunk());
            if (nteChunk.getChangedBlocks().containsKey(event.getBlock().getLocation())) {
                event.setCancelled(true);
                var baseBlockLocation = event.getBlock().getLocation().clone().subtract(0, 1, 0);
                if (NTEGlobals.getBrokenBlocksCache().getCache().get(event.getBlock().getChunk()).getChangedBlocks().containsKey(baseBlockLocation)) {
                    var baseChangedBlock = NTEGlobals.getBrokenBlocksCache().getCache().get(event.getBlock().getChunk()).getChangedBlocks().get(baseBlockLocation);
                    baseChangedBlock.getSerializableBlock().place();
                }
            }
            return;
        }

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

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled() || NTEGlobals.isBlockCached(event.getBlockPlaced()) || NTEGlobals.isPlayerInAdminMode(event.getPlayer())) {
            return;
        }

        Encampments.getInstance().getLogger().log(Level.WARNING, "Block placed at: " + event.getBlockPlaced().getLocation());

        if (NTEGlobals.isChunkClaimed(event.getBlock().getChunk())) {
            // handle claimed blocks
        }
        else {
            // handle unclaimed blocks
            if (!event.getBlockReplacedState().getType().equals(Material.AIR)) {
                NTEUtils.setBlockToRestoreCache(event);
            } else {
                NTEUtils.setBlockToPlacedCache(event);
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (event.isCancelled()) {
            return;
        }

        NTEUtils.setBlockToRestoreCache(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockExplode(BlockExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }

        event.blockList().forEach(block -> Encampments.getInstance().getServer().getPluginManager().callEvent(new BlockBreakByNDEvent(block)));
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }

        event.blockList().forEach(block -> Encampments.getInstance().getServer().getPluginManager().callEvent(new BlockBreakByNDEvent(block)));
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockFromTo(BlockFromToEvent event) {

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockBurn(BlockBurnEvent event) {

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {

    }
}
