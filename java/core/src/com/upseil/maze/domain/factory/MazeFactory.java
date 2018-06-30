package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.Maze;

public interface MazeFactory {
    
    MazeFactory Default = (w, h) -> new Maze(w, h);
    
    Maze create(int width, int height);
    
}
