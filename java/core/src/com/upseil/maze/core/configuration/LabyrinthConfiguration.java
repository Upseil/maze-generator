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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append("[border=").append(border).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((border == null) ? 0 : border.hashCode());
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
        LabyrinthConfiguration other = (LabyrinthConfiguration) obj;
        if (border != other.border)
            return false;
        return true;
    }
    
}
