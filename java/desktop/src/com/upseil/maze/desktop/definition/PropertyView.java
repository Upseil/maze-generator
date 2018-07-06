package com.upseil.maze.desktop.definition;

import java.beans.PropertyDescriptor;

import com.upseil.maze.desktop.util.Validatable;

import javafx.scene.Node;

public interface PropertyView extends Validatable {
    
    PropertyDescriptor getProperty();
    Node getView();
    
    Object getValue();
    void setValue(Object value);
    
}