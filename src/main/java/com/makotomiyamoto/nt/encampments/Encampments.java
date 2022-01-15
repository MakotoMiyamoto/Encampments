package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.adapter.BlockDataSerializationAdapter;
import com.makotomiyamoto.nt.encampments.core.adapter.ChunkSerializationAdapter;
import com.makotomiyamoto.nt.encampments.core.adapter.LocationSerializationAdapter;
import com.makotomiyamoto.nt.encampments.core.adapter.SerializableBlockSerializationAdapter;
import com.makotomiyamoto.nt.encampments.core.command.AdminToggle;
import com.makotomiyamoto.nt.encampments.core.command.DumpBlockCache;
import com.makotomiyamoto.nt.encampments.core.command.NaturallyDestroy;
import com.makotomiyamoto.nt.encampments.core.command.Regen;
import com.makotomiyamoto.nt.encampments.core.listener.BlockEventListener;
import com.makotomiyamoto.nt.encampments.core.listener.PlayerInteractListener;
import com.makotomiyamoto.nt.encampments.core.listener.PlayerQuitListener;
import com.makotomiyamoto.nt.encampments.util.GsonManager;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class Encampments extends JavaPlugin {
    private static Encampments instance;
    private static int regenScheduleId = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        GsonManager.registerSerializationHierarchyAdapter(new SerializableBlockSerializationAdapter());
        GsonManager.registerSerializationHierarchyAdapter(new BlockDataSerializationAdapter());
        GsonManager.registerSerializationHierarchyAdapter(new ChunkSerializationAdapter());
        GsonManager.registerSerializationAdapter(new LocationSerializationAdapter());
        GsonManager.reinitializeGson();

        instance = this;
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Objects.requireNonNull(this.getCommand("dump")).setExecutor(new DumpBlockCache());
        Objects.requireNonNull(this.getCommand("admintoggle")).setExecutor(new AdminToggle());
        Objects.requireNonNull(this.getCommand("regen")).setExecutor(new Regen());
        Objects.requireNonNull(this.getCommand("naturallydestroy")).setExecutor(new NaturallyDestroy());

        /*
        Beginning of config.yml parsing
         */
        FileConfiguration config = this.getConfig();

        NTEGlobals.Options.AUTO_REGEN = config.getBoolean("options.regeneration.auto_regen");
        NTEGlobals.Options.REGEN_TIME_SECONDS = config.getInt("options.regeneration.regen_time_seconds");
        /*
        End of config.yml parsing
         */

        if (NTEGlobals.Options.AUTO_REGEN) {
            regenScheduleId = Bukkit.getScheduler().runTaskTimer(this, NTEUtils::restoreBlocks, 20, 20).getTaskId();
            this.getLogger().log(Level.FINE, "Starting auto block regen schedule with ID #" + regenScheduleId);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Encampments getInstance() {
        assert instance != null;
        return instance;
    }

    public static int getRegenScheduleId() {
        return regenScheduleId;
    }
}
