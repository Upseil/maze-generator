package com.upseil.maze.desktop.display;

import com.upseil.maze.domain.Maze;

import javafx.beans.property.ObjectProperty;

public interface MazeView {
    
    final String DefaultStyle = "style/maze/default.css";
    
    ObjectProperty<Maze<?>> mazeProperty();
    
    void setMaze(Maze<?> maze);
    
    Maze<?> getMaze();
    
}