package com.upseil.maze.core.configuration;

import java.util.Objects;

import com.upseil.maze.core.domain.CellType;

public class DeadEndStripperConfiguration implements ModifierConfiguration {
    
    public static final String Type = "DeadEndStripper";
    
    public enum Strategy { BreadthFirst, DepthFirst }
    
    private Strategy strategy = Strategy.BreadthFirst;
    private CellType searchType = CellType.Floor;
    private CellType fillType = CellType.Wall;
    private double percentage = 1;
    
    @Override
    public String getType() {
        return Type;
    }
    
    public Strategy getStrategy() {
        return strategy;
    }
    
    public void setStrategy(Strategy strategy) {
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.strategy = strategy;
    }
    
    public CellType getSearchType() {
        return searchType;
    }

    public void setSearchType(CellType searchType) {
        Objects.requireNonNull(searchType, "search type must not be null");
        this.searchType = searchType;
    }

    public CellType getFillType() {
        return fillType;
    }

    public void setFillType(CellType fillType) {
        Objects.requireNonNull(fillType, "fill type must not be null");
        this.fillType = fillType;
    }

    public double getPercentage() {
        return percentage;
    }
    
    public void setPercentage(double percentage) {
        this.percentage = percentage;
        if (this.percentage > 1) {
            this.percentage = 1;
        } else if (this.percentage < 0) {
            this.percentage = 0;
        }
    }
    
}
