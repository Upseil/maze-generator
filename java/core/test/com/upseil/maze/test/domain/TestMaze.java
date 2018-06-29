package com.upseil.maze.test.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upseil.maze.MazeFactory;
import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Direction;
import com.upseil.maze.domain.Maze;

public class TestMaze {
    
    private Maze maze;
    
    @BeforeEach
    public void initializeMaze() {
        maze = MazeFactory.createFilled(2, 2, CellType.Floor);
        maze.setCell(0, 0, new Cell(0, 0, CellType.Wall));
    }
    
    @Test
    public void testGetCell() {
        Cell expectedCell = new Cell(0, 0, CellType.Wall);
        assertThat(maze.getCell(0, 0), is(expectedCell));
        
        expectedCell = new Cell(0, 1, CellType.Floor);
        assertThat(maze.getCell(0, 1), is(expectedCell));
    }
    
    @Test
    public void testGetCellOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(maze.getWidth(), 0));
        assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(-1, 0));
    }
    
    @Test
    public void testIsInBounds() {
        assertThat(maze.isInBounds(0, 1), is(true));
        assertThat(maze.isInBounds(-1, 1), is(false));
    }
    
    @Test
    public void testGetNeighbour() {
        Cell expectedCell = new Cell(0, 1, CellType.Floor);
        assertThat(maze.getNeighbour(0, 0, Direction.North), is(expectedCell));
        
        expectedCell = new Cell(1, 0, CellType.Floor);
        assertThat(maze.getNeighbour(0, 0, Direction.East), is(expectedCell));
        
        expectedCell = new Cell(1, 1, CellType.Floor);
        assertThat(maze.getNeighbour(0, 0, Direction.NorthEast), is(expectedCell));
        
        assertThat(maze.getNeighbour(0, 0, Direction.South), is(nullValue()));
    }
    
    @Test
    public void testGetNeighbours() {
        Map<Direction, Cell> expectedNeighbours = new HashMap<>();
        expectedNeighbours.put(Direction.North,     new Cell(0, 1, CellType.Floor));
        expectedNeighbours.put(Direction.East,      new Cell(1, 0, CellType.Floor));
        expectedNeighbours.put(Direction.NorthEast, new Cell(1, 1, CellType.Floor));
        assertThat(maze.getNeighbours(0, 0), is(expectedNeighbours));
        
        Map<Direction, Cell> neighbours = new HashMap<>();
        Map<Direction, Cell> returnedNeighbours = maze.getNeighbours(0, 0, neighbours);
        assertThat(returnedNeighbours, is(sameInstance(neighbours)));
        assertThat(neighbours, is(expectedNeighbours));
        
        expectedNeighbours.clear();
        expectedNeighbours.put(Direction.SouthEast, new Cell(1, 0, CellType.Floor));
        expectedNeighbours.put(Direction.East,      new Cell(1, 1, CellType.Floor));
        assertThat(maze.getNeighbours(0, 1, c -> c.getType() == CellType.Floor), is(expectedNeighbours));
        assertThat(maze.getNeighbours(0, 1, Arrays.asList(Direction.East, Direction.SouthEast)), is(expectedNeighbours));

        assertThrows(IndexOutOfBoundsException.class, () -> maze.getNeighbours(-1, 0));
    }
    
}
