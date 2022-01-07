package com.makotomiyamoto.nt.encampments.core.command;

import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.core.SerializableBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class DumpBlockCache implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Encampments.getInstance().getLogger().log(Level.INFO, "Dumping NTE cache...");
        NTEGlobals.getChunks().forEach((chunk, nteChunk) -> nteChunk.getChangedBlocks().forEach(changedBlock -> {
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

        sender.sendMessage("Cache dump written to console.");

        return true;
    }
}
