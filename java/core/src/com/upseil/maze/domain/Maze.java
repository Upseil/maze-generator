package com.upseil.maze.domain;

import java.util.Map;
import java.util.function.Predicate;

public interface Maze extends Iterable<Cell> {
    
    Cell getCell(int x, int y);
    void setCell(int x, int y, Cell cell);
    
    Cell getNeighbour(int x, int y, Direction direction);
    
    Map<Direction, Cell> getNeighbours(int x, int y);
    Map<Direction, Cell> getNeighbours(int x, int y, Map<Direction, Cell> result);
    Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions);
    Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, Cell> result);
    Map<Direction, Cell> getNeighbours(int x, int y, Predicate<Cell> predicate);
    Map<Direction, Cell> getNeighbours(int x, int y, Predicate<Cell> predicate, Map<Direction, Cell> result);
    Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<Cell> predicate, Map<Direction, Cell> result);
    
    int getWidth();
    int getHeight();
    boolean isInBounds(int x, int y);
    
}