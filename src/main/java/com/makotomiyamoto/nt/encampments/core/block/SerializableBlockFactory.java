package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public abstract class SerializableBlockFactory {
    public static SerializableBlock createSerializableBlock(BlockData blockData, Location location, Material type) {
        return new SerializableBlock(blockData, location, type);
    }

    public static SerializableBisected createSerializableBisected(BlockData blockData, Location location, Material type) {
        return new SerializableBisected(blockData, location, type);
    }

    public static SerializableSign createSerializableSign(BlockData blockData, Location location, Material type) {
        return new SerializableSign(blockData, location, type);
    }
}
