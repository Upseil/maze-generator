package com.upseil.maze.desktop.definition;

import java.beans.PropertyDescriptor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class AbstractPropertyView implements PropertyView {
    
    private final PropertyDescriptor property;
    protected final BooleanProperty validProperty;

    public AbstractPropertyView(PropertyDescriptor property) {
        this.property = property;
        validProperty = new SimpleBooleanProperty(this, "valid", true);
    }

    @Override
    public PropertyDescriptor getProperty() {
        return property;
    }
    
    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }
    
}