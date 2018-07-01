package com.upseil.maze.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleMaze extends GenericMaze<SimpleCell> {
    
    private final static Supplier<Map<Direction, SimpleCell>> DefaultMapFactory = () -> new HashMap<>();

    public SimpleMaze(int width, int height) {
        super(width, height, DefaultMapFactory);
    }

    public SimpleMaze(int width, int height, Supplier<Map<Direction, SimpleCell>> mapFactory) {
        super(width, height, mapFactory);
    }
    
}
