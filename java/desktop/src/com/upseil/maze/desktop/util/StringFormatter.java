package com.upseil.maze.desktop.util;

import javafx.util.StringConverter;

public abstract class StringFormatter<T> extends StringConverter<T> {

    @Override
    public T fromString(String string) {
        throw new UnsupportedOperationException();
    }
    
}
