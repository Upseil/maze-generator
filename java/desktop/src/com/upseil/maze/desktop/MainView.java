package com.upseil.maze.desktop;

import com.upseil.maze.desktop.display.MazeView;
import com.upseil.maze.desktop.event.MazeGeneratedEvent;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    
    @FXML private Menu menu;
    @FXML private MazeView mazeView;
    
    public MainView() {
        Launcher.getResourceLoader().loadFXML(this, this, "/view/MainView.fxml");
    }
    
    @FXML
    private void initialize() {
//        Region center = (Region) getCenter();
//        mazeView.fitWidthProperty().bind(Bindings.min(center.widthProperty(), center.heightProperty()));
//        mazeView.fitHeightProperty().bind(Bindings.min(center.widthProperty(), center.heightProperty()));
        
        addEventHandler(MazeGeneratedEvent.MazeGenerated, e -> mazeView.setMaze(e.getMaze()));
    }
    
}
