package com.upseil.maze.generator;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;

public interface MazeGenerator<M extends Maze<C>, C extends Cell> {
    
    M generate(int width, int height);
    
}
