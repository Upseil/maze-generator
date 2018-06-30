package com.upseil.maze.desktop;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Launcher extends Application {
    
    private static final String LogFile = "maze-generator.log";
    private static final String StylesheetFile = "style/default.css";

    private static Logger logger;
    private static ResourceLoader resourceLoader;
    
    public static void main(String[] args) throws IOException {
        Logger globalLogger = LogManager.getLogManager().getLogger("");
        FileHandler fileLogger;
        try {
            fileLogger = new FileHandler(LogFile);
            fileLogger.setFormatter(new SimpleFormatter());
            fileLogger.setEncoding("UTF-8");
            globalLogger.addHandler(fileLogger);
        } catch (SecurityException | IOException e) {
            globalLogger.log(Level.SEVERE, "Unable to setup FileHandler for logging", e);
        }
        logger = Logger.getLogger(Launcher.class.getName());
        
        Locale locale = Locale.ENGLISH;
        Locale.setDefault(locale);
        try {
            resourceLoader = new ResourceLoader(new File("lang"), "maze-generator", locale);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Error initializing the resource loader", e);
            throw e;
        }
        
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = resourceLoader.loadFXML("/view/MainView.fxml");
        Scene scene = new Scene(root, 800, 800);
        scene.getStylesheets().setAll(ResourceLoader.getResource(StylesheetFile).toExternalForm());

        primaryStage.setTitle(resourceLoader.getResourceBundle().getString("title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
    
}
