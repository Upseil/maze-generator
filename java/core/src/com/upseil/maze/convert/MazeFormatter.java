package com.upseil.maze.convert;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;

public interface MazeFormatter<M extends Maze<C>, C extends Cell> extends MazeConverter<M, C, String> {
    
}
