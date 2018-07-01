package com.upseil.maze.desktop.display;

import com.upseil.maze.desktop.ResourceLoader;
import com.upseil.maze.desktop.util.CssBasedColorMap;
import com.upseil.maze.desktop.util.MazeColorMap;
import com.upseil.maze.domain.Maze;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class CanvasMazeView extends StackPane implements MazeView {
    
    private static final Color DefaultColor = Color.TRANSPARENT;
    private static final Color UnknownColor = Color.RED;
    
    private final MazeColorMap colorMap;
    private final Canvas canvas;
    
    public CanvasMazeView() {
        colorMap = new CssBasedColorMap(ResourceLoader.getResource(DefaultStyle), DefaultColor, UnknownColor);

        canvas = new Canvas();
        ObservableValue<Number> scaling = Bindings.min(widthProperty().divide(canvas.widthProperty()), heightProperty().divide(canvas.heightProperty()));
        canvas.scaleXProperty().bind(scaling);
        canvas.scaleYProperty().bind(scaling);
        
        setAlignment(Pos.CENTER);
        getChildren().add(canvas);
    }

    private void displayMaze(Maze<?> maze) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (canvas.getWidth() != maze.getWidth()) {
            canvas.setWidth(maze.getWidth());
        }
        if (canvas.getHeight() != maze.getHeight()) {
            canvas.setHeight(maze.getHeight());
        }
        
        maze.forEachPoint((x, y) -> {
            Color color = colorMap.get(maze.getCell(x, y));
            context.setFill(color);
            context.fillRect(x, y, 1, 1);
        });
    }

    private final ObjectProperty<Maze<?>> mazeProperty = new SimpleObjectProperty<Maze<?>>(this, "maze") {
        @Override
        protected void invalidated() {
            displayMaze(get());
        }
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
