package com.upseil.maze.desktop.display;

import com.upseil.maze.desktop.ResourceLoader;
import com.upseil.maze.desktop.util.CssBasedColorMap;
import com.upseil.maze.desktop.util.MazeColorMap;
import com.upseil.maze.domain.Maze;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageMazeView extends ImageView implements MazeView {
    
    private static final Color DefaultColor = Color.TRANSPARENT;
    private static final Color UnknownColor = Color.RED;
    
    private final MazeColorMap colorMap;
    private WritableImage image;
    
    public ImageMazeView() {
        colorMap = new CssBasedColorMap(ResourceLoader.getResource(DefaultStyle), DefaultColor, UnknownColor);
    }

    private void displayMaze(Maze<?> maze) {
        if (image == null || image.widthProperty().intValue() != maze.getWidth() ||
            image.heightProperty().intValue() != maze.getHeight()) {
            image = new WritableImage(maze.getWidth(), maze.getHeight());
        }
        
        PixelWriter pixelWriter = image.getPixelWriter();
        maze.forEachPoint((x, y) -> {
            pixelWriter.setColor(x, y, colorMap.get(maze.getCell(x, y)));
        });
        setImage(image);
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
