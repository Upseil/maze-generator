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
import com.upseil.maze.core.domain.GenericMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.SimpleCell;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.domain.factory.MazeFactory;
import com.upseil.maze.core.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.core.modifier.DeadEndStripper;

class TestDeadEndStripper {
    
    private static final int MazeSize = 20;
    
    @Test
    void testCompleteDeadEndStripping() {
        BacktrackingLabyrinthGenerator<Maze<Cell>, Cell> generator = new BacktrackingLabyrinthGenerator<>(new Random(), MazeFactory.Default, CellFactory.Default);
        DeadEndStripper<Maze<Cell>, Cell> deadEndStripper = new DeadEndStripper<>(new Random(), CellFactory.Default);
        
        Maze<Cell> maze = deadEndStripper.modify(generator.generate(MazeSize));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Wall))));
        
        deadEndStripper.getConfiguration().setStrategy(Strategy.DepthFirst);
        maze = deadEndStripper.modify(generator.generate(MazeSize));
        assertThat(maze, everyItem(hasProperty("type", equalTo(CellType.Wall))));
    }
    
    @Test
    void testZeroDeadEndStripping() {
        DeadEndStripper<Maze<Cell>, Cell> deadEndStripper = new DeadEndStripper<>(new Random(), CellFactory.Default);
        deadEndStripper.getConfiguration().setPercentage(0);
        Maze<Cell> maze = deadEndStripper.modify(createCrossMaze(MazeSize));
        
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
        DeadEndStripper<Maze<Cell>, Cell> deadEndStripper = new DeadEndStripper<>(new Random(0), CellFactory.Default);
        DeadEndStripperConfiguration configuration = deadEndStripper.getConfiguration();
        configuration.setStrategy(Strategy.BreadthFirst);
        configuration.setPercentage(4.0 / (MazeSize*2 - 1));
        Maze<Cell> maze = deadEndStripper.modify(createCrossMaze(MazeSize));
        
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
        DeadEndStripper<Maze<Cell>, Cell> deadEndStripper = new DeadEndStripper<>(new Random(0), CellFactory.Default);
        DeadEndStripperConfiguration configuration = deadEndStripper.getConfiguration();
        configuration.setStrategy(Strategy.DepthFirst);
        configuration.setPercentage(0.5);
        Maze<Cell> maze = deadEndStripper.modify(createCrossMaze(MazeSize));
        
        int center = MazeSize / 2;
        for (Cell cell : maze) {
            int y = cell.getY();
            CellType expectedType = y == center ? CellType.Floor : CellType.Wall;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testDeadEndStrippingWithWeirdCellType() {
        CellType weirdType = new CellType("Floor_Marked_ZZ");
        DeadEndStripper<Maze<Cell>, Cell> deadEndStripper = new DeadEndStripper<>(new Random(), CellFactory.Default);
        deadEndStripper.getConfiguration().setFillType(weirdType);
        
        Maze<Cell> maze = deadEndStripper.modify(createCrossMaze(MazeSize, CellType.Floor, weirdType));
        assertThat(maze, everyItem(hasProperty("type", equalTo(weirdType))));
    }
    
    @Test
    void testCellIdentityPreservation() {
        DeadEndStripper<Maze<Cell>, Cell> deadEndStripper = new DeadEndStripper<>(new Random(), CellFactory.Default);
        deadEndStripper.getConfiguration().setPercentage(0);
        
        Maze<Cell> maze = createCrossMaze(MazeSize);
        int center = MazeSize / 2;
        Cell cell = maze.getCell(center, center);
        deadEndStripper.modify(maze);
        assertThat(maze.getCell(center, center), sameInstance(cell));
    }

    private Maze<Cell> createCrossMaze(int size) {
        return createCrossMaze(size, CellType.Floor, CellType.Wall);
    }

    private Maze<Cell> createCrossMaze(int size, CellType crossType, CellType fillType) {
        int center = size / 2;
        Maze<Cell> maze = new GenericMaze<>(size, size);
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                CellType type = x == center || y == center ? crossType : fillType;
                maze.setCell(new SimpleCell(x, y, type));
            }
        }
        return maze;
    }
    
}
