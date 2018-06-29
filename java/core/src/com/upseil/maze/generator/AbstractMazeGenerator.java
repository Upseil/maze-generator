package com.upseil.maze.generator;

import java.util.Random;

import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;

public abstract class AbstractMazeGenerator implements MazeGenerator {
    
    private final Random random;
    private final CellFactory cellFactory;
    
    public AbstractMazeGenerator(Random random, CellFactory cellFactory) {
        this.random = random;
        this.cellFactory = cellFactory;
    }
    
    protected void fillRemainingCells(Maze maze, CellType type) {
        int width = maze.getWidth();
        int height = maze.getHeight();
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (maze.getCell(x, y) == null) {
                    maze.setCell(x, y, cellFactory.create(x, y, type));
                }
            }
        }
    }
    
    protected CellFactory getCellFactory() {
        return cellFactory;
    }
    
    protected Random getRandom() {
        return random;
    }
    
    protected int randomInt(int bound) {
        return random.nextInt(bound);
    }
    
}
