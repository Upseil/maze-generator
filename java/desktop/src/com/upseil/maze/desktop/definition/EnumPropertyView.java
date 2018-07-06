package com.upseil.maze.desktop.definition;

import java.beans.PropertyDescriptor;
import java.util.ResourceBundle;

import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.util.GenericStringFormatter;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class EnumPropertyView extends AbstractPropertyView {

    private final ResourceBundle resources;
    private final ComboBox<Enum<?>> comboBox;

    public EnumPropertyView(PropertyDescriptor property) {
        super(property);
        resources = Launcher.getResourceLoader().getResourceBundle();
        comboBox = new ComboBox<>();
        comboBox.setConverter(new GenericStringFormatter<>(t -> resources.getString(t.name())));
        for (Object value : property.getPropertyType().getEnumConstants()) {
            comboBox.getItems().add((Enum<?>) value);
        }
        comboBox.setValue(comboBox.getItems().get(0));
    }

    @Override
    public Node getView() {
        return comboBox;
    }

    @Override
    public Object getValue() {
        return comboBox.getValue();
    }

    @Override
    public void setValue(Object value) {
        comboBox.setValue((Enum<?>) value);
    }
    
}