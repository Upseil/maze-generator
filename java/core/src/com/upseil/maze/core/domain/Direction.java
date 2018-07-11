package com.upseil.maze.core.domain;

import java.util.Arrays;

public enum Direction {
    
    North(0, 1), East(1, 0), South(0, -1), West(-1, 0),
    NorthEast(1, 1), NorthWest(-1, 1), SouthEast(1, -1), SouthWest(-1, -1);
    
    private static final Iterable<Direction> AllValuesIterable = Arrays.asList(North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest);
    private static final Iterable<Direction> FullValuesIterable = Arrays.asList(North, East, South, West);
    
    public static Iterable<Direction> allValues() {
        return AllValuesIterable;
    }
    
    public static Iterable<Direction> fullValues() {
        return FullValuesIterable;
    }
    
    public static Direction getOpposite(Direction direction) {
        switch (direction) {
        case North:     return South;
        case NorthEast: return SouthWest;
        case East:      return West;
        case SouthEast: return NorthWest;
        case South:     return North;
        case SouthWest: return NorthEast;
        case West:      return East;
        case NorthWest: return SouthEast;
        }
        throw new IllegalArgumentException("No opposite available for " + direction);
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
    
    public Direction getOpposite() {
        return Direction.getOpposite(this);
    }
    
}
