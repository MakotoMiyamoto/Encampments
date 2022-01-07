package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.NTEChunk;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NTEGlobals {
    private static final List<OfflinePlayer> adminPlayers = new ArrayList<>();
    private static final HashMap<Chunk, NTEChunk> chunks = new HashMap<>();
    public static long BLOCK_CHANGED_DURATION_SECONDS = 3L;
    public static String WORLD_NAME = "world";

    public static List<OfflinePlayer> getAdminPlayers() {
        return adminPlayers;
    }

    public static HashMap<Chunk, NTEChunk> getChunks() {
        return chunks;
    }
}
