package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.block.ChangedBlock;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockEvent;

import java.util.Date;
import java.util.logging.Level;

public abstract class NTEUtils {
    public static void setBlockToRestoreCache(BlockEvent blockEvent) {
        Block block = blockEvent.getBlock();
        if (!NTEGlobals.getChunks().containsKey(block.getChunk())) {
            Encampments.getInstance().getLogger().log(Level.INFO, "Chunk " + block.getChunk().getX() + ", " + block.getChunk().getZ() + " not in cache. Adding...");
            NTEGlobals.getChunks().put(block.getChunk(), new NTEChunk(block.getChunk()));
        }
        NTEChunk chunk = NTEGlobals.getChunks().get(block.getChunk());
        chunk.getChangedBlocks().add(new ChangedBlock(new Date(), blockEvent));
    }

    public static void restoreBlocks() {
        for (var set : NTEGlobals.getChunks().entrySet()) {
            for (var it = set.getValue().getChangedBlocks().iterator(); it.hasNext();) {
                var changedBlock = it.next();
                if (changedBlock.getDate().getTime() + (NTEGlobals.BLOCK_CHANGED_DURATION_SECONDS * 1000) < System.currentTimeMillis()) {
                    var serializableBlock = changedBlock.getSerializableBlock();
                    serializableBlock.place();
                    it.remove();
                }
            }
        }
    }
}
