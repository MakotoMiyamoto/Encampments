package com.makotomiyamoto.nt.encampments.core.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.NTEUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.logging.Level;

public class BlockEventListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (NTEGlobals.getAdminPlayers().contains(event.getPlayer())) {
            var location = event.getBlock().getLocation();
            if (event.getBlock().getBlockData() instanceof Door door && door.getHalf().equals(Bisected.Half.TOP)) {
                location.subtract(0, 1, 0);
            }
            NTEGlobals.Admin.tempSaveAdminBlockToCache(location);
            return;
        }
        // THE SIGN ISNT A SIGN
        if (event.getBlock().getState() instanceof Sign sign) {
            sign.lines().forEach(component -> Encampments.getInstance().getLogger().log(Level.WARNING, PlainTextComponentSerializer.plainText().serialize(component)));
        }
        NTEUtils.setBlockToRestoreCache(event);
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent event) {
        for (BlockFace blockFace : BlockFace.values()) {
            Block relative = event.getBlock().getRelative(blockFace);
            Boolean changedByAdmin = NTEGlobals.Admin.getBlocksChangedByAdmins().get(relative.getLocation());
            if (changedByAdmin != null && changedByAdmin) {
                NTEGlobals.Admin.tempSaveAdminBlockToCache(event.getBlock().getLocation());
                return;
            }
        }
        NTEUtils.setBlockToRestoreCache(event);
    }
}
