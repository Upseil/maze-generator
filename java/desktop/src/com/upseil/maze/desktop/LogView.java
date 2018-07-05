package com.upseil.maze.desktop;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import com.upseil.maze.desktop.util.GenericStringFormatter;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LogView extends BorderPane {

    private final LogHandler handler;
    private final Map<Level, Color> logLevelColorMap;
    
    @FXML private ResourceBundle resources;
    @FXML private ComboBox<Level> severityBox;
    @FXML private ToggleButton detachButton;
    @FXML private ScrollPane consoleScrollPane;
    @FXML private TextFlow console;
    
    private BorderPane parent;
    
    public LogView() {
        handler = new LogHandler();
        
        logLevelColorMap = new HashMap<>();
        logLevelColorMap.put(Level.WARNING, Color.DARKORANGE);
        logLevelColorMap.put(Level.SEVERE, Color.RED);
        logLevelColorMap.put(Level.FINE, Color.GRAY);
        logLevelColorMap.put(Level.FINER, Color.GRAY);
        logLevelColorMap.put(Level.FINEST, Color.GRAY);
        
        Launcher.getResourceLoader().loadFXML(this, this, "/view/LogView.fxml");
    }
    
    @FXML
    private void initialize() {
        severityBox.getItems().addAll(Level.SEVERE, Level.WARNING, Level.INFO, Level.FINE, Level.ALL);
        severityBox.setConverter(new GenericStringFormatter<>(level -> {
            String name = level.getLocalizedName();
            return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        }));
        severityBox.valueProperty().addListener((o, oV, nV) -> handler.setLevel(nV));
        severityBox.setValue(Level.WARNING);
        
        detachButton.textProperty().bind(Bindings.when(detachButton.selectedProperty())
                                                 .then(resources.getString("detached"))
                                                 .otherwise(resources.getString("detach")));
        detachButton.selectedProperty().addListener((o, oV, nV) -> detach(nV));
        
        console.heightProperty().addListener((o, oB, nV) -> consoleScrollPane.setVvalue(1));
    }

    private void detach(boolean detach) {
        if (detach) {
            parent = (BorderPane) getParent();
            parent.getChildren().remove(this);
            
            this.getStyleClass().remove("border-top");
            this.setLayoutX(0);
            this.setLayoutY(0);
            
            Scene scene = new Scene(this, getWidth(), getHeight());
            scene.getStylesheets().addAll(parent.getScene().getStylesheets());
            
            Stage stage = new Stage();
            stage.setTitle(resources.getString("title") + " - " + resources.getString("log"));
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> detachButton.setSelected(false));
            
            Window parentWindow = parent.getScene().getWindow();
            parentWindow.setHeight(parentWindow.getHeight() - getHeight());
            stage.setX(parentWindow.getX());
            stage.setY(parentWindow.getY() + parentWindow.getHeight());
            
            stage.show();
        } else {
            ((Stage) this.getScene().getWindow()).close();
            
            Window parentWindow = parent.getScene().getWindow();
            parentWindow.setHeight(parentWindow.getHeight() + getPrefHeight());
            
            this.getStyleClass().add("border-top");
            parent.setBottom(this);
            parent = null;
        }
    }

    private void addText(final Text text) {
        if (Platform.isFxApplicationThread()) {
            console.getChildren().add(text);
        } else {
            Platform.runLater(() -> addText(text));
        }
    }
    
    public Handler getHandler() {
        return handler;
    }
    
    @FXML
    public void clearLog() {
        console.getChildren().clear();
    }
    
    private final ObjectProperty<Formatter> formatter = new SimpleObjectProperty<Formatter>(this, "formatter", new SimpleFormatter());
    public final ObjectProperty<Formatter> formatterProperty() {
        return this.formatter;
    }
    public final Formatter getFormatter() {
        return this.formatterProperty().get();
    }
    public final void setFormatter(final Formatter formatter) {
        this.formatterProperty().set(formatter);
    }
    
    private class LogHandler extends Handler {
        
        private boolean closed;

        @Override
        public void publish(LogRecord record) {
            if (!isLoggable(record) || closed) return;
            
            Color color = logLevelColorMap.get(record.getLevel());
            if (color == null) {
                color = Color.BLACK;
            }
            Text text = new Text(LogView.this.getFormatter().format(record));
            text.setFill(color);
            addText(text);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
            flush();
            closed = true;
        }
        
    }
    
    
}
