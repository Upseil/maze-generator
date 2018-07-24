package com.upseil.maze.io.gson.test.adapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import static com.upseil.maze.io.gson.test.TestUtil.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.io.gson.GsonFactory;

class TestCellTypeAdapter {
    
    private static final Gson gson = GsonFactory.createBuilder(true).create();
    
    @Test
    void testSerialization() {
        CellType type = new CellType("Custom");
        assertThat(gson.toJson(type), is(enquote(type.getName())));
        
        assertThat(gson.toJson(CellType.Floor), is(enquote(CellType.Floor.getName())));
    }
    
    @Test
    void testDeserializeCustomType() {
        String json = "Custom";
        CellType expectedType = new CellType("Custom");
        assertThat(gson.fromJson(json, CellType.class), is(expectedType));
    }
    
    @Test
    void testDeserializeBuiltInType() {
        String json = CellType.Floor.getName();
        assertThat(gson.fromJson(json, CellType.class), is(sameInstance(CellType.Floor)));
    }
    
}
