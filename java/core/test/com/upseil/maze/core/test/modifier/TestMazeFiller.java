package com.upseil.maze.core.test.modifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.GenericMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.modifier.MazeFiller;

class TestMazeFiller {
    
    @Test
    void testMazeFiller() {
        MazeFiller<Maze<Cell>, Cell> mazeFiller = new MazeFiller<>(CellFactory.Default);
        Maze<Cell> maze = mazeFiller.modify(new GenericMaze<>(3, 3));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Wall))));
        
        mazeFiller.getConfiguration().setFillType(CellType.Floor);
        maze = mazeFiller.modify(new GenericMaze<>(3, 3));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Floor))));
    }
    
}
