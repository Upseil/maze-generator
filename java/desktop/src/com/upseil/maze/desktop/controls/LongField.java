package com.upseil.maze.desktop.controls;

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleLongProperty;

public class LongField extends AbstractIntegerField {
    
    public LongField() {
        valueProperty.bind(Bindings.createLongBinding(
            () -> isValid() ? Long.parseLong(getText()) : getDefaultValue(),
            validProperty(), textProperty()
        ));
    }
    
    @Override
    protected boolean validate() {
        return super.validate() && isInBounds(Long.parseLong(getText()));
    }
    
    @Override
    protected boolean isValidInteger(String text) {
        boolean valid = super.isValidInteger(text);
        if (valid) {
            try {
                Long.parseLong(text);
            } catch (NumberFormatException e) {
                valid = false;
            }
        }
        return valid;
    }
    
    @Override
    protected boolean negativeValuesAllowed() {
        return getLowerBound() < 0;
    }
    
    private boolean isInBounds(long value) {
        return value >= getLowerBound() && value <= getUpperBound();
    }
    
    // Properties ---------------------------------------------------------------------------------------------------------------------------------------------
    
    private final LongProperty valueProperty = new SimpleLongProperty(this, "value", 0);
    public ReadOnlyLongProperty valueProperty() {
        return valueProperty;
    }
    public long getValue() {
        return valueProperty().get();
    }
    public void setValue(long value) {
        setText(Long.toString(value));
    }
    
    private final LongProperty defaultValueProperty = new SimpleLongProperty(this, "defaultValue", 0);
    public LongProperty defaultValueProperty() {
        return defaultValueProperty;
    }
    public long getDefaultValue() {
        return defaultValueProperty().get();
    }
    public void setDefaultValue(long defaultValue) {
        defaultValueProperty().set(defaultValue);
    }
    
    private final LongProperty lowerBoundProperty = new SimpleLongProperty(this, "lowerBound", Long.MIN_VALUE);
    public LongProperty lowerBoundProperty() {
        return lowerBoundProperty;
    }
    public long getLowerBound() {
        return lowerBoundProperty().get();
    }
    public void setLowerBound(long lowerBound) {
        lowerBoundProperty().set(lowerBound);
    }
    
    private final LongProperty upperBoundProperty = new SimpleLongProperty(this, "upperBound", Long.MAX_VALUE);
    public LongProperty upperBoundProperty() {
        return upperBoundProperty;
    }
    public long getUpperBound() {
        return upperBoundProperty().get();
    }
    public void setUpperBound(long upperBound) {
        upperBoundProperty().set(upperBound);
    }
    
}
