package com.makotomiyamoto.nt.encampments.util;

import com.makotomiyamoto.nt.encampments.Encampments;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;

public abstract class NTFileUtils {
    /**
     * Serializes an object to a JSON string and writes the data to a file.
     * @param object the object to serialize
     * @param plugin the Java plugin this path belongs to
     * @param pluginRelativePath the relative path of the file with respect to the plugin folder
     * @param <T> the object type
     */
    public static <T> void saveJson(T object, JavaPlugin plugin, String pluginRelativePath) throws IOException {
        String json = GsonManager.getGson().toJson(object);
        Path path = plugin.getDataFolder().toPath().resolve(pluginRelativePath);
        File file = path.toFile();
        file.getParentFile().mkdirs();
        if (!file.exists() && file.createNewFile()) {
            Encampments.getInstance().getLogger().log(Level.INFO, String.format("%s created at %s", file.getAbsoluteFile(), new Date()));
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
    }

    /**
     * Parses the contents of a file as a single string. This is used internally for reading and directly deserializing json data.
     * @param plugin the {@link JavaPlugin} the relative path belongs to
     * @param pluginRelativePath a path relative to a plugin's daa folder
     * @return the contents of a file parsed as a string.
     * @throws IOException upon any instance where {@link Files#readString(Path)} would throw an IOException.
     */
    public static String readFromFile(JavaPlugin plugin, String pluginRelativePath) throws IOException {
        File file = plugin.getDataFolder().toPath().resolve(pluginRelativePath).toFile();
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Checks whether a cache subfolder exists within the data folder of a {@link JavaPlugin}.
     * @param plugin the Java plugin the queried cache folder belongs to
     * @return true if the folder exists
     */
    public static boolean cacheFolderExists(JavaPlugin plugin) {
        return plugin.getDataFolder().toPath().resolve("cache").toFile().exists();
    }
}
