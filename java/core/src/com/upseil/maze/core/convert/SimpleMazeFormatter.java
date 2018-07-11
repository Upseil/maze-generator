package com.upseil.maze.core.convert;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.Maze;

public class SimpleMazeFormatter implements MazeFormatter<Maze> {
    
    private String cellDelimiter;
    private char nullCellSign;
    
    public SimpleMazeFormatter() {
        this(" ", ' ');
    }
    
    public SimpleMazeFormatter(String cellDelimiter, char nullCellSign) {
        this.cellDelimiter = cellDelimiter;
        this.nullCellSign = nullCellSign;
    }

    public String getCellDelimiter() {
        return cellDelimiter;
    }

    public void setCellDelimiter(String cellDelimiter) {
        this.cellDelimiter = cellDelimiter;
    }

    @Override
    public String convert(Maze maze) {
        StringBuilder builder = new StringBuilder();
        for (int y = maze.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < maze.getWidth(); x++) {
                if (x > 0) {
                    builder.append(cellDelimiter);
                }
                builder.append(format(maze.getCell(x, y)));
            }
            if (y != 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private char format(Cell cell) {
        if (cell == null) {
            return nullCellSign;
        }
        
        char initial = cell.getType().getName().charAt(0);
        return Character.toUpperCase(initial);
    }
    
}
