package com.upseil.maze.core.modifier;

import java.util.Objects;

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
        Objects.requireNonNull(configuration, "configuration must not be null");
        this.configuration = configuration;
    }
    
}
