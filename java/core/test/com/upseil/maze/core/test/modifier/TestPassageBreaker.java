package com.upseil.maze.core.test.modifier;

import static com.upseil.maze.core.domain.CellType.Floor;
import static com.upseil.maze.core.domain.CellType.Wall;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.configuration.PassageBreakerConfiguration;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Cells;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.GridMaze;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.core.modifier.MazeModifier;
import com.upseil.maze.core.modifier.PassageBreaker;
import com.upseil.maze.core.solver.AStarMazeSolver;
import com.upseil.maze.core.solver.MazeSolver;

class TestPassageBreaker {
    
    @Test
    void testPassageBreaker() {
        MazeModifier<Maze> passageBreaker = new PassageBreaker<>(new Random());
        Maze maze = passageBreaker.modify(createStripedMaze());
        assertThat(maze, everyItem(hasProperty("type", equalTo(Floor))));
    }
    
    @Test
    void testMoreThanTwoWalkableNeighbours() {
        MazeModifier<Maze> passageBreaker = new PassageBreaker<>(new Random());
        Maze maze = new GridMaze(3, 3, Direction.fullDirections());
        Iterable<Cell> cells = Arrays.asList(
                new Cell(0, 2, Floor), new Cell(1, 2, Wall),  new Cell(2, 2, Floor),
                new Cell(0, 1, Floor), new Cell(1, 1, Wall),  new Cell(2, 1, Floor),
                new Cell(0, 0, Floor), new Cell(1, 0, Floor), new Cell(2, 0, Floor)
        );
        maze.setCells(cells);
        passageBreaker.modify(maze);
        
        for (Cell cell : maze) {
            CellType expectedType = cell.getX() == 1 && cell.getY() == 1 ? Wall : Floor;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testNonOppositeNeighbours() {
        MazeModifier<Maze> passageBreaker = new PassageBreaker<>(new Random());
        Maze maze = new GridMaze(3, 3, Direction.fullDirections());
        Iterable<Cell> cells = Arrays.asList(
                new Cell(0, 2, Floor), new Cell(1, 2, Floor), new Cell(2, 2, Floor),
                new Cell(0, 1, Floor), new Cell(1, 1, Wall),  new Cell(2, 1, Floor),
                new Cell(0, 0, Wall),  new Cell(1, 0, Floor), new Cell(2, 0, Floor)
        );
        maze.setCells(cells);
        passageBreaker.modify(maze);
        
        for (Cell cell : maze) {
            CellType expectedType = cell.getX() == cell.getY() && cell.getX() != 2 ? Wall : Floor;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testPassageAmount() {
        Random random = new Random(-7847346959264598448L);
        PassageBreaker<Maze> passageBreaker = new PassageBreaker<>(random);
        passageBreaker.getConfiguration().setAmount(8);
        Maze maze = passageBreaker.modify(createStripedMaze());
        
        for (Cell cell : maze) {
            CellType expectedType = cell.getX() % 2 == 1 && cell.getY() == 0 ? Wall : Floor;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testMinimumShortcutDistance() {
        CellType passageType = new CellType("Passage");
        MazeSolver solver = new AStarMazeSolver();
        int minimumDistance = 10;

        PassageBreaker<Maze> passageBreaker = new PassageBreaker<>(new Random());
        PassageBreakerConfiguration configuration = passageBreaker.getConfiguration();
        configuration.setMinimumShortcutDistance(minimumDistance);
        configuration.setPassageType(passageType);
        configuration.setWalkableTypes(new HashSet<>(Arrays.asList(CellType.Floor, passageType)));

        Random generatorRandom = new Random();
        long generatorSeed = generatorRandom.nextLong();
        generatorRandom.setSeed(generatorSeed);
        BacktrackingLabyrinthGenerator<Maze> generator = new BacktrackingLabyrinthGenerator<>(generatorRandom, MazeFactory.DefaultGridMaze);
        Maze maze = passageBreaker.modify(generator.generate(25));
        
        assertThat("No passages were created", maze, hasItem(hasProperty("type", is(passageType))));
        maze.forEach(Cells.ofType(passageType), cell -> {
            List<Cell> neighbours = new ArrayList<>(maze.getNeighbours(cell, Cells.ofType(CellType.Floor)).values());
            assertThat("Expected exactly 2 Floor neighbours - Seed: " + generatorSeed + " - " + cell, neighbours.size(), is(2));
            Cell from = neighbours.get(0);
            Cell to = neighbours.get(1);
            List<Direction> path = solver.solve(maze, from, to, Cells.ofType(CellType.Floor));
            assertThat("Shortcutted distance is to small - Seed: " + generatorSeed + " - From " + from + " to " + to, path.size(), greaterThanOrEqualTo(minimumDistance));
        });
    }

    private Maze createStripedMaze() {
        Maze maze = new GridMaze(5, 5, Direction.fullDirections());
        Iterable<Cell> cells = Arrays.asList(
            new Cell(0, 4, Floor), new Cell(1, 4, Wall), new Cell(2, 4, Floor), new Cell(3, 4, Wall), new Cell(4, 4, Floor),
            new Cell(0, 3, Floor), new Cell(1, 3, Wall), new Cell(2, 3, Floor), new Cell(3, 3, Wall), new Cell(4, 3, Floor),
            new Cell(0, 2, Floor), new Cell(1, 2, Wall), new Cell(2, 2, Floor), new Cell(3, 2, Wall), new Cell(4, 2, Floor),
            new Cell(0, 1, Floor), new Cell(1, 1, Wall), new Cell(2, 1, Floor), new Cell(3, 1, Wall), new Cell(4, 1, Floor),
            new Cell(0, 0, Floor), new Cell(1, 0, Wall), new Cell(2, 0, Floor), new Cell(3, 0, Wall), new Cell(4, 0, Floor)
        );
        maze.setCells(cells);
        return maze;
    }
    
}
