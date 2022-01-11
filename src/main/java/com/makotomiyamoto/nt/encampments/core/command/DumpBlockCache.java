package com.makotomiyamoto.nt.encampments.core.command;

import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.core.block.SerializableBlock;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class DumpBlockCache implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Encampments.getInstance().getLogger().log(Level.INFO, "Dumping NTE cache...");

        NTEGlobals.getChunks().forEach((chunk, nteChunk) -> nteChunk.getChangedBlocks().values().forEach(changedBlock -> {
            SerializableBlock block = changedBlock.getSerializableBlock();
            Encampments.getInstance().getLogger().log(Level.INFO,
                    String.format("Block of type(%s) at {x: %d, y: %d, z: %d} at %s",
                            block.getType().toString(),
                            block.getLocation().getBlockX(),
                            block.getLocation().getBlockY(),
                            block.getLocation().getBlockZ(),
                            changedBlock.getDate().toString()
                    ));
        }));

        NTEGlobals.Admin.getBlocksChangedByAdmins().forEach((location, aBoolean) -> Encampments.getInstance().getLogger().log(Level.INFO, String.format(
                "Block broken at {x: %d, y: %d, z: %d} by an admin and will not be restored.",
                location.getBlockX(), location.getBlockY(), location.getBlockZ()
                )));

        sender.sendMessage("Cache dump written to console.");
        return true;
    }
}
