package com.upseil.maze.core.modifier;

import java.util.Random;

import com.upseil.maze.core.domain.Maze;

public abstract class AbstractRandomizedMazeModifier<M extends Maze, C> extends AbstractMazeModifier<M, C> {
    
    private final Random random;

    public AbstractRandomizedMazeModifier(Random random) {
        this.random = random;
    }
    
    public Random getRandom() {
        return random;
    }
    
}
