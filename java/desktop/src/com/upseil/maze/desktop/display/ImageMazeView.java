package com.upseil.maze.desktop.display;

import com.upseil.maze.desktop.ResourceLoader;
import com.upseil.maze.desktop.util.CssBasedColorMap;
import com.upseil.maze.desktop.util.MazeColorMap;
import com.upseil.maze.domain.Maze;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

public class ImageMazeView extends StackPane implements MazeView {
    
    private final MazeColorMap colorMap;
    private final ImageView imageView;
    private WritableImage image;
    
    public ImageMazeView() {
        colorMap = new CssBasedColorMap(ResourceLoader.getResource(DefaultStyle));
        
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(Bindings.min(widthProperty(), heightProperty()));
        imageView.fitHeightProperty().bind(Bindings.min(widthProperty(), heightProperty()));
        getChildren().add(imageView);
    }

    private void displayMaze(Maze<?> maze) {
        if (image == null || image.widthProperty().intValue() != maze.getWidth() ||
            image.heightProperty().intValue() != maze.getHeight()) {
            image = new WritableImage(maze.getWidth(), maze.getHeight());
        }
        
        int mazeHeight = maze.getHeight();
        PixelWriter pixelWriter = image.getPixelWriter();
        maze.forEach(c -> pixelWriter.setColor(c.getX(), mazeHeight - c.getY() - 1, colorMap.get(c)));
        imageView.setImage(image);
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
    
}
