package com.upseil.maze.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleMaze implements Maze {
    
    private static final Supplier<Map<Direction, Cell>> DefaultMapFactory = () -> new HashMap<>();
    private static final Predicate<Cell> DefaultPredicate = c -> true;
    
    private final Cell[][] cells;
    private final Supplier<Map<Direction, Cell>> mapFactory;
    
    public SimpleMaze(int width, int height) {
        this(width, height, DefaultMapFactory);
    }

    public SimpleMaze(int width, int height, Supplier<Map<Direction, Cell>> mapFactory) {
        cells = new Cell[width][height];
        this.mapFactory = mapFactory;
    }
    
    @Override
    public Cell getCell(int x, int y) {
        validateCoordinates(x, y);
        return unsafeGet(x, y);
    }
    
    @Override
    public Cell getNeighbour(int x, int y, Direction direction) {
        validateCoordinates(x, y);
        
        int neighbourX = x + direction.getDeltaX();
        int neighbourY = y + direction.getDeltaY();
        return isInBounds(neighbourX, neighbourY) ? unsafeGet(neighbourX, neighbourY) : null;
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y) {
        return getNeighbours(x, y, Direction.iterable(), DefaultPredicate, mapFactory.get());
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y, Map<Direction, Cell> result) {
        return getNeighbours(x, y, Direction.iterable(), DefaultPredicate, result);
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions) {
        return getNeighbours(x, y, directions, DefaultPredicate, mapFactory.get());
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, Cell> result) {
        return getNeighbours(x, y, directions, DefaultPredicate, result);
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y, Predicate<Cell> predicate) {
        return getNeighbours(x, y, Direction.iterable(), predicate, mapFactory.get());
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y, Predicate<Cell> predicate, Map<Direction, Cell> result) {
        return getNeighbours(x, y, Direction.iterable(), predicate, result);
    }
    
    @Override
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

    @Override
    public void setCell(int x, int y, Cell cell) {
        validateCoordinates(x, y);
        unsafeSet(x, y, cell);
    }
    
    @Override
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
    
    @Override
    public int getWidth() {
        return cells.length;
    }
    
    @Override
    public int getHeight() {
        return cells[0].length;
    }
    
    @Override
    public Iterator<Cell> iterator() {
        return new MazeIterator();
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
    
    private class MazeIterator implements Iterator<Cell> {
        
        private int x;
        private int y;
        
        public MazeIterator() {
            x = -1;
            y = 0;
            findNext();
        }

        @Override
        public boolean hasNext() {
            return x >= 0 && y >= 0;
        }

        @Override
        public Cell next() {
            Cell current = unsafeGet(x, y);
            findNext();
            return current;
        }

        private void findNext() {
            int width = getWidth();
            int height = getHeight();
            
            boolean foundNext = false;
            while (!foundNext) {
                x++;
                if (x >= width) {
                    x = 0;
                    y++;
                    if (y >= height) {
                        break;
                    }
                }
                
                if (unsafeGet(x, y) != null) {
                    foundNext = true;
                }
            }
            
            if (!foundNext) {
                x = -1;
                y = -1;
            }
        }
        
    }
    
}
