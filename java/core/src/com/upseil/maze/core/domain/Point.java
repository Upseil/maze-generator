package com.upseil.maze.core.domain;

public class Point {
    
    private final int x;
    private final int y;
    
    public Point() {
        this(0, 0);
    }
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public double distance(Point point) {
        return distance(point.x, point.y);
    }
    
    public double distance(int x, int y) {
        double dx = x - this.x;
        double dy = y - this.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    public Point translateX(int x) {
        return translate(x, 0);
    }
    
    public Point translateY(int y) {
        return translate(0, y);
    }
    
    public Point translate(Direction direction) {
        return translate(direction, 1);
    }
    
    public Point translate(Direction direction, int amount) {
        return translate(direction.getDeltaX() * amount, direction.getDeltaY() * amount);
    }
    
    public Point translate(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return equals((Point) obj);
    }
    
    public boolean equals(Point point) {
        return this.x == point.x && this.y == point.y;
    }
    
}