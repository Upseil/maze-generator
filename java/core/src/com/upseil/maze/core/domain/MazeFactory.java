package com.upseil.maze.core.domain;

@FunctionalInterface
public interface MazeFactory<M extends Maze> {
    
    MazeFactory<Maze> DefaultGridMaze = (w, h) -> new GridMaze(w, h, Direction.fullValues(), Maze.DefaultMapFactory);
    
    M create(int width, int height);
    
}
