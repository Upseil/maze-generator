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
    
}
