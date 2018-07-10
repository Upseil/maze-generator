package com.upseil.maze.core.modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.upseil.maze.core.configuration.DeadEndStripperConfiguration;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Cells;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.Point;
import com.upseil.maze.core.domain.factory.CellFactory;

public class DeadEndStripper<M extends Maze<C>, C extends Cell> extends AbstractMazeModifier<M, C, DeadEndStripperConfiguration> {
    
    private static final String MarkerTypeSuffix = "_Marked";
    
    private final Random random;
    private final Collection<Direction> directions;
    
    public DeadEndStripper(Random random, CellFactory<C> cellFactory) {
        super(cellFactory);
        this.random = random;
        directions = Arrays.asList(Direction.North, Direction.East, Direction.South, Direction.West);
        setConfiguration(new DeadEndStripperConfiguration());
    }

    @Override
    public M modify(M maze) {
        Context<C> context = createContext(maze);
        CellType searchType = getConfiguration().getSearchType();
        
        int index = 0;
        int increment = getIncrement();
        while (!context.deadEnds.isEmpty()) {
            DeadEnd deadEnd = context.deadEnds.get(index);
            context.markedCells.add(maze.getCell(deadEnd.getX(), deadEnd.getY()));
            maze.setCell(cellFactory.create(deadEnd.getX(), deadEnd.getY(), context.markerType));
            
            Point next = deadEnd.getNeighbour();
            Map<Direction, C> neighbours = maze.getNeighbours(next.getX(), next.getY(), directions, Cells.ofType(searchType));
            if (neighbours.size() == 1) {
                context.deadEnds.set(index, new DeadEnd(next, neighbours.keySet().iterator().next()));
                index += increment;
            } else {
                context.deadEnds.remove(index);
            }
            
            if (index >= context.deadEnds.size()) {
                index = 0;
            }
        }

        CellType fillType = getConfiguration().getFillType();
        int threshold = (int) (context.markedCells.size() * getConfiguration().getPercentage());
        for (int i = 0; i < context.markedCells.size(); i++) {
            C cell = context.markedCells.get(i);
            if (i < threshold) {
                maze.setCell(cellFactory.create(cell.getX(), cell.getY(), fillType));
            } else {
                maze.setCell(cell);
            }
        }
        
        return maze;
    }

    private int getIncrement() {
        switch (getConfiguration().getStrategy()) {
        case BreadthFirst: return 1;
        case DepthFirst:   return 0;
        }
        throw new IllegalStateException("Unknown strategy " + getConfiguration().getStrategy());
    }

    private Context<C> createContext(M maze) {
        List<DeadEnd> deadEnds = new ArrayList<>();
        Set<String> typeNames = new HashSet<>();
        CellType searchType = getConfiguration().getSearchType();
        int cellsAmount = 0;
        for (C cell : maze) {
            CellType type = cell.getType();
            typeNames.add(type.getName());
            if (type.equals(searchType)) {
                cellsAmount++;
                Map<Direction, C> neighbours = maze.getNeighbours(cell, directions, Cells.ofType(searchType));
                if (neighbours.size() == 1) {
                    deadEnds.add(new DeadEnd(new Point(cell.getX(), cell.getY()), neighbours.keySet().iterator().next()));
                }
            }
        }
        Collections.shuffle(deadEnds, random);
        
        StringBuilder markerTypeName = new StringBuilder(searchType.getName());
        markerTypeName.append(MarkerTypeSuffix);
        if (typeNames.contains(markerTypeName.toString())) {
            markerTypeName.append("_");
            do {
                markerTypeName.append('Z');
            } while (typeNames.contains(markerTypeName.toString()));
        }
        CellType markerType = new CellType(markerTypeName.toString());
        
        return new Context<>(deadEnds, markerType, cellsAmount);
    }
    
    private static class Context<C extends Cell> {
        
        private final List<DeadEnd> deadEnds;
        private final CellType markerType;
        private final List<C> markedCells;
        
        public Context(List<DeadEnd> deadEnds, CellType markerType, int markedCellsCapacity) {
            this.deadEnds = deadEnds;
            this.markerType = markerType;
            markedCells = new ArrayList<>(markedCellsCapacity);
        }
        
    }
    
    private static class DeadEnd {
        
        private final Point point;
        private final Direction direction;
        
        public DeadEnd(Point point, Direction direction) {
            this.point = point;
            this.direction = direction;
        }
        
        public int getX() {
            return point.getX();
        }
        
        public int getY() {
            return point.getY();
        }

        public Point getNeighbour() {
            return point.translate(direction);
        }
        
        @Override
        public String toString() {
            return "D[" + point + ", " + direction + "]";
        }
        
    }
    
}
