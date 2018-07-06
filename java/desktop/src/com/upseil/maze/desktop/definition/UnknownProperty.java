package com.upseil.maze.desktop.definition;

import java.beans.PropertyDescriptor;
import java.util.ResourceBundle;

import com.upseil.maze.desktop.Launcher;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class UnknownProperty extends AbstractPropertyView {
    
    private final ResourceBundle resources;
    private final Label label;

    public UnknownProperty(PropertyDescriptor property) {
        super(property);
        resources = Launcher.getResourceLoader().getResourceBundle();
        label = new Label(resources.getString("unknownProperty"));
        label.getStyleClass().add("severe-label");
    }

    @Override
    public Node getView() {
        return label;
    }

    @Override
    public Object getValue() {
        return null;
    }
    @Override
    public void setValue(Object value) { }
    
}