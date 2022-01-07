package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.SerializableBlock;
import com.makotomiyamoto.nt.encampments.core.command.DumpBlockCache;
import com.makotomiyamoto.nt.encampments.core.listener.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Encampments extends JavaPlugin {
    private static Encampments instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        Objects.requireNonNull(this.getCommand("dump")).setExecutor(new DumpBlockCache());

        Bukkit.getScheduler().runTaskTimer(this, () -> NTEGlobals.getChunks().entrySet().removeIf(chunkNTEChunkEntry -> {
           chunkNTEChunkEntry.getValue().getChangedBlocks().removeIf(changedBlock -> {
               if (changedBlock.getDate().getTime() + (NTEGlobals.BLOCK_CHANGED_DURATION_SECONDS * 1000) < System.currentTimeMillis()) {
                   SerializableBlock block = changedBlock.getSerializableBlock();
                   Block worldBlock = Objects.requireNonNull(Bukkit.getWorld(NTEGlobals.WORLD_NAME)).getBlockAt(block.getLocation());
                   worldBlock.setType(block.getType());
                   worldBlock.setBlockData(block.getBlockData());
                   return true;
               }
               return false;
           });

            return false;
        }), 20, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Encampments getInstance() {
        assert instance != null;
        return instance;
    }
}
