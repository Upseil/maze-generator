package com.upseil.maze.core.domain;

import java.util.function.Predicate;

public class Cells {
    
    private Cells() { }
    
    public static Predicate<Cell> ofType(CellType type) {
        return c -> c.getType().equals(type);
    }
    
}
