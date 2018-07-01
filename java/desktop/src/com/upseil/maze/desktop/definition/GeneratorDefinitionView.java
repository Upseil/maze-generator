package com.upseil.maze.desktop.definition;

import java.util.Random;
import java.util.ResourceBundle;

import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.controls.IntegerField;
import com.upseil.maze.desktop.controls.LongField;
import com.upseil.maze.desktop.event.MazeGeneratedEvent;
import com.upseil.maze.desktop.util.GenericStringFormatter;
import com.upseil.maze.desktop.util.Validatable;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.domain.factory.MazeFactory;
import com.upseil.maze.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.generator.MazeGenerator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class GeneratorDefinitionView extends VBox implements Validatable {
    
    private enum GeneratorType { LabyrinthBacktracking }
    
    @FXML private ResourceBundle resources;
    
    @FXML private ComboBox<GeneratorType> generatorSelector;
    @FXML private LongField seedField;
    @FXML private IntegerField widthField;
    @FXML private IntegerField heightField;
    
    @FXML private Button generateButton;
    
    public GeneratorDefinitionView() {
        Launcher.getResourceLoader().loadFXML(this, this, "view/definition/GeneratorDefinitionView.fxml");
    }
    
    @FXML
    private void initialize() {
        generatorSelector.setConverter(new GenericStringFormatter<>(t -> resources.getString(t.name())));
        generatorSelector.getItems().addAll(GeneratorType.values());
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
        
        switch (generatorSelector.getValue()) {
        case LabyrinthBacktracking:
            return new BacktrackingLabyrinthGenerator<>(random, MazeFactory.Default, CellFactory.Default);
        }
        throw new IllegalStateException("Can't create generator for " + generatorSelector.getValue());
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
    
}
