package com.upseil.maze.core.solver;

import java.util.List;
import java.util.function.Predicate;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.Maze;

public interface MazeSolver {

    List<Direction> solve(Maze maze, int fromX, int fromY, int toX, int toY, Predicate<Cell> isWalkable, List<Direction> result);
    
    default List<Direction> solve(Maze maze, int fromX, int fromY, int toX, int toY, Predicate<Cell> isWalkable) {
        return solve(maze, fromX, fromY, toX, toY, isWalkable, null);
    }
    
    default List<Direction> solve(Maze maze, Cell from, Cell to, Predicate<Cell> isWalkable) {
        return solve(maze, from, to, isWalkable, null);
    }
    
    default List<Direction> solve(Maze maze, Cell from, Cell to, Predicate<Cell> isWalkable, List<Direction> result) {
        return solve(maze, from.getX(), from.getY(), to.getX(), to.getY(), isWalkable, result);
    }
    
}
