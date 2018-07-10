package com.upseil.maze.core.modifier;

import com.upseil.maze.core.configuration.Configurable;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;

public abstract class AbstractMazeModifier<M extends Maze<C>, C extends Cell, S> implements MazeModifier<M, C>, Configurable<S> {
    
    protected final CellFactory<C> cellFactory;
    private S configuration;

    public AbstractMazeModifier(CellFactory<C> cellFactory) {
        this.cellFactory = cellFactory;
    }
    
    @Override
    public S getConfiguration() {
        return configuration;
    }
    
    @Override
    public void setConfiguration(S configuration) {
        this.configuration = configuration;
    }
    
}
