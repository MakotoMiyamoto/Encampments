package com.makotomiyamoto.nt.encampments.util;

import com.makotomiyamoto.nt.encampments.Encampments;

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
     * @param pluginRelativePath the relative path of the file with respect to the plugin folder
     * @param <T> the object type
     */
    public static <T> void saveJson(T object, String pluginRelativePath) throws IOException {
        String json = GsonManager.getGson().toJson(object);
        Path path = Encampments.getInstance().getDataFolder().toPath().resolve(pluginRelativePath);
        File file = path.toFile();
        file.getParentFile().mkdirs();
        if (!file.exists() && file.createNewFile()) {
            Encampments.getInstance().getLogger().log(Level.INFO, String.format("%s created at %s", file.getAbsoluteFile(), new Date()));
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
    }

    public static String readFromFile(String pluginRelativePath) throws IOException {
        File file = Encampments.getInstance().getDataFolder().toPath().resolve(pluginRelativePath).toFile();
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }
}
