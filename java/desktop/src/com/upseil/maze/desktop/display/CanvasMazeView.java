package com.upseil.maze.desktop.display;

import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.desktop.ResourceLoader;
import com.upseil.maze.desktop.util.CssBasedColorMap;
import com.upseil.maze.desktop.util.MazeColorMap;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class CanvasMazeView extends StackPane implements MazeView {
    
    private final MazeColorMap colorMap;
    private final Canvas canvas;
    
    public CanvasMazeView() {
        colorMap = new CssBasedColorMap(ResourceLoader.getResource(DefaultStyle));

        canvas = new Canvas();
        ObservableValue<Number> scaling = Bindings.min(widthProperty().divide(canvas.widthProperty()), heightProperty().divide(canvas.heightProperty()));
        canvas.scaleXProperty().bind(scaling);
        canvas.scaleYProperty().bind(scaling);
        
        setAlignment(Pos.CENTER);
        getChildren().add(canvas);

        widthProperty().addListener((o, oV, nV) -> sizeChanged());
        heightProperty().addListener((o, oV, nV) -> sizeChanged());
    }
    
    private void sizeChanged() {
        if (getMaze() != null) {
            renderMaze();
        }
    }

    private void renderMaze() {
        Maze<?> maze = getMaze();
        int mazeWidth = maze.getWidth();
        int mazeHeight = maze.getHeight();
        double cellScale = Math.floor(Math.min(getWidth() / mazeWidth, getHeight() / mazeHeight));
        double canvasWidth = mazeWidth * cellScale;
        double canvasHeight = mazeHeight * cellScale;
        
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (canvas.getWidth() != canvasWidth) {
            canvas.setWidth(canvasWidth);
        }
        if (canvas.getHeight() != canvasHeight) {
            canvas.setHeight(canvasHeight);
        }
        maze.forEach(cell -> {
            context.setFill(colorMap.get(cell));
            double x = cell.getX() * cellScale;
            double y = (mazeHeight - cell.getY() - 1) * cellScale;
            context.fillRect(x, y, cellScale, cellScale);
        });
    }

    private final ObjectProperty<Maze<?>> mazeProperty = new SimpleObjectProperty<Maze<?>>(this, "maze") {
        @Override
        protected void invalidated() {
            renderMaze();
        }
    };
    @Override
    public ObjectProperty<Maze<?>> mazeProperty() {
        return mazeProperty;
    }
    
}
