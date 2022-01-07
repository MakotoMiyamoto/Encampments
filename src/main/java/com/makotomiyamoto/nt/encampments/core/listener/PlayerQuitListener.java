package com.makotomiyamoto.nt.encampments.core.listener;

import com.makotomiyamoto.nt.encampments.NTEGlobals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        NTEGlobals.getAdminPlayers().removeIf(offlinePlayer -> event.getPlayer().getUniqueId().equals(offlinePlayer.getUniqueId()));
    }
}
