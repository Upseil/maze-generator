package com.upseil.maze.core.configuration;

import java.util.Objects;

import com.upseil.maze.core.domain.CellType;

public class MazeFillerConfiguration implements ModifierConfiguration {
    
    public static final String Type = "MazeFiller";
    
    private CellType fillType = CellType.Wall;
    
    @Override
    public String getType() {
        return Type;
    }

    public CellType getFillType() {
        return fillType;
    }

    public void setFillType(CellType fillType) {
        Objects.requireNonNull(fillType, "fill type must not be null");
        this.fillType = fillType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append("[fillType=").append(fillType).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fillType == null) ? 0 : fillType.hashCode());
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
        MazeFillerConfiguration other = (MazeFillerConfiguration) obj;
        if (fillType == null) {
            if (other.fillType != null)
                return false;
        } else if (!fillType.equals(other.fillType))
            return false;
        return true;
    }
    
}
