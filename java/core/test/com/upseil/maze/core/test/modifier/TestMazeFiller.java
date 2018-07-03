package com.upseil.maze.core.test.modifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.SimpleCell;
import com.upseil.maze.core.domain.SimpleMaze;
import com.upseil.maze.core.modifier.MazeFiller;

class TestMazeFiller {
    
    @Test
    void testMazeFiller() {
        MazeFiller<SimpleMaze, SimpleCell> mazeFiller = new MazeFiller<>((x, y, t) -> new SimpleCell(x, y, t), CellType.Floor);
        Maze<SimpleCell> maze = mazeFiller.modify(new SimpleMaze(3, 3));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Floor))));
    }
    
}
