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
import com.upseil.maze.desktop.util.Validatable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ConfigurationView extends GridPane implements Validatable {
    
    private static final Logger logger = Logger.getLogger(ConfigurationView.class.getName());
    
    private final ResourceBundle resources;
    private final BooleanProperty validProperty;
    
    private Class<?> currentType;
    private final Map<String, PropertyView> properties; 
    
    public ConfigurationView() {
        resources = Launcher.getResourceLoader().getResourceBundle();
        validProperty = new SimpleBooleanProperty(this, "valid", true);
        properties = new HashMap<>();
    }

    public void setType(Class<?> configurationType) {
        if (currentType != configurationType) {
            validProperty.unbind();
            currentType = null;
            properties.clear();
            getChildren().clear();
            
            if (!hasParameterlessConstructor(configurationType)) {
                logger.log(Level.SEVERE, "The given type doesn't have a no-args constructor: " + configurationType.getName());
                return;
            }
            currentType = configurationType;
            
            try {
                ObservableBooleanValue valid = null;
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
                    valid = valid == null ? view.validProperty() : Bindings.and(valid, view.validProperty());
                }
                validProperty.bind(valid);
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
    
    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }
    
}
