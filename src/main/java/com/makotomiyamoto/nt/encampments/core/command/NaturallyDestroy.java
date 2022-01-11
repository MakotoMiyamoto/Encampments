package com.makotomiyamoto.nt.encampments.core.command;

import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.core.desht.Cuboid;
import com.makotomiyamoto.nt.encampments.core.event.BlockBreakByNDEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NaturallyDestroy implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Location pos1 = NTEGlobals.Admin.getPos1(), pos2 = NTEGlobals.Admin.getPos2();

        Cuboid cuboid = new Cuboid(pos1, pos2);
        for (Block block : cuboid) {
            Bukkit.getPluginManager().callEvent(new BlockBreakByNDEvent(block));
            block.setType(Material.AIR);
        }

        return true;
    }
}
