package com.upseil.maze.desktop.controls;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntegerField extends AbstractIntegerField {
    
    public IntegerField() {
        valueProperty.bind(Bindings.createIntegerBinding(
            () -> isValid() ? Integer.parseInt(getText()) : getDefaultValue(),
            validProperty(), textProperty()
        ));
    }
    
    @Override
    protected boolean validate() {
        return super.validate() && isInBounds(Integer.parseInt(getText()));
    }
    
    @Override
    protected boolean isValidInteger(String text) {
        boolean valid = super.isValidInteger(text);
        if (valid) {
            try {
                Integer.parseInt(text);
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
    
    private boolean isInBounds(int value) {
        return value >= getLowerBound() && value <= getUpperBound();
    }
    
    // Properties ---------------------------------------------------------------------------------------------------------------------------------------------
    
    private final IntegerProperty valueProperty = new SimpleIntegerProperty(this, "value", 0);
    public ReadOnlyIntegerProperty valueProperty() {
        return valueProperty;
    }
    public int getValue() {
        return valueProperty().get();
    }
    public void setValue(int value) {
        setText(Integer.toString(value));
    }
    
    private final IntegerProperty defaultValueProperty = new SimpleIntegerProperty(this, "defaultValue", 0);
    public IntegerProperty defaultValueProperty() {
        return defaultValueProperty;
    }
    public int getDefaultValue() {
        return defaultValueProperty().get();
    }
    public void setDefaultValue(int defaultValue) {
        defaultValueProperty().set(defaultValue);
    }
    
    private final IntegerProperty lowerBoundProperty = new SimpleIntegerProperty(this, "lowerBound", Integer.MIN_VALUE);
    public IntegerProperty lowerBoundProperty() {
        return lowerBoundProperty;
    }
    public int getLowerBound() {
        return lowerBoundProperty().get();
    }
    public void setLowerBound(int lowerBound) {
        lowerBoundProperty().set(lowerBound);
    }
    
    private final IntegerProperty upperBoundProperty = new SimpleIntegerProperty(this, "upperBound", Integer.MAX_VALUE);
    public IntegerProperty upperBoundProperty() {
        return upperBoundProperty;
    }
    public int getUpperBound() {
        return upperBoundProperty().get();
    }
    public void setUpperBound(int upperBound) {
        upperBoundProperty().set(upperBound);
    }
    
}
