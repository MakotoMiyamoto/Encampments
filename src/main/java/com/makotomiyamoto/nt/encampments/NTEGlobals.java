package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.block.ChangedBlock;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NTEGlobals {
    private static final List<OfflinePlayer> adminPlayers = new ArrayList<>();
    private static final HashMap<Chunk, NTEChunk> chunks = new HashMap<>();
    //private static final HashMap<>
    public static long BLOCK_CHANGED_DURATION_SECONDS = 3L;
//    public static class Debug {
//        private static final ArrayList<ChangedBlock> archivedChangedBlocks = new ArrayList<>();
//
//        public static ArrayList<ChangedBlock> getArchivedChangedBlocks() {
//            return archivedChangedBlocks;
//        }
//    }
    public static class Admin {
        private static final HashMap<Location, Boolean> blocksChangedByAdmins = new HashMap<>();

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
    }

    public static List<OfflinePlayer> getAdminPlayers() {
        return adminPlayers;
    }

    public static HashMap<Chunk, NTEChunk> getChunks() {
        return chunks;
    }
}
