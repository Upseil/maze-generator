package com.upseil.maze.generator;

import java.util.Random;

import com.upseil.maze.domain.factory.CellFactory;

public abstract class AbstractLabyrinthGenerator extends AbstractMazeGenerator {

    public AbstractLabyrinthGenerator(Random random, CellFactory cellFactory) {
        super(random, cellFactory);
    }

}
