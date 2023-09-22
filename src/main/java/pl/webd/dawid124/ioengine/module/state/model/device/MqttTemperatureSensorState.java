package pl.webd.dawid124.ioengine.module.state.model.device;

public class MqttTemperatureSensorState extends DeviceState {

    private double battery;
    private double humidity;
    private double temperature;

    public MqttTemperatureSensorState(String ioId, String name, EDeviceStateType ioType) {
        super(ioId, name, ioType);
        this.battery = 0;
        this.humidity = 0;
        this.temperature = 0;
    }

    public MqttTemperatureSensorState(String ioId, String name, EDeviceStateType ioType,
                                      double battery, double humidity, double temperature) {
        super(ioId, name, ioType);
        this.battery = battery;
        this.humidity = humidity;
        this.temperature = temperature;
    }

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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
