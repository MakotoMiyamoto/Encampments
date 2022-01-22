package com.makotomiyamoto.nt.encampments.core.listener;

import com.makotomiyamoto.nt.encampments.Encampments;
import com.makotomiyamoto.nt.encampments.NTEGlobals;
import com.makotomiyamoto.nt.encampments.util.NTFileUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.IOException;

public class WorldSaveListener implements Listener {
    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        try {
            NTFileUtils.saveJson(NTEGlobals.getBrokenBlocksCache(), Encampments.getInstance(), NTEGlobals.Paths.BROKEN_BLOCKS_CACHE);
            NTFileUtils.saveJson(NTEGlobals.getPlacedBlocksCache(), Encampments.getInstance(), NTEGlobals.Paths.PLACED_BLOCKS_CACHE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
