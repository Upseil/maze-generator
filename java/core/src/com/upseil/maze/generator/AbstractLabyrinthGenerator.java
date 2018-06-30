package com.upseil.maze.generator;

import java.util.Random;

import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.MazeFactory;

public abstract class AbstractLabyrinthGenerator extends AbstractMazeGenerator {

    public AbstractLabyrinthGenerator(Random random, MazeFactory mazeFactory, CellFactory cellFactory) {
        super(random, mazeFactory, cellFactory);
    }

}
