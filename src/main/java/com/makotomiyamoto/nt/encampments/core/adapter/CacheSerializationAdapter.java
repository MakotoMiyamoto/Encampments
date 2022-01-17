package com.makotomiyamoto.nt.encampments.core.adapter;

import com.google.gson.*;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import com.makotomiyamoto.nt.encampments.util.JsonSerializationAdapter;
import org.bukkit.Chunk;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CacheSerializationAdapter extends JsonSerializationAdapter<HashMap<Chunk, NTEChunk>> {
    @Override
    public JsonElement serialize(HashMap<Chunk, NTEChunk> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        JsonArray array = new JsonArray();
        for (var chunk : src.keySet()) {
            var nteChunk = src.get(chunk);
            array.add(context.serialize(nteChunk));
        }
        jsonObject.add("NTEChunks", array);

        return jsonObject;
    }

    @Override
    public HashMap<Chunk, NTEChunk> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return super.deserialize(json, typeOfT, context);
    }
}
