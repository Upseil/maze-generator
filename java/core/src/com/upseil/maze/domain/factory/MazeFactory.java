package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.SimpleMaze;

public interface MazeFactory {
    
    MazeFactory Default = (w, h) -> new SimpleMaze(w, h);
    
    SimpleMaze create(int width, int height);
    
}
