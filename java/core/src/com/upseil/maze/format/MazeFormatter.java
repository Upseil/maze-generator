package com.upseil.maze.format;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;

public interface MazeFormatter<M extends Maze<C>, C extends Cell> {
    
    String format(M maze);
    
}
