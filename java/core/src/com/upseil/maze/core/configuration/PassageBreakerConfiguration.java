package com.upseil.maze.core.configuration;

import java.util.Collections;
import java.util.Objects;

import com.upseil.maze.core.domain.CellType;

public class PassageBreakerConfiguration implements ModifierConfiguration {
    
    public static final String Type = "PassageBreaker";
    
    private int amount = -1;
    private int minimumShortcutDistance = 0;
    private CellType searchType = CellType.Wall;
    private CellType passageType = CellType.Floor;
    private Iterable<CellType> walkableTypes = Collections.singleton(CellType.Floor);
    
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

    public void setWalkableTypes(Iterable<CellType> walkableTypes) {
        Objects.requireNonNull(walkableTypes, "walkable types must not be null");
        this.walkableTypes = walkableTypes;
    }
    
}
