package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.SimpleCell;
import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;

public interface CellFactory {
    
    CellFactory Default = (x, y, t) -> new SimpleCell(x, y, t);

    Cell create(int x, int y, CellType type);
    
}
