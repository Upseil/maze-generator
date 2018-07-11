package com.upseil.maze.core.test.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.Cell;
import com.upseil.maze.core.domain.CellType;

class TestCell {
    
    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new Cell(0, 0, null));
        assertDoesNotThrow(() -> new Cell(-1, -1, CellType.Floor));
    }
    
}
