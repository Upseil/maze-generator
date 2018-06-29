package com.upseil.maze.test.format;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upseil.maze.MazeFactory;
import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.format.SimpleMazeFormatter;

class TestSimpleMazeFormatter {
    
    private SimpleMazeFormatter formatter;
    
    @BeforeEach
    void initiliazeFormatter() {
        formatter = new SimpleMazeFormatter();
    }
    
    @Test
    void testFormatSimpleMaze() {
        Maze maze = MazeFactory.createFilled(2, 2, CellType.Floor);
        maze.setCell(0, 0, new Cell(0, 0, CellType.Wall));
        
        String expectedString = "F F\nW F";
        assertThat(formatter.format(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithCustomCellType() {
        Maze maze = MazeFactory.createFilled(2, 2, CellType.Floor);
        maze.setCell(0, 0, new Cell(0, 0, new CellType("Floor2")));
        
        String expectedString = "F F\nF F";
        assertThat(formatter.format(maze), is(expectedString));
        
        maze.setCell(0, 0, new Cell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.format(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithNullCell() {
        Maze maze = MazeFactory.createFilled(2, 2, CellType.Floor);
        maze.setCell(0, 0, null);
        
        String expectedString = "F F\n  F";
        assertThat(formatter.format(maze), is(expectedString));
        
        maze.setCell(0, 0, new Cell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.format(maze), is(expectedString));
    }
    
}
