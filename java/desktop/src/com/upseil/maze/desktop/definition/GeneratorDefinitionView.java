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

import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.controls.IntegerField;
import com.upseil.maze.desktop.controls.LongField;
import com.upseil.maze.desktop.event.MazeGeneratedEvent;
import com.upseil.maze.desktop.util.GenericStringFormatter;
import com.upseil.maze.desktop.util.Validatable;
import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.MazeFactory;
import com.upseil.maze.generator.MazeGenerator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class GeneratorDefinitionView extends VBox implements Validatable {
    
    private static final Logger logger = Logger.getLogger(GeneratorDefinitionView.class.getSimpleName());
    
    @FXML private ResourceBundle resources;
    
    @FXML private ComboBox<GeneratorDefinition> generatorSelector;
    @FXML private LongField seedField;
    @FXML private IntegerField widthField;
    @FXML private IntegerField heightField;
    
    @FXML private Button generateButton;
    
    public GeneratorDefinitionView() {
        Launcher.getResourceLoader().loadFXML(this, this, "view/definition/GeneratorDefinitionView.fxml");
    }
    
    @FXML
    private void initialize() {
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
        
        MazeGenerator<?, ?> generator = getGenerator();
        Maze<?> maze = generator.generate(widthField.getValue(), heightField.getValue());
        this.fireEvent(new MazeGeneratedEvent(maze));
    }
    
    private MazeGenerator<?, ?> getGenerator() {
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
        return definition.create(random, MazeFactory.Default, CellFactory.Default);
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
        <M extends Maze<C>, C extends Cell> MazeGenerator<M, C> create(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static class GeneratorDefinition implements GeneratorFactory {
        
        public static Optional<GeneratorDefinition> createFor(Class<? extends MazeGenerator> type) {
            try {
                Constructor<MazeGenerator> constructor = (Constructor<MazeGenerator>) type.getConstructor(Random.class, MazeFactory.class, CellFactory.class);
                return Optional.of(new GeneratorDefinition(constructor));
            } catch (NoSuchMethodException | SecurityException e) {
                return Optional.empty();
            }
        }
        
        private final Constructor<MazeGenerator> constructor;

        public GeneratorDefinition(Constructor<MazeGenerator> constructor) {
            this.constructor = constructor;
        }
        
        public String getName() {
            return constructor.getDeclaringClass().getSimpleName();
        }

        @Override
        public <M extends Maze<C>, C extends Cell> MazeGenerator<M, C>  create(Random random, MazeFactory<M, C> mazeFactory, CellFactory<C> cellFactory) {
            try {
                return constructor.newInstance(random, mazeFactory, cellFactory);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.log(Level.SEVERE, "Error invoking generator constructor", e);
                return null;
            }
        }
        
    }
    
}
