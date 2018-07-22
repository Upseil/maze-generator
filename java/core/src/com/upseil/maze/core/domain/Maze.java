package com.upseil.maze.core.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.upseil.maze.core.function.IntIntConsumer;

public interface Maze extends Iterable<Cell> {
    
    final Predicate<Object> DefaultPredicate = c -> true;
    
    int getWidth();
    int getHeight();
    boolean isInBounds(int x, int y);
    
    Cell getCell(int x, int y);
    void setCell(Cell cell);
    void removeCell(int x, int y);
    
    default void setCells(Iterable<Cell> cells) {
        for (Cell cell : cells) {
            setCell(cell);
        }
    }
    
    Iterable<Direction> getWalkableDirections();
    Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super Cell> predicate, Map<Direction, Cell> result);
    
    default Cell getNeighbour(int x, int y, Direction direction) {
        int neighbourX = x + direction.getDeltaX();
        int neighbourY = y + direction.getDeltaY();
        return isInBounds(neighbourX, neighbourY) ? getCell(neighbourX, neighbourY) : null;
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y) {
        return getNeighbours(x, y, getWalkableDirections(), DefaultPredicate, new HashMap<>());
    }
    default Map<Direction, Cell> getNeighbours(Cell cell) {
        return getNeighbours(cell.getX(), cell.getY(), getWalkableDirections(), DefaultPredicate, new HashMap<>());
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y, Map<Direction, Cell> result) {
        return getNeighbours(x, y, getWalkableDirections(), DefaultPredicate, result);
    }
    default Map<Direction, Cell> getNeighbours(Cell cell, Map<Direction, Cell> result) {
        return getNeighbours(cell.getX(), cell.getY(), getWalkableDirections(), DefaultPredicate, result);
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions) {
        return getNeighbours(x, y, directions, DefaultPredicate, new HashMap<>());
    }
    default Map<Direction, Cell> getNeighbours(Cell cell, Iterable<Direction> directions) {
        return getNeighbours(cell.getX(), cell.getY(), directions, DefaultPredicate, new HashMap<>());
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, Cell> result) {
        return getNeighbours(x, y, directions, DefaultPredicate, result);
    }
    default Map<Direction, Cell> getNeighbours(Cell cell, Iterable<Direction> directions, Map<Direction, Cell> result) {
        return getNeighbours(cell.getX(), cell.getY(), directions, DefaultPredicate, result);
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y, Predicate<? super Cell> predicate) {
        return getNeighbours(x, y, getWalkableDirections(), predicate, new HashMap<>());
    }
    default Map<Direction, Cell> getNeighbours(Cell cell, Predicate<? super Cell> predicate) {
        return getNeighbours(cell.getX(), cell.getY(), getWalkableDirections(), predicate, new HashMap<>());
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super Cell> predicate) {
        return getNeighbours(x, y, directions, predicate, new HashMap<>());
    }
    default Map<Direction, Cell> getNeighbours(Cell cell, Iterable<Direction> directions, Predicate<? super Cell> predicate) {
        return getNeighbours(cell.getX(), cell.getY(), directions, predicate, new HashMap<>());
    }
    
    default Map<Direction, Cell> getNeighbours(int x, int y, Predicate<? super Cell> predicate, Map<Direction, Cell> result) {
        return getNeighbours(x, y, getWalkableDirections(), predicate, result);
    }
    default Map<Direction, Cell> getNeighbours(Cell cell, Predicate<? super Cell> predicate, Map<Direction, Cell> result) {
        return getNeighbours(cell.getX(), cell.getY(), getWalkableDirections(), predicate, result);
    }
    
    default Map<Direction, Cell> getNeighbours(Cell cell, Iterable<Direction> directions, Predicate<? super Cell> predicate, Map<Direction, Cell> result) {
        return getNeighbours(cell.getX(), cell.getY(), directions, predicate, result);
    }
    
    default void forEachPoint(IntIntConsumer consumer) {
        int width = getWidth();
        int height = getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                consumer.accept(x, y);
            }
        }
    }
    
    default void forEach(Predicate<? super Cell> predicate, Consumer<? super Cell> action) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(predicate);
        for (Cell cell : this) {
            if (predicate.test(cell)) {
                action.accept(cell);
            }
        }
    }
    
}