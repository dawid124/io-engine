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
        PicoDriver floor0Driver0 = new PicoDriver("IO-DRIVER-F0-0");
        PicoDriver test = new PicoDriver("IO-DRIVER-TEST");
        LocalDriver piLocal = new LocalDriver("pi-master");

        drivers.add(floor1Driver0);
        drivers.add(floor1Driver1);
        drivers.add(floor2Driver0);
        drivers.add(floor0Driver0);
        drivers.add(test);
        drivers.add(piLocal);

        PicoDriverConfiguration floor1Driver0ExpanderA = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver0ExpanderB = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver0Local = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor1Driver1ExpanderA = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver1ExpanderB = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver1Local = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration testExpanderA = new PicoDriverConfiguration(test, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration testExpanderB = new PicoDriverConfiguration(test, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration testLocal = new PicoDriverConfiguration(test, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor0Driver0Local = new PicoDriverConfiguration(floor0Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        LocalDriverConfiguration localPi = new LocalDriverConfiguration(piLocal, new LocalDriverConfig(ELocalDriverLocation.IO));

        addDevice(new RgbwDevice("rgbw-tv", "TV", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-dinner", "Jadalnia 2", floor1Driver0ExpanderA, 4, 5, 6, 7));
        addDevice(new RgbwDevice("rgbw-office", "Biuro", floor1Driver0ExpanderA, 8, 9, 10, 11));
        addDevice(new RgbwDevice("rgbw-kitchen", "Jadalnia 1", floor1Driver0ExpanderA, 12, 13, 14, 15));

        addDevice(new RgbwDevice("rgbw-lobby", "Korytarz", floor1Driver0ExpanderB, 8, 9, 10, 11));
        addDevice(new RgbwwDevice("rgbww-kitchen", "Kuchania szafki", floor1Driver0ExpanderB, 7, 6, 4, 3, 2));
        addDevice(new RgbwDevice("rgbw-panel", "Panele Beton", floor1Driver0ExpanderB, 12, 13, 14, 15));

        addDevice(new NeoDevice("neo-kitchen", "Kuchnia Neo", floor1Driver0Local, 12, 98, false, ENeoType.NEO_GRBW));
        addDevice(new NeoDevice("neo-celling", "Sufit", floor1Driver0Local, 13, 225, false, ENeoType.NEO_GRBW));
        addDevice(new NeoDevice("neo-tv", "Neo TV", floor1Driver0Local, 11, 133, true, ENeoType.NEO_GRBW));


        addDevice(new MotionSensor("pir-office", "Pir Biuro", floor1Driver0Local, 7));
        addDevice(new MotionSensor("pir-lobby", "Pir Korytarz", floor1Driver0Local, 8));
        addDevice(new MotionSensor("pir-kitchen", "Pir Kuchnia", floor1Driver0Local, 10));

// 121 - standard led count
        addDevice(new NeoDevice("neo-wall-1", "Wall 1", floor1Driver1Local, 14, 155, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-2", "Wall 2", floor1Driver1Local, 22, 155, true, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-3", "Wall 3", floor1Driver1Local, 20, 155, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("neo-wall-4", "Wall 4", floor1Driver1Local, 21, 155, true, ENeoType.NEO_GRB));


        addDevice(new RgbwDevice("rgbw-wc1", "WC 1", floor1Driver1ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-wc2", "WC 2", floor1Driver1ExpanderB, 0, 1, 2, 3));
        addDevice(new MotionSensor("pir-wc", "Pir Biuro", floor1Driver1Local, 12));

//        addDevice(new RgbwwDevice("rgbww-kitchen", "Kuchania szafki", floor1Driver0ExpanderB, 7, 6, 5, 4, 3));



        addDevice(new BlindDevice("f1-hs", "HS salon", localPi, RaspiPin.GPIO_15, RaspiPin.GPIO_16));
        addDevice(new BlindDevice("f1-hs-corner", "HS Salon róg", localPi, RaspiPin.GPIO_04, RaspiPin.GPIO_05));
        addDevice(new BlindDevice("f1-fix-360", "FIX Salon 360", localPi, RaspiPin.GPIO_00, RaspiPin.GPIO_02));
        addDevice(new BlindDevice("f1-fix-360-corner", "FIX Salon 360 róg", localPi, RaspiPin.GPIO_03, RaspiPin.GPIO_12));

        addDevice(new BlindDevice("office-left", "Biuro lewa", localPi, RaspiPin.GPIO_13, RaspiPin.GPIO_14));
        addDevice(new BlindDevice("office-right", "Biuro prawa", localPi, RaspiPin.GPIO_21, RaspiPin.GPIO_22));



        addDevice(new MotionSensor("pir-satel-tv", "Satel Pir TV", floor0Driver0Local, 12, true));
        addDevice(new MotionSensor("pir-satel-dinner", "Satel Pir Dinner", floor0Driver0Local, 13, true));
        addDevice(new MotionSensor("pir-satel-lobby", "Satel Pir Lobby", floor0Driver0Local, 14, true));
        addDevice(new MotionSensor("pir-satel-entry", "Satel Pir Entry", floor0Driver0Local, 15, true));
        addDevice(new MotionSensor("pir-satel-lobby-up", "Satel Pir Lobby Up", floor0Driver0Local, 21, true));

//        addDevice(new MotionSensor("pir-stairs-down", "Pir Stairs Down", floor0Driver0Local, 20));
//        addDevice(new MotionSensor("pir-stairs-up", "Pir Stairs Up", floor0Driver0Local, 22));

        addDevice(new SingleColorLedDevice("led-stairs-1", "Stairs 1", floor0Driver0Local, 0));
        addDevice(new SingleColorLedDevice("led-stairs-2", "Stairs 2", floor0Driver0Local, 1));
        addDevice(new SingleColorLedDevice("led-stairs-3", "Stairs 3", floor0Driver0Local, 2));
        addDevice(new SingleColorLedDevice("led-stairs-4", "Stairs 4", floor0Driver0Local, 3));

        addDevice(new SingleColorLedDevice("led-stairs-5", "Stairs 5", floor0Driver0Local, 6));
        addDevice(new SingleColorLedDevice("led-stairs-6", "Stairs 6", floor0Driver0Local, 7));
        addDevice(new SingleColorLedDevice("led-stairs-7", "Stairs 7", floor0Driver0Local, 8));
        addDevice(new SingleColorLedDevice("led-stairs-8", "Stairs 8", floor0Driver0Local, 9));


        addDevice(new NeoDevice("neo-test", "neo test", testLocal, 10, 60, false, ENeoType.NEO_GRBW));
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
