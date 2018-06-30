package com.upseil.maze.test.format;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upseil.maze.domain.SimpleCell;
import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.FilledMazeFactory;
import com.upseil.maze.domain.factory.MazeFactory;
import com.upseil.maze.format.SimpleMazeFormatter;

class TestSimpleMazeFormatter {
    
    private static final MazeFactory Factory = new FilledMazeFactory(CellFactory.Default, CellType.Floor);
    
    private SimpleMazeFormatter formatter;
    private Maze maze;
    
    @BeforeEach
    void initiliaze() {
        formatter = new SimpleMazeFormatter();
        maze = Factory.create(2, 2);
    }
    
    @Test
    void testFormatSimpleMaze() {
        maze.setCell(0, 0, new SimpleCell(0, 0, CellType.Wall));
        
        String expectedString = "F F\nW F";
        assertThat(formatter.format(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithCustomCellType() {
        maze.setCell(0, 0, new SimpleCell(0, 0, new CellType("Floor2")));
        
        String expectedString = "F F\nF F";
        assertThat(formatter.format(maze), is(expectedString));
        
        maze.setCell(0, 0, new SimpleCell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.format(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithNullCell() {
        maze.setCell(0, 0, null);
        
        String expectedString = "F F\n  F";
        assertThat(formatter.format(maze), is(expectedString));
        
        maze.setCell(0, 0, new SimpleCell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.format(maze), is(expectedString));
    }
    
}
