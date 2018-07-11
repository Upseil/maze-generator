package com.upseil.maze.core.test.convert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upseil.maze.core.convert.SimpleMazeFormatter;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.modifier.MazeFiller;

class TestSimpleMazeFormatter {
    
    private static final MazeFiller<Maze> Filler = new MazeFiller<>(CellType.Floor);
    
    private SimpleMazeFormatter formatter;
    private Maze maze;
    
    @BeforeEach
    void initiliaze() {
        formatter = new SimpleMazeFormatter();
        maze = Filler.modify(new GridMaze(2, 2, Direction.fullValues(), Maze.DefaultMapFactory));
    }
    
    @Test
    void testFormatSimpleMaze() {
        maze.setCell(new Cell(0, 0, CellType.Wall));
        
        String expectedString = "F F\nW F";
        assertThat(formatter.convert(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithCustomCellType() {
        maze.setCell(new Cell(0, 0, new CellType("Floor2")));
        
        String expectedString = "F F\nF F";
        assertThat(formatter.convert(maze), is(expectedString));
        
        maze.setCell(new Cell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.convert(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithNullCell() {
        maze.removeCell(0, 0);
        
        String expectedString = "F F\n  F";
        assertThat(formatter.convert(maze), is(expectedString));
        
        maze.setCell(new Cell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.convert(maze), is(expectedString));
    }
    
}
