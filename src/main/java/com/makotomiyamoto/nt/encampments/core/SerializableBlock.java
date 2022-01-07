package com.makotomiyamoto.nt.encampments.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class SerializableBlock {
    private final BlockData blockData;
    private final Location location;
    private final Material type;

    SerializableBlock(Block block) {
        this.blockData = block.getBlockData().clone();
        this.location = block.getLocation().clone();
        this.type = block.getType();
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public Location getLocation() {
        return location;
    }

    public Material getType() {
        return type;
    }
}
