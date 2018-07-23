package com.upseil.maze.core.generator;

import java.util.Random;
import java.util.function.Predicate;

import com.upseil.maze.core.configuration.Configurable;
import com.upseil.maze.core.configuration.GeneratorConfiguration;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.domain.Point;

public abstract class AbstractMazeGenerator<M extends Maze, C extends GeneratorConfiguration> implements MazeGenerator<M>, Configurable<C> {
    
    private final Random random;
    private final MazeFactory<M> mazeFactory;
    private C configuration;
    
    protected AbstractMazeGenerator(Random random, MazeFactory<M> mazeFactory) {
        this.random = random;
        this.mazeFactory = mazeFactory;
    }
    
    @Override
    public C getConfiguration() {
        return configuration;
    }
    
    @Override
    public void setConfiguration(C configuration) {
        this.configuration = configuration;
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
