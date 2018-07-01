package com.upseil.maze.desktop.util;

import javafx.beans.property.ReadOnlyBooleanProperty;

public interface Validatable {
    
    ReadOnlyBooleanProperty validProperty();
    default boolean isValid() {
        return validProperty().get();
    }
    
}
