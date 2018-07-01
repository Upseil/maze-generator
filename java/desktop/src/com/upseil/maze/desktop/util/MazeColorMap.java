package com.upseil.maze.desktop.util;

import java.util.HashMap;
import java.util.Map;

import com.upseil.maze.domain.Cell;

import javafx.scene.paint.Color;

public abstract class MazeColorMap {
    
    private final Map<String, Color> colorMap;
    private final Color defaultColor;
    private final Color unknownColor;
    
    public MazeColorMap(Color defaultColor, Color unknownColor) {
        this.colorMap = new HashMap<>();
        this.defaultColor = defaultColor;
        this.unknownColor = unknownColor;
    }
    
    public Color get(Cell cell) {
        Color color = defaultColor;
        if (cell != null) {
            String typeName = cell.getType().getName();
            color = colorMap.get(typeName);
            if (color == null) {
                color = unknownColor;
            }
        }
        return color;
    }
    
    protected void put(String key, Color color) {
        colorMap.put(key, color);
    }
    
}
