package com.upseil.maze.core.domain;

import java.util.Objects;

public class SimpleCell implements Cell {
    
    private final int x;
    private final int y;
    
    private final CellType type;

    public SimpleCell(int x, int y, CellType type) {
        Objects.requireNonNull(type, "type must not be null");
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public CellType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(SimpleCell.class.getSimpleName()).append("[x=").append(x).append(", y=").append(y).append(", type=").append(type).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + type.hashCode();
        result = prime * result + x;
        result = prime * result + y;
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
        SimpleCell other = (SimpleCell) obj;
        if (type != other.type)
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    
}
