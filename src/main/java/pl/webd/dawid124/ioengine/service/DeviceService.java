package pl.webd.dawid124.ioengine.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.devices.driver.config.EIoDriverLocation;
import pl.webd.dawid124.ioengine.home.devices.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.configuration.PicoDriverConfiguration;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.IDriver;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.PicoDriver;
import pl.webd.dawid124.ioengine.home.devices.output.IDevice;
import pl.webd.dawid124.ioengine.home.devices.output.RgbwDevice;
import pl.webd.dawid124.ioengine.home.devices.output.RgbwNeoDevice;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private List<IDriver> drivers;
    private HashMap<String, IDevice> devices;

    public DeviceService() {
        this.drivers = new ArrayList<>();
        this.devices = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        PicoDriver floor1Driver0 = new PicoDriver("io-driver-f1-0");
        PicoDriver floor1Driver1 = new PicoDriver("io-driver-f1-1");
        PicoDriver floor2Driver0 = new PicoDriver("io-driver-f2-0");

        drivers.add(floor1Driver0);
        drivers.add(floor1Driver1);
        drivers.add(floor2Driver0);

        PicoDriverConfiguration floor1Driver0ExpanderA = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EIoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver0Local = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EIoDriverLocation.LOCAL));

        addDevice(new RgbwDevice("rgbw-tv", "TV", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-kitchen", "Kuchnia", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-dinning1", "Jadalnia 1", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-dinning2", "Jadalnia 2", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-office", "Biuro", floor1Driver0ExpanderA, 0, 1, 2, 3));

        addDevice(new RgbwNeoDevice("neo-kitchen", "Kuchnia góra", floor1Driver0Local, 25));
        addDevice(new RgbwNeoDevice("neo-celling", "Sufit", floor1Driver0Local, 24));
    }

    private void addDevice(IDevice device) {
        devices.put(device.getId(), device);
    }

    public Map<String, IDevice> fetchAll() {
        return this.devices;
    }

    public Map<String, IDevice> fetchSelected(List<String> ids) {
        return this.devices.entrySet().stream()
                .filter(map -> ids.contains(map.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
