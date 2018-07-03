package com.upseil.maze.core.test;

import java.util.Random;

import com.upseil.maze.core.convert.SimpleMazeFormatter;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.domain.factory.MazeFactory;
import com.upseil.maze.core.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.core.generator.MazeGenerator;

public class Playground {
    
    public static void main(String[] args) {
        MazeGenerator<Maze<Cell>, ?> generator = new BacktrackingLabyrinthGenerator<>(new Random(), MazeFactory.Default, CellFactory.Default);
        SimpleMazeFormatter formatter = new SimpleMazeFormatter(" ", ' ');
        
        for (int i = 0; i < 10; i++) {
            Maze<Cell> maze = generator.generate(20, 10);
            System.out.println(formatter.convert(maze));
            System.out.println();
        }
    }
    
}
