package com.upseil.maze.core.modifier;

import com.upseil.maze.core.configuration.MazeFillerConfiguration;
import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.Maze;

public class MazeFiller<M extends Maze> extends AbstractMazeModifier<M, MazeFillerConfiguration> {

    public MazeFiller() {
        setConfiguration(new MazeFillerConfiguration());
    }
    
    public MazeFiller(CellType fillType) {
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
                maze.setCell(new Cell(x, y, fillType));
            }
        });
        return maze;
    }
    
}
