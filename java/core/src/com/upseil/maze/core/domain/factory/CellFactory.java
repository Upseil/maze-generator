package com.upseil.maze.core.domain.factory;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.SimpleCell;

@FunctionalInterface
public interface CellFactory<C extends Cell> {
    
    CellFactory<Cell> Default = (x, y, t) -> new SimpleCell(x, y, t);

    C create(int x, int y, CellType type);
    
}
