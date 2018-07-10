package com.upseil.maze.core.domain;

public final class CellType {
    
    public static final CellType Floor = new CellType("Floor");
    public static final CellType  Wall = new CellType("Wall");
    public static final CellType Start = new CellType("Start");
    public static final CellType  Exit = new CellType("Exit");
    
    private final String name;

    public CellType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CellType other = (CellType) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
}
