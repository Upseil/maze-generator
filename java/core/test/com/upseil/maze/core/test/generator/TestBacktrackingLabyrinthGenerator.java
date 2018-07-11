package com.upseil.maze.core.test.generator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Random;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.configuration.LabyrinthConfiguration.Border;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.generator.BacktrackingLabyrinthGenerator;

class TestBacktrackingLabyrinthGenerator {
    
    @Test
    void testGenerate() {
        long seed = -8771878613963128241L;
        BacktrackingLabyrinthGenerator<Maze> generator = new BacktrackingLabyrinthGenerator<>(new Random(seed), MazeFactory.DefaultGridMaze);
        Maze maze = generator.generate(5);
        for (Cell cell : maze) {
            CellType expectedType = cell.getX() % 2 == 0 || cell.getY() == 0 ? CellType.Floor : CellType.Wall;
            assertThat(cell.toString(), cell.getType(), is(expectedType));
        }
    }
    
    @Test
    void testGenerateSolidBorder() {
        int size = 25;
        BacktrackingLabyrinthGenerator<Maze> generator = new BacktrackingLabyrinthGenerator<>(new Random(), MazeFactory.DefaultGridMaze);
        generator.getConfiguration().setBorder(Border.Solid);
        
        Maze maze = generator.generate(size);
        int maxX = maze.getWidth() - 1;
        int maxY = maze.getHeight() - 1;
        for (int x = 0; x < maze.getWidth(); x++) {
            Cell cell = maze.getCell(x, 0);
            assertThat(cell.toString(), cell.getType(), is(CellType.Wall));
            
            cell = maze.getCell(x, maxY);
            assertThat(cell.toString(), cell.getType(), is(CellType.Wall));
        }
        for (int y = 0; y < maze.getWidth(); y++) {
            Cell cell = maze.getCell(0, y);
            assertThat(cell.toString(), cell.getType(), is(CellType.Wall));
            
            cell = maze.getCell(maxX, y);
            assertThat(cell.toString(), cell.getType(), is(CellType.Wall));
        }
    }
    
    @Test
    void testGenerateNoneBorder() {
        int size = 25;
        BacktrackingLabyrinthGenerator<Maze> generator = new BacktrackingLabyrinthGenerator<>(new Random(), MazeFactory.DefaultGridMaze);
        generator.getConfiguration().setBorder(Border.None);
        
        Maze maze = generator.generate(size);
        int maxX = maze.getWidth() - 1;
        int maxY = maze.getHeight() - 1;
        
        boolean foundFloor = false;
        for (int x = 0; x < maze.getWidth(); x++) {
            if (maze.getCell(x, maxY).getType().equals(CellType.Floor)) {
                foundFloor = true;
                break;
            }
        }
        assertThat("No floor cell found in top border", foundFloor);
        
        foundFloor = false;
        for (int x = 0; x < maze.getWidth(); x++) {
            if (maze.getCell(x, 0).getType().equals(CellType.Floor)) {
                foundFloor = true;
                break;
            }
        }
        assertThat("No floor cell found in bottom border", foundFloor);

        foundFloor = false;
        for (int y = 0; y < maze.getWidth(); y++) {
            if (maze.getCell(0, y).getType().equals(CellType.Floor)) {
                foundFloor = true;
                break;
            }
        }
        assertThat("No floor cell found in left border", foundFloor);

        foundFloor = false;
        for (int y = 0; y < maze.getWidth(); y++) {
            if (maze.getCell(maxX, y).getType().equals(CellType.Floor)) {
                foundFloor = true;
                break;
            }
        }
        assertThat("No floor cell found in right border", foundFloor);
    }
    
}
