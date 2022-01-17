package com.makotomiyamoto.nt.encampments.core.adapter;

import com.google.gson.*;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import com.makotomiyamoto.nt.encampments.util.JsonSerializationAdapter;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;

public class NTEChunkSerializationAdapter extends JsonSerializationAdapter<NTEChunk> {
    @Override
    public JsonElement serialize(NTEChunk src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        JsonArray changedBlocksJsonArray = new JsonArray();
        for (var changedBlock : src.getChangedBlocks().values()) {
            changedBlocksJsonArray.add(context.serialize(changedBlock));
        }
        jsonObject.add("changed_blocks", changedBlocksJsonArray);

        return jsonObject;
    }

    @Override
    public NTEChunk deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return super.deserialize(json, typeOfT, context);
    }
}
