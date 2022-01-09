package com.makotomiyamoto.nt.encampments.core.command;

import com.makotomiyamoto.nt.encampments.NTEGlobals;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminToggle implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            OfflinePlayer player = (OfflinePlayer) sender;
            if (NTEGlobals.getAdminPlayers().contains(player)) {
                NTEGlobals.getAdminPlayers().remove(player);
                sender.sendMessage(ChatColor.YELLOW + "You are no longer in admin mode.");
            } else {
                NTEGlobals.getAdminPlayers().add(player);
                sender.sendMessage(ChatColor.GREEN + "You are now in admin mode.");
            }
        } else {
            sender.sendMessage("Only players can execute this command.");
        }

        return true;
    }
}
