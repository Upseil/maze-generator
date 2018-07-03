package com.upseil.maze.desktop.definition;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.util.GenericStringFormatter;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ConfigurationView extends GridPane {
    
    private static final Logger logger = Logger.getLogger(ConfigurationView.class.getName());
    
    private final ResourceBundle resources;
    
    private Class<?> currentType;
    private final Map<String, PropertyView> properties; 
    
    public ConfigurationView() {
        resources = Launcher.getResourceLoader().getResourceBundle();
        properties = new HashMap<>();
    }

    public void setType(Class<?> configurationType) {
        if (currentType != configurationType) {
            currentType = null;
            properties.clear();
            getChildren().clear();
            
            if (!hasParameterlessConstructor(configurationType)) {
                logger.log(Level.SEVERE, "The given type doesn't have a no-args constructor: " + configurationType.getName());
                return;
            }
            currentType = configurationType;
            
            try {
                BeanInfo bean = Introspector.getBeanInfo(configurationType);
                List<PropertyDescriptor> properties = Arrays.asList(bean.getPropertyDescriptors());
                properties.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
                for (int row = 0; row < properties.size(); row++) {
                    PropertyDescriptor property = properties.get(row);
                    String propertyName = property.getName();
                    if (property.getReadMethod() == null || property.getWriteMethod() == null) continue;
                    
                    Label label = new Label(resources.getString(propertyName));
                    PropertyView view = createPropertyView(property);
                    addRow(row, label, view.getView());

                    this.properties.put(propertyName, view);
                }
            } catch (IntrospectionException e) {
                logger.log(Level.SEVERE, "Error inspecting the given configuration type", e);
            }
        }
    }
    
    private boolean hasParameterlessConstructor(Class<?> type) {
        for (Constructor<?> constructor : type.getConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    private PropertyView createPropertyView(PropertyDescriptor property) {
        Class<?> type = property.getPropertyType();
        if (Enum.class.isAssignableFrom(type)) {
            return new EnumPropertyView(property);
        }
        
        logger.log(Level.SEVERE, "Can't create property view for type " + type.getName());
        return new UnknownProperty(property);
    }
    
    public Object createConfiguration() {
        if (currentType == null) {
            return null;
        }
        
        Object configuration = null;
        try {
            configuration = currentType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "Error creating the configuration instance for " + currentType.getName());
        }
        
        if (configuration != null) {
            for (PropertyView propertyView : properties.values()) {
                Object value = propertyView.getValue();
                PropertyDescriptor property = propertyView.getProperty();
                try {
                    property.getWriteMethod().invoke(configuration, value);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    logger.log(Level.SEVERE, "Error writing the property" + property.getName(), e);
                }
            }
        }
        return configuration;
    }

    private static interface PropertyView {
        
        PropertyDescriptor getProperty();
        Node getView();
        
        Object getValue();
        void setValue(Object value);
        
    }
    
    private static abstract class AbstractPropertyView implements PropertyView {
        
        private final PropertyDescriptor property;

        public AbstractPropertyView(PropertyDescriptor property) {
            this.property = property;
        }

        @Override
        public PropertyDescriptor getProperty() {
            return property;
        }
        
    }
    
    private class UnknownProperty extends AbstractPropertyView {
        
        private final Label label;

        public UnknownProperty(PropertyDescriptor property) {
            super(property);
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
    
    private class EnumPropertyView extends AbstractPropertyView {
        
        private final ComboBox<Enum<?>> comboBox;

        public EnumPropertyView(PropertyDescriptor property) {
            super(property);
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
    
}
