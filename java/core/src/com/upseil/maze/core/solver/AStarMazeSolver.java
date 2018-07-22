package com.upseil.maze.core.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.Point;

public class AStarMazeSolver implements MazeSolver {
    
    @Override
    public List<Direction> solve(Maze maze, int fromX, int fromY, int toX, int toY, Predicate<Cell> isWalkable, List<Direction> result) {
        Point from = new Point(fromX, fromY);
        Point to = new Point(toX, toY);
        
        List<Node> openNodes = new ArrayList<>();
        Map<Direction, Cell> neighbours = new HashMap<>();
        boolean[][] visited = new boolean[maze.getWidth()][maze.getHeight()];
        
        Node finalNode = null;
        openNodes.add(new Node(from, 0, from.distance(to), null, null));
        do {
            Node node = openNodes.remove(openNodes.size() - 1);
            if (node.point.equals(to)) {
                finalNode = node;
                break;
            }
            
            neighbours.clear();
            maze.getNeighbours(node.point.getX(), node.point.getY(), isWalkable, neighbours);
            visited[node.point.getX()][node.point.getY()] = true;
            for (Direction direction : neighbours.keySet()) {
                Cell cell = neighbours.get(direction);
                if (!visited[cell.getX()][cell.getY()]) {
                    Point successorPoint = new Point(cell.getX(), cell.getY());
                    int successorIndex = indexOf(openNodes, successorPoint);
                    if (successorIndex == -1) {
                        openNodes.add(new Node(successorPoint, node.cost + 1, successorPoint.distance(to), node, direction));
                    } else if (node.cost + 1 < openNodes.get(successorIndex).cost) {
                        openNodes.set(successorIndex, new Node(successorPoint, node.cost + 1, successorPoint.distance(to), node, direction));
                    }
                }
            }
            openNodes.sort(null);
        } while (!openNodes.isEmpty());
        
        return finalNode == null ? result : buildPath(finalNode, result);
    }
    
    private int indexOf(List<Node> openNodes, Point point) {
        for (int index = 0; index < openNodes.size(); index++) {
            if (openNodes.get(index).point.equals(point)) {
                return index;
            }
        }
        return -1;
    }

    private List<Direction> buildPath(Node node, List<Direction> result) {
        if (result == null) {
            result = new ArrayList<>();
        } else {
            result.clear();
        }
        
        Node currentNode = node;
        while (currentNode.predecessor != null) {
            result.add(currentNode.direction);
            currentNode = currentNode.predecessor;
        }
        Collections.reverse(result);
        return result;
    }

    private static class Node implements Comparable<Node> {
        
        private final Point point;
        private final int cost;
        private final double distance;
        private final Node predecessor;
        private final Direction direction;
        
        public Node(Point point, int cost, double distance, Node predecessor, Direction direction) {
            this.point = point;
            this.cost = cost;
            this.distance = distance;
            this.predecessor = predecessor;
            this.direction = direction;
        }
        
        @Override
        public int compareTo(Node n) {
            return Double.compare(cost + distance, n.cost + n.distance) * -1;
        }
        
    }
    
}
