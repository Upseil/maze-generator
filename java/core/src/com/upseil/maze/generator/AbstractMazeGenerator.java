package com.upseil.maze.generator;

import java.util.Random;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.MazeFactory;

public abstract class AbstractMazeGenerator<M extends Maze<C>, C extends Cell> implements MazeGenerator<M, C> {
    
    private final Random random;
    private final MazeFactory<M, C> mazeFactory;
    private final CellFactory<C> cellFactory;
    
    public AbstractMazeGenerator(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
        this.random = random;
        this.mazeFactory = mazeFactory;
        this.cellFactory = cellFactory;
    }
    
    protected void fillRemainingCells(M maze, CellType type) {
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
    
    protected MazeFactory<M, C> getMazeFactory() {
        return mazeFactory;
    }
    
    protected CellFactory<C> getCellFactory() {
        return cellFactory;
    }
    
    protected Random getRandom() {
        return random;
    }
    
    protected int randomInt(int bound) {
        return random.nextInt(bound);
    }
    
}
