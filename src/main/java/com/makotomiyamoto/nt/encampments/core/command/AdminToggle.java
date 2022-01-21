package com.makotomiyamoto.nt.encampments.core.command;

import com.makotomiyamoto.nt.encampments.NTEGlobals;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class AdminToggle implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (NTEGlobals.isPlayerInAdminMode(player)) {
                NTEGlobals.removePlayerAdminMode(player);
                sender.sendMessage(ChatColor.YELLOW + "You are no longer in admin mode.");
            } else {
                NTEGlobals.setPlayerAdminMode(player);
                sender.sendMessage(ChatColor.GREEN + "You are now in admin mode.");
            }

//            ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
//            BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
//            bookMeta.setAuthor("Nebula Toxin");
//            bookMeta.setTitle("Quest Book");
//            bookMeta.addPages(Component.text("Text").color(TextColor.color(0xFFAA00)).hoverEvent(HoverEvent.showText(Component.text("hover text"))));
//            itemStack.setItemMeta(bookMeta);
//            player.getInventory().addItem(itemStack);
        } else {
            sender.sendMessage("Only players can execute this command.");
        }

        return true;
    }
}
