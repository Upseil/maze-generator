package com.upseil.maze.io.gson.test.adapter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import com.google.gson.Gson;
import com.upseil.maze.core.configuration.Configuration;
import com.upseil.maze.core.configuration.LabyrinthConfiguration;
import com.upseil.maze.core.configuration.LabyrinthConfiguration.Border;
import com.upseil.maze.io.gson.GsonFactory;

class TestConfigurationAdapter {
    
    private static final Gson gson = GsonFactory.createGson();
    
    @Test
    void testSimpleSerialization() {
        String expectedJson = "{\"border\":\"" + Border.Indifferent + "\",\"type\":\"" + LabyrinthConfiguration.Type + "\"}";
        assertThat(gson.toJson(new LabyrinthConfiguration()), is(expectedJson));
    }
    
    @Test
    void testSerialization() throws InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("com.upseil.maze");
        for (Class<? extends Configuration> clazz : reflections.getSubTypesOf(Configuration.class)) {
            if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                continue;
            }
            
            Configuration instance = clazz.newInstance();
            String json = gson.toJson(instance);
            assertThat(gson.fromJson(json, Configuration.class), is(instance));
        }
    }
    
}
