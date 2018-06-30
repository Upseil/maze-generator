package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;

public class FilledMazeFactory implements MazeFactory {
    
    private CellFactory cellFactory;
    private CellType cellType;
    
    public FilledMazeFactory(CellFactory cellFactory, CellType cellType) {
        this.cellFactory = cellFactory;
        this.cellType = cellType;
    }

    @Override
    public Maze create(int width, int height) {
        Maze maze = new Maze(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                maze.setCell(x, y, cellFactory.create(x, y, cellType));
            }
        }
        return maze;
    }
    
}
