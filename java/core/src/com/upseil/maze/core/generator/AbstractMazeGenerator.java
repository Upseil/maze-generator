package com.upseil.maze.core.generator;

import java.util.Random;
import java.util.function.Predicate;

import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.domain.Point;

public abstract class AbstractMazeGenerator<M extends Maze> implements MazeGenerator<M> {
    
    private final Random random;
    private final MazeFactory<M> mazeFactory;
    
    public AbstractMazeGenerator(Random random, MazeFactory<M> mazeFactory) {
        this.random = random;
        this.mazeFactory = mazeFactory;
    }
    
    protected MazeFactory<M> getMazeFactory() {
        return mazeFactory;
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
