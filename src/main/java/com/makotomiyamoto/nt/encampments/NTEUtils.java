package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.block.ChangedBlock;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import com.makotomiyamoto.nt.encampments.core.block.SerializableBlockFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Date;
import java.util.logging.Level;

public abstract class NTEUtils {
    public static void setBlockToRestoreCache(BlockEvent blockEvent) {
        Block block = blockEvent.getBlock();
        if (!NTEGlobals.getBrokenBlocksCache().getCache().containsKey(block.getChunk())) {
            Encampments.getInstance().getLogger().log(Level.INFO, "Chunk " + block.getChunk().getX() + ", " + block.getChunk().getZ() + " not in cache. Adding...");
            NTEGlobals.getBrokenBlocksCache().getCache().put(block.getChunk(), new NTEChunk(block.getChunk()));
        }
        NTEChunk chunk = NTEGlobals.getBrokenBlocksCache().getCache().get(block.getChunk());
        ChangedBlock changedBlock;
        if (blockEvent instanceof BlockPlaceEvent blockPlaceEvent) {
            changedBlock = new ChangedBlock(new Date(), blockPlaceEvent.getBlockReplacedState(), blockPlaceEvent);
        } else {
            changedBlock = new ChangedBlock(new Date(), blockEvent.getBlock().getState(), blockEvent);
        }
        chunk.getChangedBlocks().put(changedBlock.getSerializableBlock().getLocation(), changedBlock);
    }

    public static void setBlockToPlacedCache(BlockPlaceEvent blockPlaceEvent) {
        Block block = blockPlaceEvent.getBlockPlaced();
        if (!NTEGlobals.getPlacedBlocksCache().getCache().containsKey(block.getChunk())) {
            Encampments.getInstance().getLogger().log(Level.INFO, "Chunk " + block.getChunk().getX() + ", " + block.getChunk().getZ() + " not in placed cache. Adding...");
            NTEGlobals.getPlacedBlocksCache().getCache().put(block.getChunk(), new NTEChunk(block.getChunk()));
        }
        NTEChunk chunk = NTEGlobals.getPlacedBlocksCache().getCache().get(block.getChunk());
        var changedBlock = new ChangedBlock(new Date(), blockPlaceEvent.getBlock().getState(), blockPlaceEvent);
        chunk.getChangedBlocks().put(changedBlock.getSerializableBlock().getLocation(), changedBlock);
    }

    public static void restoreBlocks() {
        int iterations = 1;
        long duration = NTEGlobals.Options.REGEN_TIME_SECONDS * 1000;
        for (var set : NTEGlobals.getBrokenBlocksCache().getCache().entrySet()) {
            NTEGlobals.getRecentlyReplacedChunks().put(set.getKey(), new NTEChunk(set.getKey()));
            var replaceChunk = NTEGlobals.getRecentlyReplacedChunks().get(set.getKey());
            int finalIterations = iterations;
            Bukkit.getScheduler().runTaskLater(Encampments.getInstance(), () -> {
                for (var it = set.getValue().getChangedBlocks().values().iterator(); it.hasNext();) {
                    var changedBlock = it.next();
                    if (changedBlock.getDate().getTime() + duration < System.currentTimeMillis()) {
                        var serializableBlock = changedBlock.getSerializableBlock();
                        replaceChunk.getChangedBlocks().put(serializableBlock.getLocation(), changedBlock);
                        serializableBlock.place();
                        it.remove();
                    }
                }
                Bukkit.getScheduler().runTaskLater(Encampments.getInstance(), () -> {
                    NTEGlobals.getRecentlyReplacedChunks().remove(set.getKey());
                }, 20L * finalIterations + 20);
            }, 20L * iterations);
            ++iterations;
        }
    }

    // these mechanics will change in the future, which is why they're
    // in a separate function in the first place.
    public static void decayBlocks() {
        int iterations = 1;
        long duration = NTEGlobals.Options.REGEN_TIME_SECONDS * 1000;
        for (var set : NTEGlobals.getPlacedBlocksCache().getCache().entrySet()) {
            Bukkit.getScheduler().runTaskLater(Encampments.getInstance(), () -> {
                for (var it = set.getValue().getChangedBlocks().values().iterator(); it.hasNext();) {
                    var changedBlock = it.next();
                    if (changedBlock.getDate().getTime() + duration < System.currentTimeMillis()) {
                        var serializableBlock = changedBlock.getSerializableBlock();
                        serializableBlock.getLocation().getBlock().setType(Material.AIR, true);
                        it.remove();
                    }
                }
            }, 20L * iterations);
            ++iterations;
        }
    }
}
