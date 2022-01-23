package com.makotomiyamoto.nt.encampments.core.block;

import com.makotomiyamoto.nt.encampments.Encampments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class SerializableSign extends SerializableBlock {
    private final List<Component> lines = new ArrayList<>();

    SerializableSign(BlockState blockState, BlockEvent blockEvent) {
        super(blockState, blockEvent);
        Sign sign = (Sign) blockState;
        lines.addAll(sign.lines());
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

        for (int i = 0; i < this.lines.size(); ++i) {
            Encampments.getInstance().getLogger().log(Level.WARNING, this.lines.get(i).toString());
            sign.line(i, this.lines.get(i));
        }
        sign.update();
    }
}
