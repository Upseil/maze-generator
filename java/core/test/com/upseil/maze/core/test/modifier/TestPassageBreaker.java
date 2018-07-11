package com.upseil.maze.core.test.modifier;

import static com.upseil.maze.core.domain.CellType.Floor;
import static com.upseil.maze.core.domain.CellType.Wall;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.modifier.MazeModifier;
import com.upseil.maze.core.modifier.PassageBreaker;

class TestPassageBreaker {
    
    @Test
    void testPassageBreaker() {
        MazeModifier<Maze> passageBreaker = new PassageBreaker<>(new Random());
        Maze maze = passageBreaker.modify(createStripedMaze());
        assertThat(maze, everyItem(hasProperty("type", equalTo(Floor))));
    }

    private Maze createStripedMaze() {
        Maze maze = new GridMaze(5, 5, Direction.fullDirections());
        Iterable<Cell> cells = Arrays.asList(
            new Cell(0, 4, Floor), new Cell(1, 4, Wall), new Cell(2, 4, Floor), new Cell(3, 4, Wall), new Cell(4, 4, Floor),
            new Cell(0, 3, Floor), new Cell(1, 3, Wall), new Cell(2, 3, Floor), new Cell(3, 3, Wall), new Cell(4, 3, Floor),
            new Cell(0, 2, Floor), new Cell(1, 2, Wall), new Cell(2, 2, Floor), new Cell(3, 2, Wall), new Cell(4, 2, Floor),
            new Cell(0, 1, Floor), new Cell(1, 1, Wall), new Cell(2, 1, Floor), new Cell(3, 1, Wall), new Cell(4, 1, Floor),
            new Cell(0, 0, Floor), new Cell(1, 0, Wall), new Cell(2, 0, Floor), new Cell(3, 0, Wall), new Cell(4, 0, Floor)
        );
        maze.setCells(cells);
        return maze;
    }
    
    // TODO More Tests
    
}
