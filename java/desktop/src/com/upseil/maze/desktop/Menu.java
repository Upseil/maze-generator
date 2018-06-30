package com.upseil.maze.desktop;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class Menu extends MenuBar {
    
    private static final Logger logger = Logger.getLogger(Menu.class.getName());
    
    public Menu() {
        Launcher.getResourceLoader().loadFXML(this, this, "/view/Menu.fxml");
    }
    
    @FXML
    private void generate() {
        logger.log(Level.INFO, "Generating new maze");
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
    
}
