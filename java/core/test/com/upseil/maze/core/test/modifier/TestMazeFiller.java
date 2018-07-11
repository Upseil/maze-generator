package com.upseil.maze.core.test.modifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.modifier.MazeFiller;

class TestMazeFiller {
    
    @Test
    void testMazeFiller() {
        MazeFiller<Maze> mazeFiller = new MazeFiller<>();
        Maze maze = mazeFiller.modify(new GridMaze(3, 3, Direction.fullValues(), Maze.DefaultMapFactory));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Wall))));
        
        mazeFiller.getConfiguration().setFillType(CellType.Floor);
        maze = mazeFiller.modify(new GridMaze(3, 3, Direction.fullValues(), Maze.DefaultMapFactory));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Floor))));
    }
    
}
