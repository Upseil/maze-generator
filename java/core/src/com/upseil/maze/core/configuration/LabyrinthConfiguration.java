package com.upseil.maze.core.configuration;

public class LabyrinthConfiguration {
    
    public enum Border { Solid, None, Indifferent }
    
    private Border border;

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }
    
}
