package com.makotomiyamoto.nt.encampments.core.block;

import com.makotomiyamoto.nt.encampments.Encampments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class SerializableSign extends SerializableBlock {
    private final List<Component> lines = new ArrayList<>();

    SerializableSign(BlockEvent blockEvent) {
        super(blockEvent);
        lines.addAll(((Sign)blockEvent.getBlock().getState()).lines());
    }

    SerializableSign(BlockData blockData, Location location, Material material) {
        super(blockData, location, material);
    }

    public List<Component> getLines() {
        return lines;
    }

    @Override
    public void place() {
        Block block = Objects.requireNonNull(Bukkit.getWorld(location.getWorld().getName())).getBlockAt(location);
        block.setType(type);
        block.setBlockData(blockData);
        Sign sign = (Sign) block.getState();

        for (int i = 0; i < lines.size(); ++i) {
            Encampments.getInstance().getLogger().log(Level.WARNING, PlainTextComponentSerializer.plainText().serialize(lines.get(i)));
//            ((Sign)block.getState()).lines().set(i, lines.get(i));
            sign.lines().get(i).append(lines.get(i));
        }
    }
}
