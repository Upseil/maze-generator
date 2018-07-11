package com.upseil.maze.core.domain;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

public class GridMaze implements Maze {
    
    private final Cell[][] cells;
    private final Iterable<Direction> walkableDirections;

    public GridMaze(int width, int height, Iterable<Direction> walkableDirections) {
        cells = new Cell[width][height];
        this.walkableDirections = walkableDirections;
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
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
    
    @Override
    public Cell getCell(int x, int y) {
        validateCoordinates(x, y);
        return unsafeGet(x, y);
    }

    @Override
    public void setCell(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        validateCoordinates(x, y);
        unsafeSet(x, y, cell);
    }
    
    @Override
    public void removeCell(int x, int y) {
        validateCoordinates(x, y);
        unsafeSet(x, y, null);
    }
    
    @Override
    public Iterable<Direction> getWalkableDirections() {
        return walkableDirections;
    }
    
    @Override
    public Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super Cell> predicate, Map<Direction, Cell> result) {
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
