package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashMap;

// I don't know what to name this class yet.
public class NTEChunk {
    private final Chunk chunk;
    private final HashMap<Location, ChangedBlock> changedBlocks = new HashMap<>();

    public NTEChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public HashMap<Location, ChangedBlock> getChangedBlocks() {
        return changedBlocks;
    }
}
