package com.upseil.maze.core.test.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Cells;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.modifier.MazeFiller;

class TestSimpleMaze {
    
    private static final MazeFiller<Maze> Filler = new MazeFiller<>(CellType.Floor);
    
    private Maze maze;
    
    @BeforeEach
    void initializeMaze() {
        maze = Filler.modify(new GridMaze(2, 2, Direction.fullValues(), Maze.DefaultMapFactory));
        maze.setCell(new Cell(0, 0, CellType.Wall));
    }
    
    @Test
    void testGetCell() {
        Cell expectedCell = new Cell(0, 0, CellType.Wall);
        assertThat(maze.getCell(0, 0), is(expectedCell));
        
        expectedCell = new Cell(0, 1, CellType.Floor);
        assertThat(maze.getCell(0, 1), is(expectedCell));
    }
    
    @Test
    void testGetCellOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(maze.getWidth(), 0));
        assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(-1, 0));
    }
    
    @Test
    void testIsInBounds() {
        assertThat(maze.isInBounds(0, 1), is(true));
        assertThat(maze.isInBounds(-1, 1), is(false));
    }
    
    @Test
    void testGetNeighbour() {
        Cell expectedCell = new Cell(0, 1, CellType.Floor);
        assertThat(maze.getNeighbour(0, 0, Direction.North), is(expectedCell));
        
        expectedCell = new Cell(1, 0, CellType.Floor);
        assertThat(maze.getNeighbour(0, 0, Direction.East), is(expectedCell));
        
        expectedCell = new Cell(1, 1, CellType.Floor);
        assertThat(maze.getNeighbour(0, 0, Direction.NorthEast), is(expectedCell));
        
        assertThat(maze.getNeighbour(0, 0, Direction.South), is(nullValue()));
    }
    
    @Test
    void testGetNeighbours() {
        Map<Direction, Cell> expectedNeighbours = new HashMap<>();
        expectedNeighbours.put(Direction.North,     new Cell(0, 1, CellType.Floor));
        expectedNeighbours.put(Direction.East,      new Cell(1, 0, CellType.Floor));
        expectedNeighbours.put(Direction.NorthEast, new Cell(1, 1, CellType.Floor));
        assertThat(maze.getNeighbours(0, 0, Direction.allValues()), is(expectedNeighbours));
        
        Map<Direction, Cell> neighbours = new HashMap<>();
        Map<Direction, Cell> returnedNeighbours = maze.getNeighbours(0, 0, Direction.allValues(), neighbours);
        assertThat(returnedNeighbours, is(sameInstance(neighbours)));
        assertThat(neighbours, is(expectedNeighbours));
        
        expectedNeighbours.clear();
        expectedNeighbours.put(Direction.SouthEast, new Cell(1, 0, CellType.Floor));
        expectedNeighbours.put(Direction.East,      new Cell(1, 1, CellType.Floor));
        assertThat(maze.getNeighbours(0, 1, Direction.allValues(), Cells.ofType(CellType.Floor)), is(expectedNeighbours));
        assertThat(maze.getNeighbours(0, 1, Arrays.asList(Direction.East, Direction.SouthEast)), is(expectedNeighbours));

        assertThrows(IndexOutOfBoundsException.class, () -> maze.getNeighbours(-1, 0));
    }
    
    @Test
    void testIterator() {
        maze.removeCell(0, 0);
        
        Set<Cell> expectedCells = new HashSet<>();
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell != null) {
                    expectedCells.add(cell);
                }
            }
        }
        Set<Cell> cells = StreamSupport.stream(maze.spliterator(), false).collect(Collectors.toSet());
        assertThat(cells, is(expectedCells));
        
        Iterator<Cell> iterator = maze.iterator();
        while (iterator.hasNext()) iterator.next();
        assertThrows(IndexOutOfBoundsException.class, () -> iterator.next());
        
        assertThrows(UnsupportedOperationException.class, () -> maze.iterator().remove());
    }
    
}
