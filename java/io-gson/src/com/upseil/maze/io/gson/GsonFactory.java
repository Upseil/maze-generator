package com.upseil.maze.io.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.upseil.maze.core.configuration.Configuration;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.io.gson.adapter.CellTypeAdapter;
import com.upseil.maze.io.gson.adapter.ConfigurationAdapter;

public final class GsonFactory {
    
    private GsonFactory() { }
    
    private static final GsonBuilder builder = createBuilder();
    

    public static GsonBuilder createBuilder() {
        return createBuilder(false);
    }
    
    public static GsonBuilder createBuilder(boolean prettyPrinting) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(CellType.class, new CellTypeAdapter());
        if (prettyPrinting) {
            builder.setPrettyPrinting();
        }
        
        Gson internalGson = builder.create();
        builder.registerTypeHierarchyAdapter(Configuration.class, new ConfigurationAdapter(internalGson));
        
        return builder;
    }
    
    public static Gson createGson() {
        return builder.create();
    }
    
}
