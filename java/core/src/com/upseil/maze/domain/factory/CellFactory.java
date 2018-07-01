package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.SimpleCell;
import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;

@FunctionalInterface
public interface CellFactory<C extends Cell> {
    
    CellFactory<Cell> Default = (x, y, t) -> new SimpleCell(x, y, t);

    C create(int x, int y, CellType type);
    
}
