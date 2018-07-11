package com.upseil.maze.core.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.upseil.maze.core.configuration.LabyrinthConfiguration;
import com.upseil.maze.core.configuration.LabyrinthConfiguration.Border;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.domain.Point;
import com.upseil.maze.core.modifier.MazeFiller;

public class BacktrackingLabyrinthGenerator<M extends Maze> extends AbstractLabyrinthGenerator<M> {
    
    private static final Logger logger = Logger.getLogger(BacktrackingLabyrinthGenerator.class.getName());
    
    private final MazeFiller<M> mazeFiller;
    
    public BacktrackingLabyrinthGenerator(Random random, MazeFactory<M> mazeFactory) {
        super(random, mazeFactory);
        mazeFiller = new MazeFiller<>(CellType.Wall);
        setConfiguration(new LabyrinthConfiguration());
    }

    @Override
    public M generate(int width, int height) {
        M maze = getMazeFactory().create(width, height);
        
        List<Visit> visits = new ArrayList<>((int) (width * height * 0.25));
        Visit startVisit = new Visit(getStart(width, height), maze.getWalkableDirections(), getRandom());
        visits.add(startVisit);
        
        while (!visits.isEmpty()) {
            Visit visit = visits.get(visits.size() - 1);
            visitCell(maze, visit);
            if (visit.hasNextDirection()) {
                Direction direction = visit.nextDirection();
                Point step = visit.getPoint().translate(direction);
                Point next = step.translate(direction);
                
                if (maze.isInBounds(next.getX(), next.getY()) &&
                    maze.getCell(next.getX(), next.getY()) == null) {
                    maze.setCell(new Cell(step.getX(), step.getY(), CellType.Floor));
                    visits.add(new Visit(next, maze.getWalkableDirections(), getRandom()));
                }
            } else {
                visits.remove(visits.size() - 1);
            }
        }
             
        return mazeFiller.modify(maze);
    }
    
    private Point getStart(int width, int height) {
        Border border = getConfiguration().getBorder();
        if (border == Border.Indifferent) {
            return randomPoint(width, height);
        }

        final int modCheck = border == Border.None ? 0 : border == Border.Solid ? 1 : -1;
        if (modCheck == -1) {
            logger.log(Level.SEVERE, "Unknown border configuration '" + border + "'");
            return randomPoint(width, height);
        }
        if (width % 2 != 1 || height % 2 != 1) {
            logger.log(Level.WARNING, "Border " + border + " only works with odd bounds");
        }
        return randomPoint(width, height, p -> p.getX() % 2 == modCheck && p.getY() % 2 == modCheck);
    }

    private void visitCell(M maze, Visit visit) {
        if (!visit.isVisited()) {
            Point point = visit.getPoint();
            maze.setCell(new Cell(point.getX(), point.getY(), CellType.Floor));
            visit.setVisited(true);
        }
    }

    private static class Visit {
        
        private final Point point;
        private boolean visited;
        
        private final List<Direction> directions;
        private int directionsIndex;
        
        public Visit(Point point, Iterable<Direction> directions, Random random) {
            this.point = point;
            visited = false;
            
            this.directions = new ArrayList<>();
            for (Direction direction : directions) {
                this.directions.add(direction);
            }
            Collections.shuffle(this.directions, random);
        }
        
        public Point getPoint() {
            return point;
        }

        public boolean hasNextDirection() {
            return directionsIndex < directions.size();
        }
        
        public Direction nextDirection() {
            Direction direction = directions.get(directionsIndex);
            directionsIndex++;
            return direction;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("V[")
                   .append(visited ? "visited " : "not visited ")
                   .append(point).append(", [");
            for (int i = 0; i < directions.size(); i++) {
                Direction direction = directions.get(i);
                if (i != 0) builder.append(", ");
                if (i == directionsIndex) {
                    builder.append("_").append(direction).append("_");
                } else {
                    builder.append(direction);
                }
            }
            builder.append("]]");
            return builder.toString();
        }
        
    }
    
}
