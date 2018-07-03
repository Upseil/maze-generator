package com.upseil.maze.core.configuration;

public interface Configurable<C> {
    
    C getConfiguration();
    void setConfiguration(C configuration);
    
}
