package com.upseil.maze.core.test.convert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upseil.maze.core.convert.SimpleMazeFormatter;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.GenericMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.SimpleCell;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.modifier.MazeFiller;

class TestSimpleMazeFormatter {
    
    private static final MazeFiller<Maze<Cell>, Cell> Filler = new MazeFiller<>(CellFactory.Default, CellType.Floor);
    
    private SimpleMazeFormatter formatter;
    private Maze<Cell> maze;
    
    @BeforeEach
    void initiliaze() {
        formatter = new SimpleMazeFormatter();
        maze = Filler.modify(new GenericMaze<>(2, 2));
    }
    
    @Test
    void testFormatSimpleMaze() {
        maze.setCell(new SimpleCell(0, 0, CellType.Wall));
        
        String expectedString = "F F\nW F";
        assertThat(formatter.convert(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithCustomCellType() {
        maze.setCell(new SimpleCell(0, 0, new CellType("Floor2")));
        
        String expectedString = "F F\nF F";
        assertThat(formatter.convert(maze), is(expectedString));
        
        maze.setCell(new SimpleCell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.convert(maze), is(expectedString));
    }
    
    @Test
    void testFormatMazeWithNullCell() {
        maze.removeCell(0, 0);
        
        String expectedString = "F F\n  F";
        assertThat(formatter.convert(maze), is(expectedString));
        
        maze.setCell(new SimpleCell(0, 0, new CellType("Test")));
        expectedString = "F F\nT F";
        assertThat(formatter.convert(maze), is(expectedString));
    }
    
}
