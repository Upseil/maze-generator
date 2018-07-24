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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append("[strategy=").append(strategy).append(", searchType=").append(searchType).append(", fillType=")
                .append(fillType).append(", percentage=").append(percentage).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fillType == null) ? 0 : fillType.hashCode());
        long temp;
        temp = Double.doubleToLongBits(percentage);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((searchType == null) ? 0 : searchType.hashCode());
        result = prime * result + ((strategy == null) ? 0 : strategy.hashCode());
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
        DeadEndStripperConfiguration other = (DeadEndStripperConfiguration) obj;
        if (fillType == null) {
            if (other.fillType != null)
                return false;
        } else if (!fillType.equals(other.fillType))
            return false;
        if (Double.doubleToLongBits(percentage) != Double.doubleToLongBits(other.percentage))
            return false;
        if (searchType == null) {
            if (other.searchType != null)
                return false;
        } else if (!searchType.equals(other.searchType))
            return false;
        if (strategy != other.strategy)
            return false;
        return true;
    }
    
}
