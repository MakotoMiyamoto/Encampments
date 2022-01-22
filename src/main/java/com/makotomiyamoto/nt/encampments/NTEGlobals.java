package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.block.BlockStateCache;
import com.makotomiyamoto.nt.encampments.core.block.ChangedBlock;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class NTEGlobals {
    private static final HashMap<Chunk, NTEChunk> recentlyReplacedChunks = new HashMap<>(); // do not refactor, this is simply a state mechanism

    private static final HashSet<Chunk> claimedChunks = new HashSet<>();
    private static final HashSet<Player> playersInAdminMode = new HashSet<>();
    private static BlockStateCache brokenBlocksCache = new BlockStateCache();
    private static BlockStateCache placedBlocksCache = new BlockStateCache();

    public static class Admin {
        private static final HashMap<Location, Boolean> blocksChangedByAdmins = new HashMap<>();
        private static Location pos1 = new Location(null, 0, 0, 0), pos2 = new Location(null, 0, 0, 0);

        public static void removeLater(Location location) {
            Bukkit.getScheduler().runTaskLater(Encampments.getInstance(), () -> getBlocksChangedByAdmins().remove(location), 20);
        }

        public static void tempSaveAdminBlockToCache(Location location) {
            blocksChangedByAdmins.put(location, true);
            removeLater(location);
        }

        public static HashMap<Location, Boolean> getBlocksChangedByAdmins() {
            return blocksChangedByAdmins;
        }

        public static Location getPos1() {
            return pos1;
        }

        public static Location getPos2() {
            return pos2;
        }

        public static void setPos1(Location pos1) {
            Admin.pos1 = pos1;
        }

        public static void setPos2(Location pos2) {
            Admin.pos2 = pos2;
        }
    }

    public static class Options {
        public static boolean AUTO_REGEN = true;
        public static long REGEN_TIME_SECONDS = 3L;
    }

    public static class Paths {
        public static final String BROKEN_BLOCKS_CACHE = "cache/broken_blocks.json";
        public static final String PLACED_BLOCKS_CACHE = "cache/placed_blocks.json";
    }

    public static HashSet<Chunk> getClaimedChunks() {
        return claimedChunks;
    }

    public static HashSet<Player> getPlayersInAdminMode() {
        return playersInAdminMode;
    }

    public static void setPlayerAdminMode(Player player) {
        getPlayersInAdminMode().add(player);
    }

    public static void removePlayerAdminMode(Player player) {
        getPlayersInAdminMode().remove(player);
    }

    public static BlockStateCache getBrokenBlocksCache() {
        return brokenBlocksCache;
    }

    public static BlockStateCache getPlacedBlocksCache() {
        return placedBlocksCache;
    }

    /**
     * Sets the broken blocks cache.
     * <p>WARNING: This is an unsafe method and is only meant for internal use.</p>
     * @param blockStateCache the cache
     */
    public static void setBrokenBlocksCache(BlockStateCache blockStateCache) {
        NTEGlobals.brokenBlocksCache = blockStateCache;
    }

    /**
     * Sets the placed blocks cache.
     * <p>WARNING: This is an unsafe method and is only meant for internal use.</p>
     * @param placedBlocksCache the cache
     */
    public static void setPlacedBlocksCache(BlockStateCache placedBlocksCache) {
        NTEGlobals.placedBlocksCache = placedBlocksCache;
    }

    public static HashMap<Chunk, NTEChunk> getRecentlyReplacedChunks() {
        return recentlyReplacedChunks;
    }

    public static boolean isPlayerInAdminMode(Player player) {
        return getPlayersInAdminMode().contains(player);
    }

    /**
     * Checks if a chunk is claimed (or padding a claim) so that it is
     * ignored in block events.
     * @param chunk A chunk object representing a Minecraft chunk
     * @return true if the chunk is padding a claim(s)
     */
    public static boolean isChunkClaimed(@NotNull Chunk chunk) {
        return getClaimedChunks().contains(chunk);
    }

    /**
     * Checks if a block is cached in any state. This method was written to
     * avoid cache overriding (e.g., if you place a block and destroy it, the
     * algorithm will only remember the initial placement and not the destruction
     * thereof).
     * @param block an object representing a block in Minecraft
     * @return true if the block is cached
     */
    public static boolean isBlockCached(@NotNull Block block) {
        // redundant if-else for clarity
        boolean cached = false;

        var chunk = getBrokenBlocksCache().getCache().get(block.getChunk());
        if (chunk != null && chunk.getChangedBlocks().containsKey(block.getLocation())) {
            cached = true;
        }
        chunk = getPlacedBlocksCache().getCache().get(block.getChunk());
        if (chunk != null && chunk.getChangedBlocks().containsKey(block.getLocation())) {
            cached = true;
        }

        return cached;
    }
}
