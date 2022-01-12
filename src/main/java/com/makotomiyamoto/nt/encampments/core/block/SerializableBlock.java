package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

/**
 * Represents the data of a cached block that can safely be serialized,
 * unlike Bukkit's native block system. Serializable blocks should not
 * have constructors that are visible to classes outside
 * com.makotomiyamoto.nt.encampments.core.block. NTEUtils should take
 * care of the underlying type, not the end user.
 */
public class SerializableBlock {
    protected final BlockData blockData;
    protected final Location location;
    protected Material type;

    SerializableBlock(BlockEvent blockEvent) {
        this(blockEvent.getBlock());
    }

    private SerializableBlock(Block block) {
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

    /**
     * Default block restoration behaviour. Should be overrides
     * by blocks whose placement behaviour is unique, like doors.
     * God, I hate doors. I don't know what this possibly throws yet.
     * I guess we'll find out soon enough.
     */
    public void place() {
        Block block = Objects.requireNonNull(Bukkit.getWorld(location.getWorld().getName())).getBlockAt(location);
        block.setType(type);
        block.setBlockData(blockData);
    }
}
