package com.upseil.maze.core.test.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import com.upseil.maze.core.domain.Direction;
import com.upseil.maze.core.domain.Point;

class TestPoint {
    
    @Test
    void testDistancePoint() {
        Point a = new Point(1, 1);
        
        Point b = new Point(2, 1);
        assertThat(a.distance(b), is(1.0));
        assertThat(b.distance(a), is(1.0));
        
        b = new Point(1, 2);
        assertThat(a.distance(b), is(1.0));
        assertThat(b.distance(a), is(1.0));
        
        b = new Point(1 + 4, 1 + 3);
        assertThat(a.distance(b), is(5.0));
        assertThat(b.distance(a), is(5.0));
    }
    
    @Test
    void testTranslate() {
        Point p = new Point();
        
        p.translate(1, 1);
        assertThat(p, is(new Point()));
        
        p = p.translateX(1);
        assertThat(p, is(new Point(1, 0)));
        
        p = p.translateY(1);
        assertThat(p, is(new Point(1, 1)));
        
        p = p.translate(1, 1);
        assertThat(p, is(new Point(2, 2)));
        
        p = p.translate(Direction.South);
        assertThat(p, is(new Point(2, 1)));
        
        p = p.translate(Direction.NorthEast, 3);
        assertThat(p, is(new Point(5, 4)));
    }
    
}
