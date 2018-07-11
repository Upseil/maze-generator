package com.upseil.maze.desktop.display;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.desktop.ResourceLoader;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GridPaneMazeView extends GridPane implements MazeView {
    
    private final DoubleProperty cellSizeProperty;
    
    public GridPaneMazeView() {
        cellSizeProperty = new SimpleDoubleProperty();
        getStylesheets().add(ResourceLoader.getResource(DefaultStyle).toExternalForm());

        cellSizeProperty.bind(Bindings.min(
            widthProperty().divide(Bindings.selectInteger(mazeProperty, "width")), 
            heightProperty().divide(Bindings.selectInteger(mazeProperty, "height")))
        );
    }
    
    private void displayMaze(Maze maze) {
        getChildren().clear();
        int mazeHeight = maze.getHeight();
        maze.forEachPoint((x, y) -> add(createCellWidget(maze, x, y), x, mazeHeight - y - 1));
    }

    private Pane createCellWidget(Maze maze, int x, int y) {
        Pane cellPane = new Pane();
        cellPane.prefWidthProperty().bind(cellSizeProperty);
        cellPane.prefHeightProperty().bind(cellSizeProperty);
        
        Cell cell = maze.getCell(x, y);
        String styleClass = cell == null ? "Default" : cell.getType().getName();
        ObservableList<String> styleClasses = cellPane.getStyleClass();
        styleClasses.add("Unknown");
        styleClasses.add(styleClass);
        
        return cellPane;
    }

    private final ObjectProperty<Maze> mazeProperty = new SimpleObjectProperty<Maze>(this, "maze") {
        @Override
        protected void invalidated() {
            displayMaze(get());
        };
    };
    @Override
    public ObjectProperty<Maze> mazeProperty() {
        return mazeProperty;
    }
    
}
