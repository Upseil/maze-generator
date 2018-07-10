package com.upseil.maze.core.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import com.upseil.maze.core.domain.Point;
import com.upseil.maze.core.domain.factory.CellFactory;
import com.upseil.maze.core.domain.factory.MazeFactory;
import com.upseil.maze.core.modifier.MazeFiller;

public class BacktrackingLabyrinthGenerator<M extends Maze<C>, C extends Cell> extends AbstractLabyrinthGenerator<M, C> {
    
    private static final Logger logger = Logger.getLogger(BacktrackingLabyrinthGenerator.class.getName());
    
    private final MazeFiller<M, C> mazeFiller;
    private final Collection<Direction> directions;
    
    public BacktrackingLabyrinthGenerator(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
        super(random, mazeFactory, cellFactory);
        mazeFiller = new MazeFiller<>(cellFactory, CellType.Wall);
        directions = Arrays.asList(Direction.North, Direction.East, Direction.South, Direction.West);
        setConfiguration(new LabyrinthConfiguration());
    }

    @Override
    public M generate(int width, int height) {
        M maze = getMazeFactory().create(width, height);
        
        List<Visit> visits = new ArrayList<>((int) (width * height * 0.25));
        Visit startVisit = new Visit(getStart(width, height));
        startVisit.setVisited(true);
        setCell(maze, startVisit.getPoint(), CellType.Floor);
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
                    setCell(maze, step, CellType.Floor);
                    visits.add(new Visit(next));
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
            setCell(maze, visit.getPoint(), CellType.Floor);
            visit.setVisited(true);
        }
    }

    private void setCell(M maze, Point point, CellType type) {
        int x = point.getX();
        int y = point.getY();
        maze.setCell(getCellFactory().create(x, y, type));
    }
    
    private class Visit {
        
        private final Point point;
        private boolean visited;
        
        private final List<Direction> directions;
        private int directionsIndex;
        
        public Visit(Point point) {
            this.point = point;
            visited = false;
            
            directions = new ArrayList<>(BacktrackingLabyrinthGenerator.this.directions);
            Collections.shuffle(directions, getRandom());
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
