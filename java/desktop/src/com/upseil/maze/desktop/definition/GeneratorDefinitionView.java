package com.upseil.maze.desktop.definition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reflections.Reflections;

import com.upseil.maze.core.configuration.Configurable;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.MazeFactory;
import com.upseil.maze.core.generator.MazeGenerator;
import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.controls.IntegerField;
import com.upseil.maze.desktop.controls.LongField;
import com.upseil.maze.desktop.event.MazeGeneratedEvent;
import com.upseil.maze.desktop.util.GenericStringFormatter;
import com.upseil.maze.desktop.util.Validatable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class GeneratorDefinitionView extends VBox implements Validatable {
    
    private static final Logger logger = Logger.getLogger(GeneratorDefinitionView.class.getName());
    
    @FXML private ResourceBundle resources;
    
    @FXML private ComboBox<GeneratorDefinition> generatorSelector;
    @FXML private LongField seedField;
    @FXML private IntegerField widthField;
    @FXML private IntegerField heightField;
    @FXML private TitledPane mazeConfigurationContainer;
    @FXML private ConfigurationView mazeConfigurationView;
    
    @FXML private Button generateButton;
    
    public GeneratorDefinitionView() {
        Launcher.getResourceLoader().loadFXML(this, this, "view/definition/GeneratorDefinitionView.fxml");
    }
    
    @FXML
    private void initialize() {
        generatorSelector.valueProperty().addListener((o, oV, nV) -> {
            Class<?> configurationType = nV.getConfigurationType();
            boolean configurable = configurationType != null;
            
            mazeConfigurationContainer.setVisible(configurable);
            mazeConfigurationContainer.setManaged(configurable);
            mazeConfigurationContainer.setExpanded(true);
            if (configurable) {
                mazeConfigurationView.setType(configurationType);
            }
        });
        
        Reflections reflections = new Reflections("com.upseil.maze");
        for (@SuppressWarnings("rawtypes") Class<? extends MazeGenerator> type : reflections.getSubTypesOf(MazeGenerator.class)) {
            if (!type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
                GeneratorDefinition.createFor(type).ifPresent(d -> generatorSelector.getItems().add(d));
            }
        }
        generatorSelector.setConverter(new GenericStringFormatter<>(t -> resources.getString(t.getName())));
        generatorSelector.setValue(generatorSelector.getItems().get(0));
        
        seedField.setOnMouseClicked(e -> seedField.selectAll());
        
        generateButton.disableProperty().bind(validProperty().not());
        
        validProperty.bind(
            generatorSelector.valueProperty().isNotNull()
            .and(widthField.validProperty()).and(heightField.validProperty())
        );
    }
    
    @FXML
    private void generateMaze() {
        if (!isValid()) return;
        
        MazeGenerator<Maze> generator = getGenerator();
        long start = System.currentTimeMillis();
        Maze maze = generator.generate(widthField.getValue(), heightField.getValue());
        long duration = System.currentTimeMillis() - start;
        logger.log(Level.FINE, "Generated maze in " + duration + "ms");
        this.fireEvent(new MazeGeneratedEvent(maze));
    }
    
    private MazeGenerator<Maze> getGenerator() {
        Random random;
        if (seedField.isValid()) {
            random = new Random(seedField.getValue());
        } else {
            random = new Random();
            long seed = random.nextLong();
            random.setSeed(seed);
            seedField.setPromptText(Long.toString(seed));
        }
        
        GeneratorDefinition definition = generatorSelector.getValue();
        return definition.create(random, MazeFactory.DefaultGridMaze, mazeConfigurationView.createConfiguration());
    }
    
    @FXML
    private void fixSeed() {
        seedField.setText(seedField.getPromptText());
    }

    private final BooleanProperty validProperty = new SimpleBooleanProperty(this, "valid", false);
    @Override
    public ReadOnlyBooleanProperty validProperty() {
        return validProperty;
    }
    
    @FunctionalInterface
    private interface GeneratorFactory {
        <M extends Maze> MazeGenerator<M> create(Random random, MazeFactory<M> mazeFactory, Object configuration);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static class GeneratorDefinition implements GeneratorFactory {
        
        public static Optional<GeneratorDefinition> createFor(Class<? extends MazeGenerator> type) {
            try {
                Constructor<MazeGenerator> constructor = (Constructor<MazeGenerator>) type.getConstructor(Random.class, MazeFactory.class);
                Class configurationType = null;
                if (Configurable.class.isAssignableFrom(type)) {
                    configurationType = type.getMethod("getConfiguration").getReturnType();
                }
                return Optional.of(new GeneratorDefinition(type, constructor, configurationType));
            } catch (NoSuchMethodException | SecurityException e) {
                logger.log(Level.SEVERE, "Error creating the generator definition", e);
                return Optional.empty();
            }
        }
        
        private final Class<? extends MazeGenerator> type;
        private final Constructor<MazeGenerator> constructor;
        private final Class configurationType;

        public GeneratorDefinition(Class<? extends MazeGenerator> type, Constructor<MazeGenerator> constructor, Class configurationType) {
            this.type = type;
            this.constructor = constructor;
            this.configurationType = configurationType;
        }
        
        public String getName() {
            return type.getSimpleName();
        }
        
        public Class<?> getConfigurationType() {
            return configurationType;
        }

        @Override
        public <M extends Maze> MazeGenerator<M> create(Random random, MazeFactory<M> mazeFactory, Object configuration) {
            MazeGenerator generator = null;
            try {
                generator = constructor.newInstance(random, mazeFactory);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.log(Level.SEVERE, "Error invoking generator constructor", e);
            }
            
            if (generator != null && configuration != null && generator instanceof Configurable) {
                if (configurationType.isAssignableFrom(configuration.getClass())) {
                    ((Configurable) generator).setConfiguration(configuration);
                } else {
                    logger.log(Level.SEVERE, "The given configuration of type " + configuration.getClass().getName() +
                                             " doesn't match the expected type " + configurationType.getName());
                }
            }
            return generator;
        }
        
    }
    
}
