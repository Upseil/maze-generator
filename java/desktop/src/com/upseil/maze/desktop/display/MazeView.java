package com.upseil.maze.desktop.display;

import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.ResourceLoader;
import com.upseil.maze.domain.Maze;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MazeView extends GridPane {
    
    private static final String DefaultStyle = "style/maze/default.css";
    
    private final DoubleProperty cellSizeProperty;
    
    public MazeView() {
        cellSizeProperty = new SimpleDoubleProperty();
        
        Launcher.getResourceLoader().loadFXML(this, this, "/view/maze/MazeView.fxml");
        getStylesheets().add(ResourceLoader.getResource(DefaultStyle).toExternalForm());
    }
    
    @FXML
    private void initialize() {
        cellSizeProperty.bind(Bindings.min(
            widthProperty().divide(Bindings.selectInteger(mazeProperty, "width")), 
            heightProperty().divide(Bindings.selectInteger(mazeProperty, "height")))
        );
    }
    
    private void displayMaze(Maze maze) {
        getChildren().clear();
        
        int height = maze.getHeight() - 1;
        maze.forEach(cell -> {
            Pane cellPane = new Pane();
            cellPane.prefWidthProperty().bind(cellSizeProperty);
            cellPane.prefHeightProperty().bind(cellSizeProperty);
            cellPane.getStyleClass().add(cell.getType().getName());
            add(cellPane, cell.getX(), height - cell.getY());
        });
    }

    private final ObjectProperty<Maze> mazeProperty = new SimpleObjectProperty<Maze>(this, "maze") {
        @Override
        protected void invalidated() {
            displayMaze(get());
        };
    };
    
    public ObjectProperty<Maze> mazeProperty() {
        return mazeProperty;
    }
    public void setMaze(Maze maze) {
        mazeProperty.set(maze);
    }
    public Maze getMaze() {
        return mazeProperty.get();
    }
    
}
