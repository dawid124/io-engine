package pl.webd.dawid124.ioengine.module.zigbee.devices;

import pl.webd.dawid124.ioengine.module.zigbee.ZigbeeMessage;

public class SonoffSNZB02Msg extends ZigbeeMessage {
    private double battery;
    private double humidity;

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
}
