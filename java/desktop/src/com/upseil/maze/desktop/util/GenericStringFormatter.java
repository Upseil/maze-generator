package com.upseil.maze.desktop.util;

import java.util.function.Function;

public class GenericStringFormatter<T> extends StringFormatter<T> {
    
    private final Function<T, String> format;

    public GenericStringFormatter(Function<T, String> format) {
        this.format = format;
    }

    @Override
    public String toString(T object) {
        return format.apply(object);
    }
    
}
