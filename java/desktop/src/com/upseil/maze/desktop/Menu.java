package com.upseil.maze.desktop;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.upseil.maze.desktop.event.MazeGeneratedEvent;
import com.upseil.maze.domain.Maze;
import com.upseil.maze.domain.factory.CellFactory;
import com.upseil.maze.generator.BacktrackingLabyrinthGenerator;
import com.upseil.maze.generator.MazeGenerator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class Menu extends MenuBar {
    
    private static final Logger logger = Logger.getLogger(Menu.class.getName());
    
    private final MazeGenerator generator;
    
    public Menu() {
        Launcher.getResourceLoader().loadFXML(this, this, "/view/Menu.fxml");
        
        generator = new BacktrackingLabyrinthGenerator(new Random(), CellFactory.Default);
    }
    
    @FXML
    private void generate() {
        Maze maze = generator.generate(15, 15);
        this.fireEvent(new MazeGeneratedEvent(maze));
    }

    @FXML
    private void exit() {
        logger.log(Level.INFO, "Exiting application");
        Platform.exit();
    }
    
}
