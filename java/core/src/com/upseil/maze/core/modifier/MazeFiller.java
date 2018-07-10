package com.upseil.maze.core.modifier;

import com.upseil.maze.core.configuration.MazeFillerConfiguration;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Maze;
import com.upseil.maze.core.domain.factory.CellFactory;

public class MazeFiller<M extends Maze<C>, C extends Cell> extends AbstractMazeModifier<M, C, MazeFillerConfiguration> {

    public MazeFiller(CellFactory<C> cellFactory) {
        super(cellFactory);
        setConfiguration(new MazeFillerConfiguration());
    }
    
    public MazeFiller(CellFactory<C> cellFactory, CellType fillType) {
        super(cellFactory);
        
        MazeFillerConfiguration configuration = new MazeFillerConfiguration();
        configuration.setFillType(fillType);
        setConfiguration(configuration);
    }

    @Override
    public M modify(M maze) {
        if (getConfiguration() == null) {
            throw new IllegalStateException("No configuration has been given");
        }
        
        final CellType fillType = getConfiguration().getFillType();
        maze.forEachPoint((x, y) -> {
            if (maze.getCell(x, y) == null) {
                maze.setCell(cellFactory.create(x, y, fillType));
            }
        });
        return maze;
    }
    
}
