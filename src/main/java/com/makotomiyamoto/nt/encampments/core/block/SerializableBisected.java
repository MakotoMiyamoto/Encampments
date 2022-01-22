package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SerializableBisected extends SerializableBlock {
//    @Deprecated
//    SerializableBisected(BlockEvent blockEvent) {
//        super(blockEvent);
//        if (blockEvent instanceof BlockBreakEvent && ((Bisected)blockEvent.getBlock().getBlockData()).getHalf().equals(Bisected.Half.TOP)) {
//            ((Bisected)getBlockData()).setHalf(Bisected.Half.BOTTOM);
//            getLocation().subtract(0, 1, 0);
//        }
//    }

    SerializableBisected(BlockState blockState, @NotNull BlockEvent blockEvent) {
        super(blockState, blockEvent);
        if (blockEvent instanceof BlockBreakEvent && ((Bisected) blockEvent.getBlock().getBlockData()).getHalf().equals(Bisected.Half.TOP)) {
            ((Bisected)getBlockData()).setHalf(Bisected.Half.BOTTOM);
            getLocation().subtract(0, 1, 0);
        }
    }

    SerializableBisected(BlockData blockData, Location location, Material material) {
        super(blockData, location, material);
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
