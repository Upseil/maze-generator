package com.upseil.maze.core.domain.factory;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.GenericMaze;
import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeFactory<M extends Maze<C>, C extends Cell> {
    
    MazeFactory<Maze<Cell>, Cell> Default = (w, h) -> new GenericMaze<>(w, h);
    
    M create(int width, int height);
    
}
