package com.upseil.maze.core.generator;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeGenerator<M extends Maze<C>, C extends Cell> {
    
    M generate(int width, int height);
    
}
