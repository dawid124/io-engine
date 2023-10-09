package pl.webd.dawid124.ioengine.module.zigbee;

public abstract class ZigbeeMessage {
    private double linkquality;
    private double temperature;
    private double voltage;

    public double getLinkquality() {
        return linkquality;
    }

    public void setLinkquality(double linkquality) {
        this.linkquality = linkquality;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
}
