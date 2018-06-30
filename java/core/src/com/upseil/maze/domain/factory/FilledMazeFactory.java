package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;

public class FilledMazeFactory<M extends Maze<C>, C extends Cell> implements MazeFactory<M, C> {
    
    private MazeFactory<M, C> mazeFactory;
    private CellFactory<C> cellFactory;
    private CellType cellType;
    
    public FilledMazeFactory(MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory, CellType cellType) {
        this.mazeFactory = mazeFactory;
        this.cellFactory = cellFactory;
        this.cellType = cellType;
    }

    @Override
    public M create(int width, int height) {
        M maze = mazeFactory.create(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                maze.setCell(x, y, cellFactory.create(x, y, cellType));
            }
        }
        return maze;
    }
    
}
