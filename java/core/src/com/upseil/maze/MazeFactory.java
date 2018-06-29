package com.upseil.maze;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;

public class MazeFactory {
    
    private MazeFactory() { }
    
    public static Maze createFilled(int width, int height, CellType type) {
        Maze maze = new Maze(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                maze.setCell(x, y, new Cell(x, y, type));
            }
        }
        return maze;
    }
    
}
