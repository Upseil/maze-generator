package com.upseil.maze.core.convert;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeConverter<M extends Maze<C>, C extends Cell, R> {
    
    R convert(M maze);
    
}
