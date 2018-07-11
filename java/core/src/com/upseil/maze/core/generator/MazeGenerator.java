package com.upseil.maze.core.generator;

import com.upseil.maze.core.domain.Maze;

@FunctionalInterface
public interface MazeGenerator<M extends Maze> {
    
    M generate(int width, int height);
    default M generate(int size) {
        return generate(size, size);
    }
    
}
