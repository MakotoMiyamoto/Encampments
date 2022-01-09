package com.makotomiyamoto.nt.encampments.core.block;

import com.makotomiyamoto.nt.encampments.Encampments;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;

import java.util.Objects;
import java.util.logging.Level;

public class SerializableDoor extends SerializableBlock {
    SerializableDoor(BlockEvent blockEvent) {
        super(blockEvent);
        if (blockEvent instanceof BlockBreakEvent && ((Door)blockEvent.getBlock().getBlockData()).getHalf().equals(Bisected.Half.TOP)) {
            ((Door)getBlockData()).setHalf(Bisected.Half.BOTTOM);
            getLocation().subtract(0, 1, 0);
        }
        //Encampments.getInstance().getLogger().log(Level.SEVERE, ((Door)getBlockData()).getHalf() + ", " + getLocation().getY());
    }

    /**
     * Patch to glitch where doors break on a race condition with game physics.
     */
    @Override
    public void place() {
        Block block = Objects.requireNonNull(Bukkit.getWorld(location.getWorld().getName())).getBlockAt(location);
        block.setType(type, false);
        block.setBlockData(blockData);
    }
}
