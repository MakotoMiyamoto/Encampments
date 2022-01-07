package com.makotomiyamoto.nt.encampments.core;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.io.Serializable;
import java.util.ArrayList;

// I don't know what to name this class yet.
public class NTEChunk {
    private final Chunk chunk;
    private final ArrayList<ChangedBlock> changedBlocks = new ArrayList<>();

    public NTEChunk(Chunk chunk) {
        this.chunk = chunk;

    }

    public Chunk getChunk() {
        return chunk;
    }

    public ArrayList<ChangedBlock> getChangedBlocks() {
        return changedBlocks;
    }
}
