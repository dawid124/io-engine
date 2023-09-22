package pl.webd.dawid124.ioengine.module.structure.model;

import java.time.LocalTime;

public class TemperatureRange {
    private String id;
    private String name;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private double temperature;
    private boolean allTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getHourFrom() {
        return hourFrom;
    }

    public void setHourFrom(LocalTime hourFrom) {
        this.hourFrom = hourFrom;
    }

    public LocalTime getHourTo() {
        return hourTo;
    }

    public void setHourTo(LocalTime hourTo) {
        this.hourTo = hourTo;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isAllTime() {
        return allTime;
    }

    public void setAllTime(boolean allTime) {
        this.allTime = allTime;
    }
}
