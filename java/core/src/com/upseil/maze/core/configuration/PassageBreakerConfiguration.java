package com.upseil.maze.core.configuration;

public class PassageBreakerConfiguration {
    
    private int amount = -1;
    private int minimumShortcutDistance = 0;
    
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
    
}
