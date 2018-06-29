package com.upseil.maze.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Maze {
    
    private static final Supplier<Map<Direction, Cell>> DefaultMapFactory = () -> new HashMap<>();
    private static final Predicate<Cell> DefaultPredicate = c -> true;
    
    private final Cell[][] cells;
    private final Supplier<Map<Direction, Cell>> mapFactory;
    
    public Maze(int width, int height) {
        this(width, height, DefaultMapFactory);
    }

    public Maze(int width, int height, Supplier<Map<Direction, Cell>> mapFactory) {
        cells = new Cell[width][height];
        this.mapFactory = mapFactory;
    }
    
    public Cell getCell(int x, int y) {
        validateCoordinates(x, y);
        return unsafeGet(x, y);
    }
    
    public Cell getNeighbour(int x, int y, Direction direction) {
        validateCoordinates(x, y);
        
        int neighbourX = x + direction.getDeltaX();
        int neighbourY = y + direction.getDeltaY();
        return isInBounds(neighbourX, neighbourY) ? unsafeGet(neighbourX, neighbourY) : null;
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y) {
        return getNeighbours(x, y, Direction.iterable(), DefaultPredicate, mapFactory.get());
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y, Map<Direction, Cell> result) {
        return getNeighbours(x, y, Direction.iterable(), DefaultPredicate, result);
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions) {
        return getNeighbours(x, y, directions, DefaultPredicate, mapFactory.get());
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, Cell> result) {
        return getNeighbours(x, y, directions, DefaultPredicate, result);
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y, Predicate<Cell> predicate) {
        return getNeighbours(x, y, Direction.iterable(), predicate, mapFactory.get());
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y, Predicate<Cell> predicate, Map<Direction, Cell> result) {
        return getNeighbours(x, y, Direction.iterable(), predicate, result);
    }
    
    public Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<Cell> predicate, Map<Direction, Cell> result) {
        validateCoordinates(x, y);
        
        for (Direction direction : directions) {
            int neighbourX = x + direction.getDeltaX();
            int neighbourY = y + direction.getDeltaY();
            if (isInBounds(neighbourX, neighbourY)) {
                Cell neighbour = unsafeGet(neighbourX, neighbourY);
                if (predicate.test(neighbour)) {
                    result.put(direction, neighbour);
                }
            }
        }
        return result;
    }

    public void setCell(int x, int y, Cell cell) {
        validateCoordinates(x, y);
        unsafeSet(x, y, cell);
    }
    
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
    
    public int getWidth() {
        return cells.length;
    }
    
    public int getHeight() {
        return cells[0].length;
    }

    protected final Cell unsafeGet(int x, int y) {
        return cells[x][y];
    }

    protected final void unsafeSet(int x, int y, Cell cell) {
        cells[x][y] = cell;
    }

    private void validateCoordinates(int x, int y) {
        if (!isInBounds(x, y)) {
            throw new IndexOutOfBoundsException("The given coordinates are out of bounds: 0 <= " + x + " < " + getWidth() + " | "
                                                                                       + "0 <= " + y + " < " + getHeight());
        }
    }
    
}
