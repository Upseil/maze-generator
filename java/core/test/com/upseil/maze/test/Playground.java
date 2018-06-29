package com.upseil.maze.test;

import com.upseil.maze.MazeFactory;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.format.MazeFormatter;
import com.upseil.maze.format.SimpleMazeFormatter;

public class Playground {
    
    public static void main(String[] args) {
        Maze maze = MazeFactory.createFilled(25, 25, CellType.Floor);
        MazeFormatter formatter = new SimpleMazeFormatter(" ", ' ');
        System.out.println(formatter.format(maze));
    }
    
}
