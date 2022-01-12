package com.makotomiyamoto.nt.encampments.core.command;

import com.makotomiyamoto.nt.encampments.NTEUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Regen implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        long start = System.currentTimeMillis();
        NTEUtils.restoreBlocks();
        NTEUtils.decayBlocks();
        long end = System.currentTimeMillis();
        sender.sendMessage(String.format("Blocks regenerated. (%dms)", end - start));
        return true;
    }
}
