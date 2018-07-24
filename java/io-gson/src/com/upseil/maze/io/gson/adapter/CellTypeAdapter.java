package com.upseil.maze.io.gson.adapter;

import static java.lang.reflect.Modifier.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.upseil.maze.core.domain.CellType;

public class CellTypeAdapter implements JsonSerializer<CellType>, JsonDeserializer<CellType> {
    
    private static final Logger logger = Logger.getLogger(CellTypeAdapter.class.getName());
    
    private static Map<String, CellType> BuiltInTypes;
    
    private static CellType getBuiltInType(String name) {
        if (BuiltInTypes == null) {
            BuiltInTypes = new HashMap<>();
            for (Field field : CellType.class.getDeclaredFields()) {
                if ((field.getModifiers() & (PUBLIC ^ STATIC ^ FINAL)) != 0 && field.getType().equals(CellType.class)) {
                    try {
                        CellType type = (CellType) field.get(null);
                        BuiltInTypes.put(type.getName(), type);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        logger.log(Level.SEVERE, "Error retrieving built-in cell type from field: " + field, e);
                    }
                }
            }
        }
        return BuiltInTypes.get(name);
    }

    @Override
    public JsonElement serialize(CellType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getName());
    }

    @Override
    public CellType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String typeName = json.getAsString();
        CellType type = getBuiltInType(typeName);
        if (type == null) {
            type = new CellType(typeName);
        }
        return type;
    }
    
}
