package com.upseil.maze.core.generator;

import java.util.Random;
import java.util.function.Predicate;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.Point;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.domain.factory.MazeFactory;

public abstract class AbstractMazeGenerator<M extends Maze<C>, C extends Cell> implements MazeGenerator<M, C> {
    
    private final Random random;
    private final MazeFactory<M, C> mazeFactory;
    private final CellFactory<C> cellFactory;
    
    public AbstractMazeGenerator(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
        this.random = random;
        this.mazeFactory = mazeFactory;
        this.cellFactory = cellFactory;
    }
    
    protected MazeFactory<M, C> getMazeFactory() {
        return mazeFactory;
    }
    
    protected CellFactory<C> getCellFactory() {
        return cellFactory;
    }
    
    protected Random getRandom() {
        return random;
    }
    
    protected int randomInt(int bound) {
        return random.nextInt(bound);
    }
    
    protected Point randomPoint(int xBound, int yBound) {
        return new Point(randomInt(xBound), randomInt(yBound));
    }
    
    protected Point randomPoint(int xBound, int yBound, Predicate<Point> predicate) {
        Point point;
        do {
            point = randomPoint(xBound, yBound);
        } while (!predicate.test(point));
        return point;
    }
    
}
