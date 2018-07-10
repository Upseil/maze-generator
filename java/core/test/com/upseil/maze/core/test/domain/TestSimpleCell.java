package com.upseil.maze.core.test.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.CellType;
import com.upseil.maze.core.domain.SimpleCell;

class TestSimpleCell {
    
    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new SimpleCell(0, 0, null));
        assertDoesNotThrow(() -> new SimpleCell(-1, -1, CellType.Floor));
    }
    
}
