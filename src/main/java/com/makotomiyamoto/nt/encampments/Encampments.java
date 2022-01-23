package com.makotomiyamoto.nt.encampments;

import com.makotomiyamoto.nt.encampments.core.adapter.*;
import com.makotomiyamoto.nt.encampments.core.block.BlockStateCache;
import com.makotomiyamoto.nt.encampments.core.command.AdminToggle;
import com.makotomiyamoto.nt.encampments.core.command.DumpBlockCache;
import com.makotomiyamoto.nt.encampments.core.command.NaturallyDestroy;
import com.makotomiyamoto.nt.encampments.core.command.Regen;
import com.makotomiyamoto.nt.encampments.core.listener.*;
import com.makotomiyamoto.nt.encampments.util.GsonManager;
import com.makotomiyamoto.nt.encampments.util.NTFileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public final class Encampments extends JavaPlugin {
    private static Encampments instance;
    private static int regenScheduleId = 0;

    private static final HashMap<Material, ItemStack> customDrops = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.saveDefaultConfig();

        GsonManager.registerSerializationHierarchyAdapter(new SerializableBlockSerializationAdapter());
        GsonManager.registerSerializationHierarchyAdapter(new BlockDataSerializationAdapter());
        GsonManager.registerSerializationHierarchyAdapter(new ChunkSerializationAdapter());
        GsonManager.registerSerializationAdapter(new BlockStateCacheSerializationAdapter());
        GsonManager.registerSerializationAdapter(new NTEChunkSerializationAdapter());
        GsonManager.registerSerializationAdapter(new LocationSerializationAdapter());
        GsonManager.registerSerializationAdapter(new DateSerializationAdapter());
        GsonManager.reinitializeGson();

        if (NTFileUtils.cacheFolderExists(this)) {
            try {
                // rhs makes this difficult to read
                NTEGlobals.setBrokenBlocksCache(GsonManager.getGson().fromJson(NTFileUtils.readFromFile(this, NTEGlobals.Paths.BROKEN_BLOCKS_CACHE), BlockStateCache.class));
                NTEGlobals.setPlacedBlocksCache(GsonManager.getGson().fromJson(NTFileUtils.readFromFile(this, NTEGlobals.Paths.PLACED_BLOCKS_CACHE), BlockStateCache.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getPluginManager().registerEvents(new WorldSaveListener(), this);
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


        NamespacedKey key = new NamespacedKey(this, "campfire_charcoal");
        CampfireRecipe campfireCharcoalRecipe = new CampfireRecipe(key, new ItemStack(Material.CHARCOAL, 1), Material.OAK_LOG, 1.f, 100);
        Bukkit.addRecipe(campfireCharcoalRecipe);

        customDrops.put(Material.AZALEA, new ItemStack(Material.STICK));
        customDrops.put(Material.GRASS, new ItemStack(Material.STRING));
        customDrops.put(Material.STONE_BUTTON, new ItemStack(Material.FLINT));

        NamespacedKey campfireAltKey = new NamespacedKey(this, "campfire_alt");
        ShapedRecipe campfireAltRecipe = new ShapedRecipe(campfireAltKey, new ItemStack(Material.CAMPFIRE));
        campfireAltRecipe.shape("SS", "SS").setIngredient('S', Material.STICK);
        Bukkit.addRecipe(campfireAltRecipe);
        this.getServer().getPluginManager().registerEvents(new AlphaBlockPlaceListener(), this);

        NamespacedKey cobblestoneAltKey = new NamespacedKey(this, "cobblestone_alt");
        ShapedRecipe cobblestoneAltRecipe = new ShapedRecipe(cobblestoneAltKey, new ItemStack(Material.COBBLESTONE));
        cobblestoneAltRecipe.shape("FF", "FF").setIngredient('F', Material.FLINT);
        Bukkit.addRecipe(cobblestoneAltRecipe);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            NTFileUtils.saveJson(NTEGlobals.getBrokenBlocksCache(), Encampments.getInstance(), NTEGlobals.Paths.BROKEN_BLOCKS_CACHE);
            NTFileUtils.saveJson(NTEGlobals.getPlacedBlocksCache(), Encampments.getInstance(), NTEGlobals.Paths.PLACED_BLOCKS_CACHE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Encampments getInstance() {
        assert instance != null;
        return instance;
    }

    public static int getRegenScheduleId() {
        return regenScheduleId;
    }

    public static HashMap<Material, ItemStack> getCustomDrops() {
        return customDrops;
    }
}
