package com.upseil.maze.domain.factory;

import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.CellType;

public interface CellFactory {
    
    Cell create(int x, int y, CellType type);
    
}
