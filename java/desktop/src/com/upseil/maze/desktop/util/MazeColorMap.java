package com.upseil.maze.desktop.util;

import java.util.HashMap;
import java.util.Map;

import com.upseil.maze.domain.Cell;

import javafx.scene.paint.Color;

public abstract class MazeColorMap {
    
    private final Map<String, Color> colorMap;
    
    public MazeColorMap() {
        this.colorMap = new HashMap<>();
    }
    
    public Color get(Cell cell) {
        Color color = colorMap.get("Default");
        if (cell != null) {
            String typeName = cell.getType().getName();
            color = colorMap.get(typeName);
            if (color == null) {
                color = colorMap.get("Unknown");
            }
        }
        return color;
    }
    
    protected void put(String key, Color color) {
        colorMap.put(key, color);
    }
    
}
