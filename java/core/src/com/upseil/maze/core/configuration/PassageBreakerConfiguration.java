package com.upseil.maze.core.configuration;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.upseil.maze.core.domain.CellType;

public class PassageBreakerConfiguration implements ModifierConfiguration {
    
    public static final String Type = "PassageBreaker";
    
    private int amount = -1;
    private int minimumShortcutDistance = 0;
    private CellType searchType = CellType.Wall;
    private CellType passageType = CellType.Floor;
    private Set<CellType> walkableTypes = Collections.singleton(CellType.Floor);
    
    @Override
    public String getType() {
        return Type;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public int getMinimumShortcutDistance() {
        return minimumShortcutDistance;
    }
    
    public void setMinimumShortcutDistance(int minimumShortcutDistance) {
        this.minimumShortcutDistance = minimumShortcutDistance;
    }

    public CellType getSearchType() {
        return searchType;
    }

    public void setSearchType(CellType searchType) {
        Objects.requireNonNull(searchType, "search type must not be null");
        this.searchType = searchType;
    }

    public CellType getPassageType() {
        return passageType;
    }

    public void setPassageType(CellType passageType) {
        Objects.requireNonNull(passageType, "passage type must not be null");
        this.passageType = passageType;
    }

    public Iterable<CellType> getWalkableTypes() {
        return walkableTypes;
    }

    public void setWalkableTypes(Set<CellType> walkableTypes) {
        Objects.requireNonNull(walkableTypes, "walkable types must not be null");
        this.walkableTypes = walkableTypes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append("[amount=").append(amount).append(", minimumShortcutDistance=").append(minimumShortcutDistance)
                .append(", searchType=").append(searchType).append(", passageType=").append(passageType).append(", walkableTypes=").append(walkableTypes)
                .append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + minimumShortcutDistance;
        result = prime * result + ((passageType == null) ? 0 : passageType.hashCode());
        result = prime * result + ((searchType == null) ? 0 : searchType.hashCode());
        result = prime * result + ((walkableTypes == null) ? 0 : walkableTypes.hashCode());
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
        PassageBreakerConfiguration other = (PassageBreakerConfiguration) obj;
        if (amount != other.amount)
            return false;
        if (minimumShortcutDistance != other.minimumShortcutDistance)
            return false;
        if (passageType == null) {
            if (other.passageType != null)
                return false;
        } else if (!passageType.equals(other.passageType))
            return false;
        if (searchType == null) {
            if (other.searchType != null)
                return false;
        } else if (!searchType.equals(other.searchType))
            return false;
        if (walkableTypes == null) {
            if (other.walkableTypes != null)
                return false;
        } else if (!walkableTypes.equals(other.walkableTypes))
            return false;
        return true;
    }

}
