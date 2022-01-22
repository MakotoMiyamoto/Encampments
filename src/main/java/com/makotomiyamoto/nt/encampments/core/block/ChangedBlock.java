package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class ChangedBlock {
    private final Date date;
    private SerializableBlock serializableBlock;

//    @Deprecated
//    public ChangedBlock(Date time, BlockEvent blockEvent) {
//        this.date = (Date) time.clone();
//        if (blockEvent.getBlock().getBlockData() instanceof Bisected && ! (blockEvent.getBlock().getBlockData() instanceof TrapDoor)) {
//            this.serializableBlock = new SerializableBisected(blockEvent);
//        }
//        else if (blockEvent.getBlock().getState() instanceof Sign) {
//            this.serializableBlock = new SerializableSign(blockEvent);
//        }
//        else {
//            this.serializableBlock = new SerializableBlock(blockEvent);
//        }
//    }

    public ChangedBlock(Date time, BlockState blockState, @NotNull BlockEvent blockEvent) {
        this.date = (Date) time.clone();
        if (blockState.getBlockData() instanceof Bisected && ! (blockState.getBlockData() instanceof TrapDoor)) {
            this.serializableBlock = new SerializableBisected(blockState, blockEvent);
        }
        else if (blockEvent.getBlock().getState() instanceof Sign) {
            this.serializableBlock = new SerializableSign(blockState, blockEvent);
        }
        else {
            this.serializableBlock = new SerializableBlock(blockState, blockEvent);
        }
    }

    public Date getDate() {
        return date;
    }

    public SerializableBlock getSerializableBlock() {
        return serializableBlock;
    }

    public void setSerializableBlock(SerializableBlock serializableBlock) {
        this.serializableBlock = serializableBlock;
    }
}
