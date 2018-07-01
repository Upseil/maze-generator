package com.upseil.maze.convert;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;

@FunctionalInterface
public interface MazeConverter<M extends Maze<C>, C extends Cell, R> {
    
    R convert(M maze);
    
}
