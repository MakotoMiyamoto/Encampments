package com.makotomiyamoto.nt.encampments.core.adapter;

import com.google.gson.*;
import com.makotomiyamoto.nt.encampments.util.JsonSerializationAdapter;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Type;

public class BlockDataSerializationAdapter extends JsonSerializationAdapter<BlockData> {
    @Override
    public JsonElement serialize(BlockData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getAsString());
    }

    @Override
    public BlockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Bukkit.createBlockData(json.getAsString());
    }
}
