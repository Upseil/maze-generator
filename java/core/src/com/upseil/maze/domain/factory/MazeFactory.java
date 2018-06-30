package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.GenericMaze;

public interface MazeFactory<M extends Maze<C>, C extends Cell> {
    
    MazeFactory<Maze<Cell>, Cell> Default = (w, h) -> new GenericMaze<>(w, h);
    
    M create(int width, int height);
    
}
