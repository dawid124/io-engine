package pl.webd.dawid124.ioengine.module.automation.model;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Memory of heating system state for zone with cyclic logic
 */
public class ZoneHeatingMemory {
    private static final int MAX_CYCLE_HISTORY = 10;
    private static final int DEFAULT_HEATING_DURATION_MINUTES = 15;
    private static final int MIN_WAITING_DURATION_MINUTES = 15;
    private static final int MID_WAITING_DURATION_MINUTES = 20;
    private static final int MAX_WAITING_DURATION_MINUTES = 30;

    private String zoneId;
    private HeatingState state;
    private LocalDateTime stateStartTime;
    private double temperatureAtHeatingStart;
    private double temperatureAtWaitingStart;
    private int currentWaitingDurationMinutes;
    private LinkedList<HeatingCycle> recentCycles;
    private HeatingCycle currentCycle;

    public ZoneHeatingMemory(String zoneId) {
        this.zoneId = zoneId;
        this.state = HeatingState.IDLE;
        this.stateStartTime = LocalDateTime.now();
        this.currentWaitingDurationMinutes = MIN_WAITING_DURATION_MINUTES;
        this.recentCycles = new LinkedList<>();
    }

    public void startHeating(double currentTemperature) {
        this.state = HeatingState.HEATING;
        this.stateStartTime = LocalDateTime.now();
        this.temperatureAtHeatingStart = currentTemperature;
        this.currentCycle = new HeatingCycle(LocalDateTime.now(), currentTemperature);
    }

    public void startWaiting(double currentTemperature) {
        this.state = HeatingState.WAITING;
        this.stateStartTime = LocalDateTime.now();
        this.temperatureAtWaitingStart = currentTemperature;
    }

    public void setIdle() {
        if (currentCycle != null && state == HeatingState.WAITING) {
            // Complete current cycle before transitioning to IDLE
            currentCycle.completeWaiting(temperatureAtWaitingStart, currentWaitingDurationMinutes);
            addCycleToHistory(currentCycle);
            currentCycle = null;
        }
        this.state = HeatingState.IDLE;
        this.stateStartTime = LocalDateTime.now();
    }

    /**
     * Calculates optimal waiting time based on temperature rise
     * @param temperatureRise temperature rise during last WAITING period
     * @return waiting time in minutes
     */
    public int calculateNextWaitingDuration(double temperatureRise) {
        if (temperatureRise > 0.6) {
            return MAX_WAITING_DURATION_MINUTES; // Fast rise - long wait
        } else if (temperatureRise >= 0.3) {
            return MID_WAITING_DURATION_MINUTES; // Medium rise
        } else {
            return MIN_WAITING_DURATION_MINUTES; // Slow rise - short wait
        }
    }

    public void updateWaitingDuration(int minutes) {
        this.currentWaitingDurationMinutes = minutes;
    }

    private void addCycleToHistory(HeatingCycle cycle) {
        recentCycles.addFirst(cycle);
        if (recentCycles.size() > MAX_CYCLE_HISTORY) {
            recentCycles.removeLast();
        }
    }

    public boolean isHeatingDurationElapsed() {
        if (state != HeatingState.HEATING) return false;
        return java.time.Duration.between(stateStartTime, LocalDateTime.now()).toMinutes() >= DEFAULT_HEATING_DURATION_MINUTES;
    }

    public boolean isWaitingDurationElapsed() {
        if (state != HeatingState.WAITING) return false;
        return java.time.Duration.between(stateStartTime, LocalDateTime.now()).toMinutes() >= currentWaitingDurationMinutes;
    }

    // Getters
    public String getZoneId() {
        return zoneId;
    }

    public HeatingState getState() {
        return state;
    }

    public LocalDateTime getStateStartTime() {
        return stateStartTime;
    }

    public double getTemperatureAtHeatingStart() {
        return temperatureAtHeatingStart;
    }

    public double getTemperatureAtWaitingStart() {
        return temperatureAtWaitingStart;
    }

    public int getCurrentWaitingDurationMinutes() {
        return currentWaitingDurationMinutes;
    }

    public LinkedList<HeatingCycle> getRecentCycles() {
        return recentCycles;
    }

    public HeatingCycle getCurrentCycle() {
        return currentCycle;
    }
}
