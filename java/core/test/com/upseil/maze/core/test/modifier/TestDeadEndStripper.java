package com.upseil.maze.core.test.modifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Random;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.configuration.DeadEndStripperConfiguration;
import com.upseil.maze.core.configuration.DeadEndStripperConfiguration.Strategy;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.core.modifier.DeadEndStripper;

class TestDeadEndStripper {
    
    private static final int MazeSize = 20;
    
    @Test
    void testCompleteDeadEndStripping() {
        BacktrackingLabyrinthGenerator<Maze> generator = new BacktrackingLabyrinthGenerator<>(new Random(), MazeFactory.DefaultGridMaze);
        DeadEndStripper<Maze> deadEndStripper = new DeadEndStripper<>(new Random());
        
        Maze maze = deadEndStripper.modify(generator.generate(MazeSize));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Wall))));
        
        deadEndStripper.getConfiguration().setStrategy(Strategy.DepthFirst);
        maze = deadEndStripper.modify(generator.generate(MazeSize));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Wall))));
    }
    
    @Test
    void testZeroDeadEndStripping() {
        DeadEndStripper<Maze> deadEndStripper = new DeadEndStripper<>(new Random());
        deadEndStripper.getConfiguration().setPercentage(0);
        Maze maze = deadEndStripper.modify(createCrossMaze(MazeSize));
        
        int center = MazeSize / 2;
        for (Cell cell : maze) {
            int x = cell.getX();
            int y = cell.getY();
            CellType expectedType = x == center || y == center ? CellType.Floor : CellType.Wall;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testPartialBreadthFirstDeadEndStripping() {
        DeadEndStripper<Maze> deadEndStripper = new DeadEndStripper<>(new Random(0));
        DeadEndStripperConfiguration configuration = deadEndStripper.getConfiguration();
        configuration.setStrategy(Strategy.BreadthFirst);
        configuration.setPercentage(4.0 / (MazeSize*2 - 1));
        Maze maze = deadEndStripper.modify(createCrossMaze(MazeSize));
        
        int center = MazeSize / 2;
        for (Cell cell : maze) {
            int x = cell.getX();
            int y = cell.getY();
            
            CellType expectedType;
            if ((x == center || y == center) && x != 0 && y != 0 &&
                 x != MazeSize - 1 && y != MazeSize - 1) {
                expectedType = CellType.Floor;
            } else {
                expectedType = CellType.Wall;
            }
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testPartialDepthFirstDeadEndStripping() {
        DeadEndStripper<Maze> deadEndStripper = new DeadEndStripper<>(new Random(0));
        DeadEndStripperConfiguration configuration = deadEndStripper.getConfiguration();
        configuration.setStrategy(Strategy.DepthFirst);
        configuration.setPercentage(0.5);
        Maze maze = deadEndStripper.modify(createCrossMaze(MazeSize));
        
        int center = MazeSize / 2;
        for (Cell cell : maze) {
            int y = cell.getY();
            CellType expectedType = y == center ? CellType.Floor : CellType.Wall;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testDeadEndStrippingWithWeirdCellTypes() {
        CellType crossType = CellType.Floor;
        CellType fillType = new CellType("Floor_Marked");
        DeadEndStripper<Maze> deadEndStripper = new DeadEndStripper<>(new Random());
        deadEndStripper.getConfiguration().setSearchType(crossType);
        deadEndStripper.getConfiguration().setFillType(fillType);
        
        Maze maze = deadEndStripper.modify(createCrossMaze(MazeSize, crossType, fillType));
        assertThat(maze, everyItem(hasProperty("type", equalTo(fillType))));
    }
    
    @Test
    void testCellIdentityPreservation() {
        DeadEndStripper<Maze> deadEndStripper = new DeadEndStripper<>(new Random());
        deadEndStripper.getConfiguration().setPercentage(0);
        
        Maze maze = createCrossMaze(MazeSize);
        int center = MazeSize / 2;
        Cell cell = maze.getCell(center, center);
        deadEndStripper.modify(maze);
        assertThat(maze.getCell(center, center), sameInstance(cell));
    }

    private Maze createCrossMaze(int size) {
        return createCrossMaze(size, CellType.Floor, CellType.Wall);
    }

    private Maze createCrossMaze(int size, CellType crossType, CellType fillType) {
        int center = size / 2;
        Maze maze = new GridMaze(size, size, Direction.fullDirections());
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                CellType type = x == center || y == center ? crossType : fillType;
                maze.setCell(new Cell(x, y, type));
            }
        }
        return maze;
    }
    
}
