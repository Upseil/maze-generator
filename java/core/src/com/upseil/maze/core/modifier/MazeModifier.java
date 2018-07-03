package com.upseil.maze.core.modifier;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeModifier<M extends Maze<C>, C extends Cell> {
    
    M modify(M maze);
    
}
