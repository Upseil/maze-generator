package com.upseil.maze.core.test.solver;

import static com.upseil.maze.core.domain.Direction.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static com.upseil.maze.core.domain.CellType.Floor;
import static com.upseil.maze.core.domain.CellType.Wall;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Cells;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.solver.AStarMazeSolver;

class TestAStarMazeSolver {
    
    private static final AStarMazeSolver solver = new AStarMazeSolver();
    private static final Predicate<Cell> isWalkable = Cells.ofType(CellType.Floor);
    
    @Test
    void testSolve() {
        Maze maze = createExampleMaze();
        List<Direction> expectedPath = Arrays.asList(South, South, East, East, North, North);
        assertThat(solver.solve(maze, 0, 3, 2, 3, isWalkable), is(expectedPath));
    }
    
    @Test
    void testSolveWithMultipleSolutions() {
        Maze maze = createExampleMaze();
        List<Direction> expectedPath1 = Arrays.asList(West, West, North, North);
        List<Direction> expectedPath2 = Arrays.asList(North, North, West, West);
        assertThat(solver.solve(maze, 4, 1, 2, 3, isWalkable), anyOf(equalTo(expectedPath1), equalTo(expectedPath2)));
    }
    
    @Test
    void testSolveZeroLengthPath() {
        Maze maze = createExampleMaze();
        assertThat(solver.solve(maze, 0, 3, 0, 3, isWalkable), is(Collections.emptyList()));
    }
    
    @Test
    void testSolveUnreachableCell() {
        Maze maze = createExampleMaze();
        assertThat(solver.solve(maze, 0, 3, 0, 0, isWalkable), is(nullValue()));
    }

    private Maze createExampleMaze() {
        Maze maze = new GridMaze(5, 5, Direction.fullDirections());
        Iterable<Cell> cells = Arrays.asList(
            new Cell(0, 4, Wall),  new Cell(1, 4, Wall),  new Cell(2, 4, Wall),  new Cell(3, 4, Wall),  new Cell(4, 4, Wall),
            new Cell(0, 3, Floor), new Cell(1, 3, Wall),  new Cell(2, 3, Floor), new Cell(3, 3, Floor), new Cell(4, 3, Floor),
            new Cell(0, 2, Floor), new Cell(1, 2, Wall),  new Cell(2, 2, Floor),  new Cell(3, 2, Wall), new Cell(4, 2, Floor),
            new Cell(0, 1, Floor), new Cell(1, 1, Floor), new Cell(2, 1, Floor), new Cell(3, 1, Floor), new Cell(4, 1, Floor),
            new Cell(0, 0, Wall),  new Cell(1, 0, Wall),  new Cell(2, 0, Wall),  new Cell(3, 0, Wall),  new Cell(4, 0, Wall)
        );
        maze.setCells(cells);
        return maze;
    }
    
}
