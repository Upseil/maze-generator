package com.upseil.maze.generator;

import java.util.Random;

import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.MazeFactory;

public abstract class AbstractMazeGenerator implements MazeGenerator {
    
    private final Random random;
    private final MazeFactory mazeFactory;
    private final CellFactory cellFactory;
    
    public AbstractMazeGenerator(Random random, MazeFactory mazeFactory, CellFactory cellFactory) {
        this.random = random;
        this.mazeFactory = mazeFactory;
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
    
    protected MazeFactory getMazeFactory() {
        return mazeFactory;
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
