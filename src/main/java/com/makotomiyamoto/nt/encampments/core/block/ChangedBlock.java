package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.block.BlockEvent;

import java.util.Date;

public class ChangedBlock {
    private final Date date;
    private final SerializableBlock serializableBlock;

    public ChangedBlock(Date time, BlockEvent blockEvent) {
        this.date = (Date) time.clone();
        if (blockEvent.getBlock().getBlockData() instanceof Door) {
            this.serializableBlock = new SerializableDoor(blockEvent);
        }
        else if (blockEvent.getBlock().getState() instanceof Sign) {
            this.serializableBlock = new SerializableSign(blockEvent);
        }
        else {
            this.serializableBlock = new SerializableBlock(blockEvent);
        }
    }

    public Date getDate() {
        return date;
    }

    public SerializableBlock getSerializableBlock() {
        return serializableBlock;
    }
}
