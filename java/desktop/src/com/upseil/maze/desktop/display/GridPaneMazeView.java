package com.upseil.maze.desktop.display;

import com.upseil.maze.desktop.Launcher;
import com.upseil.maze.desktop.ResourceLoader;
import com.upseil.maze.domain.Cell;
import com.upseil.maze.domain.Maze;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GridPaneMazeView extends GridPane implements MazeView {
    
    private static final String DefaultStyle = "style/maze/default.css";
    
    private final DoubleProperty cellSizeProperty;
    
    public GridPaneMazeView() {
        cellSizeProperty = new SimpleDoubleProperty();
        
        Launcher.getResourceLoader().loadFXML(this, this, "/view/maze/GridPaneMazeView.fxml");
        getStylesheets().add(ResourceLoader.getResource(DefaultStyle).toExternalForm());
        getStyleClass().add("background");
    }
    
    @FXML
    private void initialize() {
        cellSizeProperty.bind(Bindings.min(
            widthProperty().divide(Bindings.selectInteger(mazeProperty, "width")), 
            heightProperty().divide(Bindings.selectInteger(mazeProperty, "height")))
        );
    }
    
    private void displayMaze(Maze<?> maze) {
        getChildren().clear();
        int height = maze.getHeight() - 1;
        maze.forEachPoint((x, y) -> add(createCellWidget(maze, x, y), x, height - y));
    }

    private Pane createCellWidget(Maze<?> maze, int x, int y) {
        Pane cellPane = new Pane();
        cellPane.prefWidthProperty().bind(cellSizeProperty);
        cellPane.prefHeightProperty().bind(cellSizeProperty);
        
        Cell cell = maze.getCell(x, y);
        String styleClass = cell == null ? "Empty" : cell.getType().getName();
        cellPane.getStyleClass().add(styleClass);
        
        return cellPane;
    }

    private final ObjectProperty<Maze<?>> mazeProperty = new SimpleObjectProperty<Maze<?>>(this, "maze") {
        @Override
        protected void invalidated() {
            displayMaze(get());
        };
    };
    
    @Override
    public ObjectProperty<Maze<?>> mazeProperty() {
        return mazeProperty;
    }
    @Override
    public void setMaze(Maze<?> maze) {
        mazeProperty.set(maze);
    }
    @Override
    public Maze<?> getMaze() {
        return mazeProperty.get();
    }
    
}
