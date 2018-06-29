package com.upseil.maze.domain;

import java.util.Arrays;

public enum Direction {
    
    North(0, 1), East(1, 0), South(0, -1), West(-1, 0),
    NorthEast(1, 1), NorthWest(-1, 1), SouthEast(1, -1), SouthWest(-1, -1);
    
    private static final Iterable<Direction> valuesIterable = Arrays.asList(values());
    
    public static Iterable<Direction> iterable() {
        return valuesIterable;
    }
    
    private final int deltaX;
    private final int deltaY;
    
    private Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
    
}
