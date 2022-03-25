package pl.webd.dawid124.ioengine.service;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.devices.driver.config.ELocalDriverLocation;
import pl.webd.dawid124.ioengine.home.devices.driver.config.EPicoDriverLocation;
import pl.webd.dawid124.ioengine.home.devices.driver.config.LocalDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.configuration.LocalDriverConfiguration;
import pl.webd.dawid124.ioengine.home.devices.driver.configuration.PicoDriverConfiguration;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.IDriver;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.LocalDriver;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.PicoDriver;
import pl.webd.dawid124.ioengine.home.devices.output.BlindDevice;
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
        LocalDriver piLocal = new LocalDriver("pi-master");

        drivers.add(floor1Driver0);
        drivers.add(floor1Driver1);
        drivers.add(floor2Driver0);
        drivers.add(piLocal);

        PicoDriverConfiguration floor1Driver0ExpanderA = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver0Local = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));
        LocalDriverConfiguration localPi = new LocalDriverConfiguration(piLocal, new LocalDriverConfig(ELocalDriverLocation.IO));

        addDevice(new RgbwDevice("rgbw-tv", "TV", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-kitchen", "Kuchnia", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-dinning1", "Jadalnia 1", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-dinning2", "Jadalnia 2", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-office", "Biuro", floor1Driver0ExpanderA, 0, 1, 2, 3));

        addDevice(new RgbwNeoDevice("neo-kitchen", "Kuchnia góra", floor1Driver0Local, 25));
        addDevice(new RgbwNeoDevice("neo-celling", "Sufit", floor1Driver0Local, 24));


        addDevice(new BlindDevice("f1-hs", "HS salon", localPi, RaspiPin.GPIO_15, RaspiPin.GPIO_16));
        addDevice(new BlindDevice("f1-hs-corner", "HS Salon róg", localPi, RaspiPin.GPIO_04, RaspiPin.GPIO_05));
        addDevice(new BlindDevice("f1-fix-360", "FIX Salon 360", localPi, RaspiPin.GPIO_00, RaspiPin.GPIO_02));
        addDevice(new BlindDevice("f1-fix-360-corner", "FIX Salon 360 róg", localPi, RaspiPin.GPIO_03, RaspiPin.GPIO_12));
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
