package com.upseil.maze.generator;

import com.upseil.maze.domain.Maze;

public interface MazeGenerator {
    
    Maze generate(int width, int height);
    
}
