package pl.webd.dawid124.ioengine.module.automation.model;

import java.time.LocalDateTime;

/**
 * Represents a single heating cycle with temperature rise data
 */
public class HeatingCycle {
    private LocalDateTime timestamp;
    private double tempBeforeHeating;
    private double tempAfterWaiting;
    private double tempRise;
    private int waitingDurationMinutes;

    public HeatingCycle(LocalDateTime timestamp, double tempBeforeHeating) {
        this.timestamp = timestamp;
        this.tempBeforeHeating = tempBeforeHeating;
    }

    public void completeWaiting(double tempAfterWaiting, int waitingDurationMinutes) {
        this.tempAfterWaiting = tempAfterWaiting;
        this.tempRise = tempAfterWaiting - tempBeforeHeating;
        this.waitingDurationMinutes = waitingDurationMinutes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getTempBeforeHeating() {
        return tempBeforeHeating;
    }

    public double getTempAfterWaiting() {
        return tempAfterWaiting;
    }

    public double getTempRise() {
        return tempRise;
    }

    public int getWaitingDurationMinutes() {
        return waitingDurationMinutes;
    }
}
