package com.upseil.maze.core.modifier;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;

public class MazeFiller<M extends Maze<C>, C extends Cell> implements MazeModifier<M, C> {
    
    private final CellFactory<C> cellFactory;
    private CellType fillType;

    public MazeFiller(CellFactory<C> cellFactory, CellType fillType) {
        this.cellFactory = cellFactory;
        this.fillType = fillType;
    }

    @Override
    public M modify(M maze) {
        maze.forEachPoint((x, y) -> {
            if (maze.getCell(x, y) == null) {
                maze.setCell(x, y, cellFactory.create(x, y, fillType));
            }
        });
        return maze;
    }
    
}
