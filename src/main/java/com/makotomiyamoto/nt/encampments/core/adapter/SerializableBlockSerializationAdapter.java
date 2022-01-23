package com.makotomiyamoto.nt.encampments.core.adapter;

import com.google.gson.*;
import com.makotomiyamoto.nt.encampments.core.block.SerializableBisected;
import com.makotomiyamoto.nt.encampments.core.block.SerializableBlock;
import com.makotomiyamoto.nt.encampments.core.block.SerializableBlockFactory;
import com.makotomiyamoto.nt.encampments.core.block.SerializableSign;
import com.makotomiyamoto.nt.encampments.util.JsonSerializationAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Type;
import java.util.List;

public class SerializableBlockSerializationAdapter extends JsonSerializationAdapter<SerializableBlock> {
    static final String TYPE_BISECTED = "BISECTED";
    static final String TYPE_SIGN = "SIGN";
    static final String TYPE_NORMAL = "NORMAL";

    @Override
    public JsonElement serialize(SerializableBlock src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject blockJsonObject = new JsonObject();

        blockJsonObject.add("block_data", context.serialize(src.getBlockData()));
        blockJsonObject.add("location", context.serialize(src.getLocation()));
        blockJsonObject.add("material", context.serialize(src.getType()));
        if (src instanceof SerializableBisected) {
            blockJsonObject.addProperty("type", TYPE_BISECTED);
        }
        else if (src instanceof SerializableSign sign) {
            blockJsonObject.addProperty("type", TYPE_SIGN);
            JsonArray lines = new JsonArray();
            for (Component component : sign.getLines()) {
                lines.add(GsonComponentSerializer.gson().serialize(component));
            }
            blockJsonObject.add("lines", lines);

        }
        else {
            blockJsonObject.addProperty("type", TYPE_NORMAL);
        }

        return blockJsonObject;
    }

    @Override
    public SerializableBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        var jsonObject = (JsonObject) json;

        BlockData blockData = context.deserialize(jsonObject.get("block_data"), BlockData.class);
        Location location = context.deserialize(jsonObject.get("location"), Location.class);
        Material type = context.deserialize(jsonObject.get("material"), Material.class);
        String dataType = jsonObject.get("type").getAsString();

        SerializableBlock block;
        switch (dataType) {
            case TYPE_BISECTED -> block = SerializableBlockFactory.createSerializableBisected(blockData, location, type);
            case TYPE_SIGN -> {
                block = SerializableBlockFactory.createSerializableSign(blockData, location, type);
                SerializableSign sign = (SerializableSign) block;
                JsonArray lines = jsonObject.getAsJsonArray("lines");
                for (var line : lines) {
                    sign.getLines().add(GsonComponentSerializer.gson().deserialize(line.getAsString()));
                }
            }
            default -> block = SerializableBlockFactory.createSerializableBlock(blockData, location, type);
        }

        return block;
    }
}
