package com.upseil.maze.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.upseil.maze.domain.CellType;
import com.upseil.maze.domain.Direction;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;

public class BacktrackingLabyrinthGenerator extends AbstractLabyrinthGenerator {
    
    private final Collection<Direction> directions;
    
    public BacktrackingLabyrinthGenerator(Random random, CellFactory cellFactory) {
        super(random, cellFactory);
        directions = Arrays.asList(Direction.North, Direction.East, Direction.South, Direction.West);
    }

    @Override
    public Maze generate(int width, int height) {
        Maze maze = new Maze(width, height);
        
        List<Visit> visits = new ArrayList<>((int) (width * height * 0.25));
        visits.add(new Visit(randomInt(width), randomInt(height)));
        
        while (!visits.isEmpty()) {
            Visit visit = visits.get(visits.size() - 1);
            visitCell(maze, visit);
            if (visit.hasNextDirection()) {
                Direction direction = visit.nextDirection();
                
                int stepX = visit.getX() + direction.getDeltaX();
                int stepY = visit.getY() + direction.getDeltaY();
                
                int nextX = stepX + direction.getDeltaX();
                int nextY = stepY + direction.getDeltaY();
                
                if (maze.isInBounds(nextX, nextY) && maze.getCell(nextX, nextY) == null) {
                    setToFloor(maze, stepX, stepY);
                    visits.add(new Visit(nextX, nextY));
                }
            } else {
                visits.remove(visits.size() - 1);
            }
        }
        
        fillRemainingCells(maze, CellType.Wall);
        return maze;
    }

    private void visitCell(Maze maze, Visit visit) {
        int x = visit.getX();
        int y = visit.getY();
        if (maze.getCell(x, y) == null) {
            setToFloor(maze, x, y);
        }
    }

    private void setToFloor(Maze maze, int x, int y) {
        maze.setCell(x, y, getCellFactory().create(x, y, CellType.Floor));
    }
    
    private class Visit {
        
        private final int x;
        private final int y;
        private final Iterator<Direction> directionsIterator;
        
        public Visit(int x, int y) {
            this.x = x;
            this.y = y;
            
            List<Direction> directions = new ArrayList<>(BacktrackingLabyrinthGenerator.this.directions);
            Collections.shuffle(directions, getRandom());
            directionsIterator = directions.iterator();
        }
        
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean hasNextDirection() {
            return directionsIterator.hasNext();
        }
        
        public Direction nextDirection() {
            return directionsIterator.next();
        }
        
    }
    
}
