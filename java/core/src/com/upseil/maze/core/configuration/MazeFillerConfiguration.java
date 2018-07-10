package com.upseil.maze.core.configuration;

import java.util.Objects;

import com.upseil.maze.core.domain.CellType;

public class MazeFillerConfiguration {
    
    private CellType fillType = CellType.Wall;

    public CellType getFillType() {
        return fillType;
    }

    public void setFillType(CellType fillType) {
        Objects.requireNonNull(fillType, "fill type must not be null");
        this.fillType = fillType;
    }
    
}
