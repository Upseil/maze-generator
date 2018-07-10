package com.upseil.maze.core.domain;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.upseil.maze.core.function.IntIntConsumer;

public interface Maze<C extends Cell> extends Iterable<C> {
    
    C getCell(int x, int y);
    
    void setCell(C cell);
    default void setCells(Iterable<C> cells) {
        for (C cell : cells) {
            setCell(cell);
        }
    }
    
    void removeCell(int x, int y);
    
    C getNeighbour(int x, int y, Direction direction);
    
    Map<Direction, C> getNeighbours(int x, int y);
    default Map<Direction, C> getNeighbours(C cell) {
        return getNeighbours(cell.getX(), cell.getY());
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Map<Direction, C> result);
    default Map<Direction, C> getNeighbours(C cell, Map<Direction, C> result) {
        return getNeighbours(cell.getX(), cell.getY(), result);
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions);
    default Map<Direction, C> getNeighbours(C cell, Iterable<Direction> directions) {
        return getNeighbours(cell.getX(), cell.getY(), directions);
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Map<Direction, C> result);
    default Map<Direction, C> getNeighbours(C cell, Iterable<Direction> directions, Map<Direction, C> result) {
        return getNeighbours(cell.getX(), cell.getY(), directions, result);
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Predicate<? super C> predicate);
    default Map<Direction, C> getNeighbours(C cell, Predicate<? super C> predicate) {
        return getNeighbours(cell.getX(), cell.getY(), predicate);
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super C> predicate);
    default Map<Direction, C> getNeighbours(C cell, Iterable<Direction> directions, Predicate<? super C> predicate) {
        return getNeighbours(cell.getX(), cell.getY(), directions, predicate);
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Predicate<? super C> predicate, Map<Direction, C> result);
    default Map<Direction, C> getNeighbours(C cell, Predicate<? super C> predicate, Map<Direction, C> result) {
        return getNeighbours(cell.getX(), cell.getY(), predicate, result);
    }
    
    Map<Direction, C> getNeighbours(int x, int y, Iterable<Direction> directions, Predicate<? super C> predicate, Map<Direction, C> result);
    default Map<Direction, C> getNeighbours(C cell, Iterable<Direction> directions, Predicate<? super C> predicate, Map<Direction, C> result) {
        return getNeighbours(cell.getX(), cell.getY(), directions, predicate, result);
    }
    
    int getWidth();
    int getHeight();
    boolean isInBounds(int x, int y);
    
    default void forEachPoint(IntIntConsumer consumer) {
        int width = getWidth();
        int height = getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                consumer.accept(x, y);
            }
        }
    }
    
    default void forEach(Predicate<? super C> predicate, Consumer<? super C> action) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(predicate);
        for (C cell : this) {
            if (predicate.test(cell)) {
                action.accept(cell);
            }
        }
    }
    
}