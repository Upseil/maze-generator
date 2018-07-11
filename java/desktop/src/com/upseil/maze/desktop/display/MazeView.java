package com.upseil.maze.desktop.display;

import com.upseil.maze.core.domain.Maze;

import javafx.beans.property.ObjectProperty;

public interface MazeView {
    
    final String DefaultStyle = "style/maze/default.css";
    
    ObjectProperty<Maze> mazeProperty();
    
    default void setMaze(Maze maze) {
        mazeProperty().set(maze);
    }
    
    default Maze getMaze() {
        return mazeProperty().get();
    }
    
}