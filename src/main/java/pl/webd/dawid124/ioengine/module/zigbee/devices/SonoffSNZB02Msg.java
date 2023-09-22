package pl.webd.dawid124.ioengine.module.zigbee.devices;

public class SonoffSNZB02Msg {
    private double battery;
    private double humidity;
    private double linkquality;
    private double temperature;
    private double voltage;

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

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
