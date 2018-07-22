package com.upseil.maze.core.modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.upseil.maze.core.configuration.PassageBreakerConfiguration;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Cells;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.Point;
import com.upseil.maze.core.solver.AStarMazeSolver;
import com.upseil.maze.core.solver.MazeSolver;

public class PassageBreaker<M extends Maze> extends AbstractRandomizedMazeModifier<M, PassageBreakerConfiguration> {
    
    private static final Logger logger = Logger.getLogger(PassageBreaker.class.getName());
    
    private final MazeSolver solver;

    public PassageBreaker(Random random) {
        super(random);
        solver = new AStarMazeSolver();
        setConfiguration(new PassageBreakerConfiguration());
    }

    @Override
    public M modify(M maze) {
        PassageBreakerConfiguration configuration = getConfiguration();
        Predicate<Cell> isWalkable = Cells.ofType(configuration.getWalkableTypes());
        List<Candidate> candidates = findCandidates(maze, configuration.getSearchType(), isWalkable);
        int passageAmount = configuration.getAmount();
        int minimumDistance = configuration.getMinimumShortcutDistance();
        CellType passageType = configuration.getPassageType();
        
        int count = 0;
        int index = 0;
        List<Direction> path = new ArrayList<>();
        while ((passageAmount < 0 || count < passageAmount) && index < candidates.size()) {
            Candidate candidate = candidates.get(index);
            solver.solve(maze, candidate.a, candidate.b, isWalkable, path);
            if (path.size() >= minimumDistance) {
                maze.setCell(new Cell(candidate.point.getX(), candidate.point.getY(), passageType));
                count++;
            }
            index++;
        }
        
        if (passageAmount >= 0 && count < passageAmount) {
            logger.log(Level.WARNING, "Only " + count + " of " + passageAmount + " passages matching the configurations requirements could be created");
        }
        
        return maze;
    }

    private List<Candidate> findCandidates(M maze, CellType searchType, Predicate<Cell> isWalkable) {
        List<Candidate> candidates = new ArrayList<>();
        Map<Direction, Cell> neighbours = new HashMap<>();
        maze.forEach(Cells.ofType(searchType), cell -> {
            neighbours.clear();
            maze.getNeighbours(cell, isWalkable, neighbours);
            if (neighbours.size() == 2) {
                Iterator<Direction> iterator = neighbours.keySet().iterator();
                Direction a = iterator.next();
                Direction b = iterator.next();
                if (a.getOpposite() == b) {
                    candidates.add(new Candidate(new Point(cell.getX(), cell.getY()), neighbours.get(a), neighbours.get(b)));
                }
            }
        });
        Collections.shuffle(candidates, getRandom());
        return candidates;
    }
    
    private static class Candidate {
        
        private final Point point;
        private final Cell a;
        private final Cell b;
        
        public Candidate(Point point, Cell a, Cell b) {
            this.point = point;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public String toString() {
            return "C[" + point + ", " + a + " | " + b + "]";
        }
        
    }
    
}
