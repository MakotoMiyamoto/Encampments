package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.command.AdminToggle;
import com.makotomiyamoto.nt.encampments.core.command.DumpBlockCache;
import com.makotomiyamoto.nt.encampments.core.listener.BlockEventListener;
import com.makotomiyamoto.nt.encampments.core.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Encampments extends JavaPlugin {
    private static Encampments instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Objects.requireNonNull(this.getCommand("dump")).setExecutor(new DumpBlockCache());
        Objects.requireNonNull(this.getCommand("admintoggle")).setExecutor(new AdminToggle());
        // this command is only here for urgent debugging purposes. I'll keep it disabled for now.
        //Objects.requireNonNull(this.getCommand("regen")).setExecutor(new Regen());

        Bukkit.getScheduler().runTaskTimer(this, NTEUtils::restoreBlocks, 20, 20);
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
