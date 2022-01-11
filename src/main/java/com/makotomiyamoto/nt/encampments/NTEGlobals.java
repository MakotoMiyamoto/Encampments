package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NTEGlobals {
    private static final List<Player> adminPlayers = new ArrayList<>();
    private static final HashMap<Chunk, NTEChunk> chunks = new HashMap<>();
    //private static final HashMap<>
    public static class Options {
        public static boolean AUTO_REGEN = true;
        public static long REGEN_TIME_SECONDS = 3L;
    }
//    public static class Debug {
//        private static final ArrayList<ChangedBlock> archivedChangedBlocks = new ArrayList<>();
//
//        public static ArrayList<ChangedBlock> getArchivedChangedBlocks() {
//            return archivedChangedBlocks;
//        }
//    }
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

    public static List<Player> getAdminPlayers() {
        return adminPlayers;
    }

    public static HashMap<Chunk, NTEChunk> getChunks() {
        return chunks;
    }

    public static boolean playerIsAdminMode(Player player) {
        return getAdminPlayers().contains(player);
    }
}
