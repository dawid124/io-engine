package pl.webd.dawid124.ioengine.module.device.service;

import com.pi4j.io.gpio.RaspiPin;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.ELocalDriverLocation;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.EPicoDriverLocation;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.LocalDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.LocalDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.PicoDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.IDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.LocalDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.PicoDriver;
import pl.webd.dawid124.ioengine.module.device.model.input.MotionSensor;
import pl.webd.dawid124.ioengine.module.device.model.output.*;

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
        PicoDriver floor1Driver0 = new PicoDriver("IO-DRIVER-F1-0");
        PicoDriver floor1Driver1 = new PicoDriver("IO-DRIVER-F1-1");
        PicoDriver floor2Driver0 = new PicoDriver("IO-DRIVER-F2-0");
        LocalDriver piLocal = new LocalDriver("pi-master");

        drivers.add(floor1Driver0);
        drivers.add(floor1Driver1);
        drivers.add(floor2Driver0);
        drivers.add(piLocal);

        PicoDriverConfiguration floor1Driver0ExpanderA = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver0ExpanderB = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver0Local = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor1Driver1ExpanderA = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver1ExpanderB = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver1Local = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        LocalDriverConfiguration localPi = new LocalDriverConfiguration(piLocal, new LocalDriverConfig(ELocalDriverLocation.IO));

        addDevice(new RgbwDevice("rgbw-tv", "TV", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-dinner", "Jadalnia 2", floor1Driver0ExpanderA, 4, 5, 6, 7));
        addDevice(new RgbwDevice("rgbw-office", "Biuro", floor1Driver0ExpanderA, 8, 9, 10, 11));
        addDevice(new RgbwDevice("rgbw-kitchen", "Jadalnia 1", floor1Driver0ExpanderA, 12, 13, 14, 15));

        addDevice(new NeoDevice("neo-kitchen", "Kuchnia Neo", floor1Driver0Local, 12, 98, false, ENeoType.NEO_GRBW));
        addDevice(new NeoDevice("neo-celling", "Sufit", floor1Driver0Local, 13, 225, false, ENeoType.NEO_GRBW));
        addDevice(new NeoDevice("neo-tv", "Neo TV", floor1Driver0Local, 11, 133, true, ENeoType.NEO_GRBW));


        addDevice(new MotionSensor("pir-office", "Pir Biuro", floor1Driver0Local, 7));
        addDevice(new MotionSensor("pir-lobby", "Pir Korytarz", floor1Driver0Local, 8));
        addDevice(new MotionSensor("pir-kitchen", "Pir Kuchnia", floor1Driver0Local, 10));


        addDevice(new NeoDevice("neo-wall-1", "Wall 1", floor1Driver1Local, 15, 121, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-2", "Wall 2", floor1Driver1Local, 14, 121, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-3", "Wall 3", floor1Driver1Local, 13, 121, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-4", "Wall 4", floor1Driver1Local, 12, 121, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-5", "Wall 5", floor1Driver1Local, 12, 121, false, ENeoType.NEO_GRB));

        addDevice(new RgbwDevice("rgbw-lobby", "Korytarz", floor1Driver0ExpanderB, 8, 9, 10, 11));

        addDevice(new RgbwwDevice("rgbww-kitchen", "Kuchania szafki", floor1Driver0ExpanderB, 7, 6, 5, 4, 3));


        addDevice(new BlindDevice("f1-hs", "HS salon", localPi, RaspiPin.GPIO_15, RaspiPin.GPIO_16));
        addDevice(new BlindDevice("f1-hs-corner", "HS Salon róg", localPi, RaspiPin.GPIO_04, RaspiPin.GPIO_05));
        addDevice(new BlindDevice("f1-fix-360", "FIX Salon 360", localPi, RaspiPin.GPIO_00, RaspiPin.GPIO_02));
        addDevice(new BlindDevice("f1-fix-360-corner", "FIX Salon 360 róg", localPi, RaspiPin.GPIO_03, RaspiPin.GPIO_12));

        addDevice(new BlindDevice("office-left", "Biuro lewa", localPi, RaspiPin.GPIO_13, RaspiPin.GPIO_14));
        addDevice(new BlindDevice("office-right", "Biuro prawa", localPi, RaspiPin.GPIO_21, RaspiPin.GPIO_22));
    }

    private void addDevice(IDevice device) {
        devices.put(device.getId(), device);
    }

    public Map<String, IDevice> fetchAll() {
        return this.devices;
    }

    public Map<String, IDevice> fetchDevicesByDriverId(String deviceId) {
        return this.devices.entrySet().stream()
                .filter(device -> device.getValue().getDriverConfiguration().getDriver().getId().equals(deviceId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, IDevice> fetchSelected(List<String> ids) {
        return this.devices.entrySet().stream()
                .filter(map -> ids.contains(map.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public IDevice fetchDevice(String ioId) {
        return devices.get(ioId);
    }
}
