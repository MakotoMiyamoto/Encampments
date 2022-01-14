package com.makotomiyamoto.nt.encampments.core.adapter;

import com.google.gson.*;
import com.makotomiyamoto.nt.encampments.util.JsonSerializationAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.UUID;

public class ChunkSerializationAdapter extends JsonSerializationAdapter<Chunk> {
    @Override
    public JsonElement serialize(Chunk src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject chunkJsonObject = new JsonObject();

        chunkJsonObject.addProperty("x", src.getX());
        chunkJsonObject.addProperty("z", src.getZ());
        chunkJsonObject.addProperty("world", src.getWorld().getUID().toString());

        return chunkJsonObject;
    }

    @Override
    public Chunk deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject chunkJsonObject = (JsonObject) json;

        int x = chunkJsonObject.get("x").getAsInt();
        int z = chunkJsonObject.get("z").getAsInt();
        UUID world = UUID.fromString(chunkJsonObject.get("world").getAsString());

        return Objects.requireNonNull(Bukkit.getWorld(world)).getChunkAt(x, z);
    }
}
