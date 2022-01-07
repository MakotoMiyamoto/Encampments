package com.makotomiyamoto.nt.encampments.core.listener;

import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.core.ChangedBlock;
import com.makotomiyamoto.nt.encampments.core.NTEChunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Date;
import java.util.logging.Level;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // it is important to distinguish admin players from OPs that way
        // OPs can both unit test features and build on the world without
        // losing progress.
        if (NTEGlobals.getAdminPlayers().contains(event.getPlayer())) {
            return; // admin mode overrides block restoration functionality
        }

        if (!NTEGlobals.getChunks().containsKey(event.getBlock().getChunk())) {
            Encampments.getInstance().getLogger().log(Level.INFO, "Chunk " + event.getBlock().getChunk().getX() + ", " + event.getBlock().getChunk().getZ() + " not in cache. Adding...");
            NTEGlobals.getChunks().put(event.getBlock().getChunk(), new NTEChunk(event.getBlock().getChunk()));
        }
        NTEChunk chunk = NTEGlobals.getChunks().get(event.getBlock().getChunk());
        chunk.getChangedBlocks().add(new ChangedBlock(new Date(), event.getBlock()));
    }
}
