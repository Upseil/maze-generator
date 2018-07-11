package com.upseil.maze.core.modifier;

import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeModifier<M extends Maze> {
    
    M modify(M maze);
    
}
