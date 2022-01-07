package com.makotomiyamoto.nt.encampments.core;

import org.bukkit.block.Block;

import java.util.Date;

public class ChangedBlock {
    private final Date date;
    private final SerializableBlock serializableBlock;

    public ChangedBlock(Date time, Block block) {
        this.date = (Date) time.clone();
        this.serializableBlock = new SerializableBlock(block);
    }

    public Date getDate() {
        return date;
    }

    public SerializableBlock getSerializableBlock() {
        return serializableBlock;
    }
}
