package com.makotomiyamoto.nt.encampments.core.block;

import org.bukkit.Chunk;

import java.util.HashMap;

public class BlockStateCache {
    private final HashMap<Chunk, NTEChunk> cache = new HashMap<>();

    public HashMap<Chunk, NTEChunk> getCache() {
        return cache;
    }
}
