package com.upseil.maze.core.convert;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;

public interface MazeFormatter<M extends Maze<C>, C extends Cell> extends MazeConverter<M, C, String> {
    
}
