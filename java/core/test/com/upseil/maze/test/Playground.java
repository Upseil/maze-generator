package com.upseil.maze.test;

import java.util.Random;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.format.MazeFormatter;
import com.upseil.maze.format.SimpleMazeFormatter;
import com.upseil.maze.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.generator.MazeGenerator;

public class Playground {
    
    public static void main(String[] args) {
        MazeGenerator generator = new BacktrackingLabyrinthGenerator(new Random(0), (x, y, t) -> new Cell(x, y, t));
        MazeFormatter formatter = new SimpleMazeFormatter(" ", ' ');
        
        for (int i = 0; i < 10; i++) {
            Maze maze = generator.generate(20, 10);
            System.out.println(formatter.format(maze));
            System.out.println();
        }
    }
    
}
