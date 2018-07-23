package com.upseil.maze.core.configuration;

import java.util.Objects;

public class LabyrinthConfiguration implements GeneratorConfiguration {
    
    public static final String Type = "Labyrinth";
    
    public enum Border { Solid, None, Indifferent }
    
    private Border border = Border.Indifferent;
    
    @Override
    public String getType() {
        return Type;
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        Objects.requireNonNull(border, "border must not be null");
        this.border = border;
    }
    
}
