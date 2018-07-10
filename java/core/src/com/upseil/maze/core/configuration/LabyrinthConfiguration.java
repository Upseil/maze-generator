package com.upseil.maze.core.configuration;

import java.util.Objects;

public class LabyrinthConfiguration {
    
    public enum Border { Solid, None, Indifferent }
    
    private Border border = Border.Indifferent;

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        Objects.requireNonNull(border, "border must not be null");
        this.border = border;
    }
    
}
