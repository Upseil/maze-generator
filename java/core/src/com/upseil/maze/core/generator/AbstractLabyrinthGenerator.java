package com.upseil.maze.core.generator;

import java.util.Random;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.domain.factory.MazeFactory;

public abstract class AbstractLabyrinthGenerator<M extends Maze<C>, C extends Cell> extends AbstractMazeGenerator<M, C> {

    public AbstractLabyrinthGenerator(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
        super(random, mazeFactory, cellFactory);
    }

}
