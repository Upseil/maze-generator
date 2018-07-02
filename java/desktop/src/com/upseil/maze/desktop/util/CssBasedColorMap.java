package com.upseil.maze.desktop.util;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import com.sun.javafx.css.CompoundSelector;
import com.sun.javafx.css.Declaration;
import com.sun.javafx.css.Rule;
import com.sun.javafx.css.Selector;
import com.sun.javafx.css.SimpleSelector;
import com.sun.javafx.css.Stylesheet;
import com.sun.javafx.css.parser.CSSParser;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

@SuppressWarnings("restriction")
public class CssBasedColorMap extends MazeColorMap {
    
    private static final String BackgroundPropertyName = "-fx-background-color";

    public CssBasedColorMap(URL stylesheetUrl) {
        CSSParser parser = new CSSParser();
        try {
            Stylesheet stylesheet = parser.parse(stylesheetUrl);
            for (Rule rule : stylesheet.getRules()) {
                Optional<Declaration> background = rule.getDeclarations().stream()
                                                       .filter(d -> d.getProperty().equals(BackgroundPropertyName))
                                                       .findFirst();
                background.ifPresent(declaration -> {
                    Paint[] convertedValue = (Paint[]) declaration.getParsedValue().convert(null);
                    Color color = (Color) convertedValue[0];
                    for (Selector selector : rule.getSelectors()) {
                        if (selector instanceof SimpleSelector) {
                            registerColorFor(color, (SimpleSelector) selector);
                        } else if (selector instanceof CompoundSelector) {
                            CompoundSelector compoundSelector = (CompoundSelector) selector;
                            compoundSelector.getSelectors().forEach(s -> registerColorFor(color, s));
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error parsing the given URL as stylesheet", e);
        }
    }

    private void registerColorFor(Color color, SimpleSelector selector) {
        String selectorName = selector.getName();
        String selectorId = selector.getId();
        if ((selectorName == null || selectorName.isEmpty() || selectorName.equals("*")) &&
            (selectorId == null || selectorId.isEmpty())) {
            selector.getStyleClasses();
            for (String styleClass : selector.getStyleClasses()) {
                put(styleClass, color);
            }
        }
    }
    
}
