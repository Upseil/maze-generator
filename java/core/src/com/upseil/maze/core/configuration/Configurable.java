package com.upseil.maze.core.configuration;

public interface Configurable<C extends Configuration> {
    
    C getConfiguration();
    void setConfiguration(C configuration);
    
}
