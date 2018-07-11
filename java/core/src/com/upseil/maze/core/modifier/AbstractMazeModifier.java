package com.upseil.maze.core.modifier;

import com.upseil.maze.core.configuration.Configurable;
import com.upseil.maze.core.domain.Maze;

public abstract class AbstractMazeModifier<M extends Maze, C> implements MazeModifier<M>, Configurable<C> {
    
    private C configuration;
    
    @Override
    public C getConfiguration() {
        return configuration;
    }
    
    @Override
    public void setConfiguration(C configuration) {
        this.configuration = configuration;
    }
    
}
