package com.upseil.maze.core.generator;

import java.util.Random;

import com.upseil.maze.core.configuration.Configurable;
import com.upseil.maze.core.configuration.LabyrinthConfiguration;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;

public abstract class AbstractLabyrinthGenerator<M extends Maze> extends AbstractMazeGenerator<M> implements Configurable<LabyrinthConfiguration> {

    private LabyrinthConfiguration configuration;

    public AbstractLabyrinthGenerator(Random random, MazeFactory<M> mazeFactory) {
        super(random, mazeFactory);
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
