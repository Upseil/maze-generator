package com.upseil.maze.core.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Cells {
    
    private Cells() { }
    
    public static Predicate<Cell> ofType(CellType type) {
        return c -> c.getType().equals(type);
    }
    
    public static Predicate<Cell> ofType(Iterable<CellType> types) {
        Set<CellType> typesSet;
        if (types instanceof Set) {
            typesSet = (Set<CellType>) types;
        } else {
            typesSet = new HashSet<>();
            for (CellType type : types) {
                typesSet.add(type);
            }
        }
        return ofType(typesSet);
    }
    
    public static Predicate<Cell> ofType(CellType... types) {
        Set<CellType> typesSet = new HashSet<>();
        for (CellType type : types) {
            typesSet.add(type);
        }
        return ofType(typesSet);
    }
    
    private static Predicate<Cell> ofType(Set<CellType> types) {
        return c -> types.contains(c.getType());
    }
    
}
