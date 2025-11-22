package pl.webd.dawid124.ioengine.module.automation.model;

public enum HeatingState {
    IDLE,       // Temperature OK, system inactive
    HEATING,    // Active heating
    WAITING     // Waiting and observing temperature rise
}
