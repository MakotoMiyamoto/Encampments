package com.makotomiyamoto.nt.encampments.core.adapter;

import com.google.gson.*;
import com.makotomiyamoto.nt.encampments.core.block.BlockStateCache;
import com.makotomiyamoto.nt.encampments.core.block.ChangedBlock;
import com.makotomiyamoto.nt.encampments.core.block.NTEChunk;
import com.makotomiyamoto.nt.encampments.util.JsonSerializationAdapter;

import java.lang.reflect.Type;

public class BlockStateCacheSerializationAdapter extends JsonSerializationAdapter<BlockStateCache> {
    @Override
    public JsonElement serialize(BlockStateCache src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        JsonArray changedBlocks = new JsonArray();
        for (var nteChunk : src.getCache().values()) {
            for (var changedBlock : nteChunk.getChangedBlocks().values()) {
                changedBlocks.add(context.serialize(changedBlock));
            }
        }
        jsonObject.add("changedBlocks", changedBlocks);

        return jsonObject;
    }

    @Override
    public BlockStateCache deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        var blockStateCache = new BlockStateCache();

        var changedBlocks = jsonObject.getAsJsonArray("changedBlocks");
        for (var changedBlockJsonElement : changedBlocks) {
            ChangedBlock changedBlock = context.deserialize(changedBlockJsonElement.getAsJsonObject(), ChangedBlock.class);
            var location = changedBlock.getSerializableBlock().getLocation();
            if (!blockStateCache.getCache().containsKey(location.getChunk())) {
                blockStateCache.getCache().put(location.getChunk(), new NTEChunk(location.getChunk()));
            }
            var nteChunk = blockStateCache.getCache().get(location.getChunk());
            nteChunk.getChangedBlocks().put(location, changedBlock);
        }

        return blockStateCache;
    }
}
