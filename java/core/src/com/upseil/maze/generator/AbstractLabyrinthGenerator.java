package com.upseil.maze.generator;

import java.util.Random;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.MazeFactory;

public abstract class AbstractLabyrinthGenerator<M extends Maze<C>, C extends Cell> extends AbstractMazeGenerator<M, C> {

    public AbstractLabyrinthGenerator(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
        super(random, mazeFactory, cellFactory);
    }

}
