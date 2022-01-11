package com.makotomiyamoto.nt.encampments.core.block;

import com.makotomiyamoto.nt.encampments.Encampments;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;

import java.util.Objects;
import java.util.logging.Level;

public class SerializableBisected extends SerializableBlock {
    SerializableBisected(BlockEvent blockEvent) {
        super(blockEvent);
        if (blockEvent instanceof BlockBreakEvent && ((Bisected)blockEvent.getBlock().getBlockData()).getHalf().equals(Bisected.Half.TOP)) {
            ((Bisected)getBlockData()).setHalf(Bisected.Half.BOTTOM);
            getLocation().subtract(0, 1, 0);
        }
    }

    /**
     * Patch to glitch where doors break on a race condition with game physics.
     */
    @Override
    public void place() {
        Block block = Objects.requireNonNull(Bukkit.getWorld(location.getWorld().getName())).getBlockAt(location);
        block.setType(type, false);
        block.setBlockData(blockData, false);
    }
}
