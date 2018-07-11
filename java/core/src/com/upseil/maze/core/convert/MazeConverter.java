package com.upseil.maze.core.convert;

import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeConverter<M extends Maze, R> {
    
    R convert(M maze);
    
}
