package com.upseil.maze.desktop.event;

import com.upseil.maze.domain.Maze;

import javafx.event.Event;
import javafx.event.EventType;

public class MazeGeneratedEvent extends Event {
    private static final long serialVersionUID = -3722853192231985736L;

    public static final EventType<MazeGeneratedEvent> MazeGenerated = new EventType<>("MazeGenerated");
    
    private final Maze<?> maze;

    public MazeGeneratedEvent(Maze<?> maze) {
        super(MazeGenerated);
        this.maze = maze;
    }
    
    public Maze<?> getMaze() {
        return maze;
    }
    
}
