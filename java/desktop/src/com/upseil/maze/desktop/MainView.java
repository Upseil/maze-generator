package com.upseil.maze.desktop;

import java.util.logging.LogManager;

import com.upseil.maze.desktop.display.MazeView;
import com.upseil.maze.desktop.event.MazeGeneratedEvent;
import com.upseil.maze.desktop.logging.ConsoleFormatter;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    
    @FXML private MazeView mazeView;
    @FXML private LogView logView;
    
    public MainView() {
        Launcher.getResourceLoader().loadFXML(this, this, "/view/MainView.fxml");
    }
    
    @FXML
    private void initialize() {
        addEventHandler(MazeGeneratedEvent.MazeGenerated, e -> mazeView.setMaze(e.getMaze()));
        
        logView.setFormatter(new ConsoleFormatter("%tT - "));
        LogManager.getLogManager().getLogger("").addHandler(logView.getHandler());
    }
    
}
