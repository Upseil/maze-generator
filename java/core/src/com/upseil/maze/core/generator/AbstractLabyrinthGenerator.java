package com.upseil.maze.core.generator;

import java.util.Random;

import com.upseil.maze.core.configuration.Configurable;
import com.upseil.maze.core.configuration.LabyrinthConfiguration;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.domain.factory.MazeFactory;

public abstract class AbstractLabyrinthGenerator<M extends Maze<C>, C extends Cell> extends AbstractMazeGenerator<M, C> implements Configurable<LabyrinthConfiguration> {

    private LabyrinthConfiguration configuration;

    public AbstractLabyrinthGenerator(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
        super(random, mazeFactory, cellFactory);
    }

    @Override
    public LabyrinthConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(LabyrinthConfiguration configuration) {
        this.configuration = configuration;
    }

}
