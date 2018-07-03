package com.upseil.maze.core.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GenericMaze<C extends Cell> implements Maze<C> {
    
    private static final Predicate<Object> DefaultPredicate = c -> true;
    
    private final C[][] cells;
    private final Supplier<Map<Direction, C>> mapFactory;
    
    public GenericMaze(int width, int height) {
        this(width, height, () -> new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public GenericMaze(int width, int height, Supplier<Map<Direction, C>> mapFactory) {
        cells = (C[][]) new Cell[width][height];
        this.mapFactory = mapFactory;
    }
    
    @Override
    public C getCell(int x, int y) {
        validateCoordinates(x, y);
        return unsafeGet(x, y);
    }
    
    @Override
    public C getNeighbour(int x, int y, Direction direction) {
        validateCoordinates(x, y);
        
        int neighbourX = x + direction.getDeltaX();
        int neighbourY = y + direction.getDeltaY();
        return isInBounds(neighbourX, neighbourY) ? unsafeGet(neighbourX, neighbourY) : null;
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y) {
        return getNeighbours(x, y, Direction.iterable(), DefaultPredicate, mapFactory.get());
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y, Map<Direction, C> result) {
        return getNeighbours(x, y, Direction.iterable(), DefaultPredicate, result);
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions) {
        return getNeighbours(x, y, directions, DefaultPredicate, mapFactory.get());
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, C> result) {
        return getNeighbours(x, y, directions, DefaultPredicate, result);
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y, Predicate<? super C> predicate) {
        return getNeighbours(x, y, Direction.iterable(), predicate, mapFactory.get());
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y, Predicate<? super C> predicate, Map<Direction, C> result) {
        return getNeighbours(x, y, Direction.iterable(), predicate, result);
    }
    
    @Override
    public Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super C> predicate, Map<Direction, C> result) {
        validateCoordinates(x, y);
        
        for (Direction direction : directions) {
            int neighbourX = x + direction.getDeltaX();
            int neighbourY = y + direction.getDeltaY();
            if (isInBounds(neighbourX, neighbourY)) {
                C neighbour = unsafeGet(neighbourX, neighbourY);
                if (predicate.test(neighbour)) {
                    result.put(direction, neighbour);
                }
            }
        }
        return result;
    }

    @Override
    public void setCell(int x, int y, C cell) {
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
    public Iterator<C> iterator() {
        return new MazeIterator();
    }

    protected final C unsafeGet(int x, int y) {
        return cells[x][y];
    }

    protected final void unsafeSet(int x, int y, C cell) {
        cells[x][y] = cell;
    }

    private void validateCoordinates(int x, int y) {
        if (!isInBounds(x, y)) {
            throw new IndexOutOfBoundsException("The given coordinates are out of bounds: 0 <= " + x + " < " + getWidth() + " | "
                                                                                       + "0 <= " + y + " < " + getHeight());
        }
    }
    
    private class MazeIterator implements Iterator<C> {
        
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
        public C next() {
            C current = unsafeGet(x, y);
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
