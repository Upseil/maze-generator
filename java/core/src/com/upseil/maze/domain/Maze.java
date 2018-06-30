package com.upseil.maze.domain;

import java.util.Map;
import java.util.function.Predicate;

public interface Maze<C extends Cell> extends Iterable<C> {
    
    C getCell(int x, int y);
    void setCell(int x, int y, C cell);
    
    C getNeighbour(int x, int y, Direction direction);
    
    Map<Direction, C> getNeighbours(int x, int y);
    Map<Direction, C> getNeighbours(int x, int y, Map<Direction, C> result);
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions);
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, C> result);
    Map<Direction, C> getNeighbours(int x, int y, Predicate<? super C> predicate);
    Map<Direction, C> getNeighbours(int x, int y, Predicate<? super C> predicate, Map<Direction, C> result);
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super C> predicate, Map<Direction, C> result);
    
    int getWidth();
    int getHeight();
    boolean isInBounds(int x, int y);
    
}