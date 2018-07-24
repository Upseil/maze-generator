package com.upseil.maze.io.gson.adapter;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.upseil.maze.core.configuration.Configuration;
import com.upseil.maze.core.configuration.DeadEndStripperConfiguration;
import com.upseil.maze.core.configuration.LabyrinthConfiguration;
import com.upseil.maze.core.configuration.MazeFillerConfiguration;
import com.upseil.maze.core.configuration.PassageBreakerConfiguration;

public class ConfigurationAdapter implements JsonSerializer<Configuration>, JsonDeserializer<Configuration> {
    
    private static final String TypeProperty = "type";
    
    private final Gson gson;

    public ConfigurationAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public JsonElement serialize(Configuration src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = (JsonObject) gson.toJsonTree(src);
        json.addProperty(TypeProperty, src.getType());
        return json;
    }

    @Override
    public Configuration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.getAsJsonPrimitive(TypeProperty).getAsString();
        return gson.fromJson(json, getClassForType(type));
    }

    private Class<? extends Configuration> getClassForType(String type) {
        switch (type) {
        case DeadEndStripperConfiguration.Type:
            return DeadEndStripperConfiguration.class;
        case LabyrinthConfiguration.Type:
            return LabyrinthConfiguration.class;
        case MazeFillerConfiguration.Type:
            return MazeFillerConfiguration.class;
        case PassageBreakerConfiguration.Type:
            return PassageBreakerConfiguration.class;
        }
        throw new IllegalArgumentException("Unable to determine configuration class for type " + type);
    }
    
}
