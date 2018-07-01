package com.upseil.maze.modifier;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;

@FunctionalInterface
public interface MazeModifier<M extends Maze<C>, C extends Cell> {
    
    M modify(M maze);
    
}
