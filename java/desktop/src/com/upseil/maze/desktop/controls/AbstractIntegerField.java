package com.upseil.maze.desktop.controls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.upseil.maze.desktop.util.Validatable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public abstract class AbstractIntegerField extends TextField implements Validatable {
    
    private static final Matcher IntegerMatcher = Pattern.compile("(-?)\\d+").matcher("");

    public AbstractIntegerField() {
        /* Blocking changes that would result in an invalid integer value
         * Exceptions:
         *  - Deletions to allow an empty text field.
         *  - A single "-" character, if the upper bound is less 0.
         *    This allows to start a negative integer with "-" instead of typing the number first and adding "-" afterwards.
         */
        super.setTextFormatter(new TextFormatter<>(change -> {
            String controlNewText = change.getControlNewText();
            String changeText = change.getText();
            
            if ((negativeValuesAllowed() && controlNewText.equals("-")) ||
                changeText.isEmpty() || isValidInteger(controlNewText)) {
                return change;
            }
            return null;
        }));
        
        validProperty.bind(Bindings.createBooleanBinding(this::validate, textProperty()));
    }
    
    protected boolean validate() {
        String text = getText();
        if (text == null || text.isEmpty()) {
            return isNullValid();
        }
        return isValidInteger(text);
    }
    
    protected boolean isValidInteger(String text) {
        return IntegerMatcher.reset(text).matches();
    }
    
    protected abstract boolean negativeValuesAllowed();
    
    // Properties ---------------------------------------------------------------------------------------------------------------------------------------------
    
    private final BooleanProperty validProperty = new SimpleBooleanProperty(this, "valid", false);
    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }
    
    private final BooleanProperty nullValidProperty = new SimpleBooleanProperty(this, "nullValid", false);
    public BooleanProperty nullValidProperty() {
        return nullValidProperty;
    }
    public boolean isNullValid() {
        return nullValidProperty().get();
    }
    public void setNullValid(boolean isNullValid) {
        nullValidProperty().set(isNullValid);
    }
    
}
