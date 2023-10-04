package pl.webd.dawid124.ioengine.module.device.service;

import com.pi4j.io.gpio.RaspiPin;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.ELocalDriverLocation;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.EPicoDriverLocation;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.LocalDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.LocalDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.PicoDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.ZigbeeDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.IDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.LocalDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.MqttDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.PicoDriver;
import pl.webd.dawid124.ioengine.module.device.model.input.MotionSensor;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.switchs.ESonoff3GangSwitchLocation;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.switchs.Sonoff3GangSwitch;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.temperature.SonoffTemperatureSensor;
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
        PicoDriver floor1Driver2 = new PicoDriver("IO-DRIVER-F1-2");
        PicoDriver floor1Driver5 = new PicoDriver("IO-DRIVER-F1-5");
        PicoDriver floor2Driver0 = new PicoDriver("IO-DRIVER-F2-0");
        PicoDriver floor0Driver0 = new PicoDriver("IO-DRIVER-F0-0");
        PicoDriver test = new PicoDriver("IO-DRIVER-TEST");
        LocalDriver piLocal = new LocalDriver("pi-master");
        MqttDriver zigbee2mqttUp = new MqttDriver("zigbee2mqttUp", "zigbee2mqttUp");
        MqttDriver zigbee2mqttDown = new MqttDriver("zigbee2mqttDown", "zigbee2mqttDown");

        drivers.add(floor1Driver0);
        drivers.add(floor1Driver1);
        drivers.add(floor1Driver2);
        drivers.add(floor1Driver5);
        drivers.add(floor2Driver0);
        drivers.add(floor0Driver0);
        drivers.add(test);
        drivers.add(piLocal);
        drivers.add(zigbee2mqttUp);
        drivers.add(zigbee2mqttDown);

        ZigbeeDriverConfiguration zigbeeDriverConfigurationUp = new ZigbeeDriverConfiguration(zigbee2mqttUp);
        ZigbeeDriverConfiguration zigbeeDriverConfigurationDown = new ZigbeeDriverConfiguration(zigbee2mqttDown);

        PicoDriverConfiguration floor1Driver0ExpanderA = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver0ExpanderB = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver0Local = new PicoDriverConfiguration(floor1Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor1Driver1ExpanderA = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver1ExpanderB = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver1Local = new PicoDriverConfiguration(floor1Driver1, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor1Driver2ExpanderA = new PicoDriverConfiguration(floor1Driver2, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor1Driver2ExpanderB = new PicoDriverConfiguration(floor1Driver2, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration floor1Driver2Local = new PicoDriverConfiguration(floor1Driver2, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor1Driver5Local = new PicoDriverConfiguration(floor1Driver5, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration testExpanderA = new PicoDriverConfiguration(test, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration testExpanderB = new PicoDriverConfiguration(test, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));
        PicoDriverConfiguration testLocal = new PicoDriverConfiguration(test, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor0Driver0Local = new PicoDriverConfiguration(floor0Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));

        PicoDriverConfiguration floor2Driver0Local = new PicoDriverConfiguration(floor2Driver0, new PicoDriverConfig(EPicoDriverLocation.LOCAL));
        PicoDriverConfiguration floor2Driver0ExpanderA = new PicoDriverConfiguration(floor2Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_A));
        PicoDriverConfiguration floor2Driver0ExpanderB = new PicoDriverConfiguration(floor2Driver0, new PicoDriverConfig(EPicoDriverLocation.EXPANDER_B));

        LocalDriverConfiguration localPi = new LocalDriverConfiguration(piLocal, new LocalDriverConfig(ELocalDriverLocation.IO));

//        LOCAL PI

        addDevice(new BlindDevice("f1-hs", "HS salon", localPi, RaspiPin.GPIO_15, RaspiPin.GPIO_16));
        addDevice(new BlindDevice("f1-hs-corner", "HS Salon róg", localPi, RaspiPin.GPIO_04, RaspiPin.GPIO_05));
        addDevice(new BlindDevice("f1-fix-360", "FIX Salon 360", localPi, RaspiPin.GPIO_00, RaspiPin.GPIO_02));
        addDevice(new BlindDevice("f1-fix-360-corner", "FIX Salon 360 róg", localPi, RaspiPin.GPIO_03, RaspiPin.GPIO_12));

        addDevice(new BlindDevice("office-left", "Biuro lewa", localPi, RaspiPin.GPIO_13, RaspiPin.GPIO_14));
        addDevice(new BlindDevice("office-right", "Biuro prawa", localPi, RaspiPin.GPIO_21, RaspiPin.GPIO_22));

//        FLOOR 0 - DRIVER 0

        addDevice(new MotionSensor("pir-satel-tv", "Satel Pir TV", floor0Driver0Local, 12, true));
        addDevice(new MotionSensor("pir-satel-dinner", "Satel Pir Dinner", floor0Driver0Local, 13, true));
        addDevice(new MotionSensor("pir-satel-lobby", "Satel Pir Lobby", floor0Driver0Local, 14, true));
        addDevice(new MotionSensor("pir-satel-entry", "Satel Pir Entry", floor0Driver0Local, 15, true));
        addDevice(new MotionSensor("pir-satel-lobby-up", "Satel Pir Lobby Up", floor0Driver0Local, 20, true));

        addDevice(new SwitchDevice("cookerHood", "Okap", floor0Driver0Local, 22, ESwitchType.INPUT_OFF_LOW_ON));
        addDevice(new SwitchDevice("entryGate1", "Brama wjazdowa 1", floor0Driver0Local, 26, ESwitchType.INPUT_OFF_LOW_ON));
        addDevice(new SwitchDevice("garageGate", "Brama wjazdowa 2", floor0Driver0Local, 27, ESwitchType.INPUT_OFF_LOW_ON));
        addDevice(new SwitchDevice("gateway", "Brama garażowa", floor0Driver0Local, 28, ESwitchType.INPUT_OFF_LOW_ON));
        addDevice(new SwitchDevice("home-pump-switch", "home-pump-switch", floor0Driver0Local, 11, ESwitchType.INPUT_OFF_LOW_ON));
        addDevice(new SwitchDevice("hot-water-pump-switch", "hot-water-pump-switch", floor0Driver0Local, 0, ESwitchType.INPUT_OFF_LOW_ON));

        addDevice(new SingleColorLedDevice("led-stairs-1", "Stairs 1", floor0Driver0Local, 1));
        addDevice(new SingleColorLedDevice("led-stairs-2", "Stairs 2", floor0Driver0Local, 2));
        addDevice(new SingleColorLedDevice("led-stairs-3", "Stairs 3", floor0Driver0Local, 3));
        addDevice(new SingleColorLedDevice("led-stairs-4", "Stairs 4", floor0Driver0Local, 6));

        addDevice(new SingleColorLedDevice("led-stairs-5", "Stairs 5", floor0Driver0Local, 7));
        addDevice(new SingleColorLedDevice("led-stairs-6", "Stairs 6", floor0Driver0Local, 8));
        addDevice(new SingleColorLedDevice("led-stairs-7", "Stairs 7", floor0Driver0Local, 9));
        addDevice(new SingleColorLedDevice("led-stairs-8", "Stairs 8", floor0Driver0Local, 10));

//        FLOOR 1 - DRIVER 0

        addDevice(new RgbwDevice("rgbw-wc1", "WC 1", floor1Driver0ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("lamel", "Lamele", floor1Driver0ExpanderA, 4, 5, 6, 7));
        addDevice(new RgbwDevice("rgbw-office", "Biuro", floor1Driver0ExpanderA, 8, 9, 10, 11));

        addDevice(new RgbwwDevice("kit-blat", "Kuchania szafki", floor1Driver0ExpanderB, 0, 1, 2, 3, 4));
        addDevice(new RgbwDevice("l-mirror", "Korytarz Lustro", floor1Driver0ExpanderB, 8, 9, 10, 11));
//        addDevice(new RgbwDevice("rgbw-island", "Wyspa", floor1Driver0ExpanderB, 12, 13, 14, 15));

        addDevice(new NeoDevice("n-kit", "Kuchnia Neo", floor1Driver0Local, 21, 98, false, ENeoType.NEO_GRBW));
        addDevice(new NeoDevice("n-cell", "Sufit", floor1Driver0Local, 14, 225, false, ENeoType.NEO_GRBW));
        addDevice(new NeoDevice("n-tv", "Neo TV", floor1Driver0Local, 20, 133, true, ENeoType.NEO_GRBW));


        addDevice(new MotionSensor("pir-office", "Pir Biuro", floor1Driver0Local, 7));
        addDevice(new MotionSensor("pir-lobby", "Pir Korytarz", floor1Driver0Local, 8));
        addDevice(new MotionSensor("pir-kitchen", "Pir Kuchnia", floor1Driver0Local, 9));


//        FLOOR 1 - DRIVER 1

// 121 - standard led count
        addDevice(new NeoDevice("w1", "Wall 1", floor1Driver1Local, 12, 155, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("w2", "Wall 2", floor1Driver1Local, 13, 155, true, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("w3", "Wall 3", floor1Driver1Local, 14, 155, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("w4", "Wall 4", floor1Driver1Local, 15, 155, true, ENeoType.NEO_GRB));



        addDevice(new RgbwDevice("dinner-m", "Jadalnia 1", floor1Driver1ExpanderA, 4, 5, 6, 7));
        addDevice(new RgbwwDevice("cooker", "Okap", floor1Driver1ExpanderA, 8, 9, 10, 11, 12));

        addDevice(new RgbwDevice("rgbw-wc2", "WC 2", floor1Driver1ExpanderB, 0, 1, 2, 3));
        addDevice(new RgbwDevice("rgbw-tv1", "TV 1", floor1Driver1ExpanderB, 4, 5, 6, 7));
        addDevice(new RgbwDevice("rgbw-tv2", "TV 2", floor1Driver1ExpanderB, 8, 9, 10, 11));
        addDevice(new RgbwDevice("rgbw-lobby", "Korytarz", floor1Driver1ExpanderB, 12, 13, 14, 15));

        addDevice(new MotionSensor("pir-wc", "Pir WC", floor1Driver1Local, 22));


//        FLOOR 1 - DRIVER 2
        addDevice(new RgbwDevice("p1", "Panel1", floor1Driver2ExpanderA, 0, 1, 2, 3));
        addDevice(new RgbwDevice("pa2", "Panel2", floor1Driver2ExpanderA, 4, 5, 6, 7));
        addDevice(new RgbwDevice("p3", "Panel3", floor1Driver2ExpanderA, 8, 9, 10, 11));
        addDevice(new RgbwDevice("p4", "Panel4", floor1Driver2ExpanderA, 12, 13, 14, 15));

        addDevice(new RgbwDevice("plants", "Doniczka", floor1Driver2ExpanderB, 0, 1, 2, 3));
        addDevice(new RgbwDevice("led-hs", "Jadalnia Okno", floor1Driver2ExpanderB, 12, 13, 14, 15));

        addDevice(new MotionSensor("pir-tv", "Pir Tv", floor1Driver2Local, 20));


        addDevice(new NeoDevice("w5", "Wall 5", floor1Driver2Local, 12, 155, false, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("w6", "Wall 6", floor1Driver2Local, 13, 155, true, ENeoType.NEO_GRB));
        addDevice(new NeoDevice("w7", "Wall 7", floor1Driver2Local, 14, 155, false, ENeoType.NEO_GRB));

//        FLOOR 1 - DRIVER 5

        addDevice(new SwitchDevice("rainBirdPump", "Zraszacz pompa", floor1Driver5Local, 1, ESwitchType.HIGH_ON));
        addDevice(new SwitchDevice("rainBirdZone1", "Zraszacz strefa 1 - Tuje", floor1Driver5Local, 2, ESwitchType.HIGH_ON));
        addDevice(new SwitchDevice("rainBirdZone2", "Zraszacz strefa 2", floor1Driver5Local, 3, ESwitchType.HIGH_ON));
        addDevice(new SwitchDevice("rainBirdZone3", "Zraszacz strefa 3", floor1Driver5Local, 6, ESwitchType.HIGH_ON));

//        FLOOR 2 - DRIVER 0
        addDevice(new CctDevice("cct-bedroom-w", "Szafa Dawid", floor2Driver0ExpanderA, 0, 1));
        addDevice(new CctDevice("cct-wardrobe-d", "Szafa Dawid", floor2Driver0ExpanderA, 2, 3));
        addDevice(new CctDevice("cct-bedroom", "Sypialnia", floor2Driver0ExpanderA, 4, 5));
        addDevice(new CctDevice("cct-f2-lobby", "Korytarz", floor2Driver0ExpanderA, 6, 7));
        addDevice(new CctDevice("cct-p1", "Pokój 1", floor2Driver0ExpanderA, 8, 9));
        addDevice(new CctDevice("cct-p2", "Pokój 2", floor2Driver0ExpanderA, 10, 11));
        addDevice(new CctDevice("cct-wardrobe-m", "Szafa Magda", floor2Driver0ExpanderA, 12, 13));
        addDevice(new CctDevice("cct-wardrobe-m2", "Szafa Magda 2", floor2Driver0ExpanderA, 14, 15));

        addDevice(new MotionSensor("pir-war-m", "Pir Szafa Magda", floor2Driver0Local, 0));
        addDevice(new MotionSensor("pir-war-d", "Pir Szafa Dawid", floor2Driver0Local, 1));
        addDevice(new MotionSensor("pir-bedroom", "Pir Sypialnia", floor2Driver0Local, 2));
        addDevice(new MotionSensor("pir-f2-lobby", "Pir Korytarz", floor2Driver0Local, 3));
        addDevice(new MotionSensor("pir-p1", "Pir P1", floor2Driver0Local, 6));
        addDevice(new MotionSensor("pir-p2", "Pir P2", floor2Driver0Local, 7));

        addDevice(new BlindDevice("syp", "Sypialnia", floor2Driver0ExpanderB, 0, 1));
        addDevice(new BlindDevice("p1-l", "Pokój 1 L", floor2Driver0ExpanderB, 2, 3));
        addDevice(new BlindDevice("p1-p", "Pokój 1 P", floor2Driver0ExpanderB, 4, 5));
        addDevice(new BlindDevice("p2", "Pokój 2", floor2Driver0ExpanderB, 6, 7));
        addDevice(new BlindDevice("bath", "Lazienka", floor2Driver0ExpanderB, 8, 9));

        addDevice(new RgbwwDevice("cct-bedroom-o", "okno", floor2Driver0ExpanderB, 15, 14, 13, 12, 11));


        addDevice(new NeoDevice("neo-test", "neo test", testLocal, 10, 60, false, ENeoType.NEO_GRBW));

        addDevice(new SonoffTemperatureSensor("t-office", "Temperatura Biuro", zigbeeDriverConfigurationDown));
        addDevice(new SonoffTemperatureSensor("t-livingroom", "Temperatura Salon", zigbeeDriverConfigurationDown));

        addDevice(new SonoffTemperatureSensor("t-bethroom", "Sypialnia", zigbeeDriverConfigurationUp));
        addDevice(new SonoffTemperatureSensor("t-bathroom", "Łazienka", zigbeeDriverConfigurationUp));
        addDevice(new SonoffTemperatureSensor("t-p1", "Pokój 1", zigbeeDriverConfigurationUp));
        addDevice(new SonoffTemperatureSensor("t-p2", "Pokój 2", zigbeeDriverConfigurationUp));

        addDevice(new Sonoff3GangSwitch("temperature-bathroom-switch", "f2-temperature-switch-1","temperature-bathroom-switch",
                ESonoff3GangSwitchLocation.L1, zigbeeDriverConfigurationUp));
        addDevice(new Sonoff3GangSwitch("temperature-bedroom-switch", "f2-temperature-switch-1","temperature-bedroom-switch",
                ESonoff3GangSwitchLocation.L2, zigbeeDriverConfigurationUp));
        addDevice(new Sonoff3GangSwitch("temperature-p1-switch", "f2-temperature-switch-1", "temperature-p1-switch",
                ESonoff3GangSwitchLocation.L3, zigbeeDriverConfigurationUp));

        addDevice(new Sonoff3GangSwitch("temperature-p2-switch", "f2-temperature-switch-2","temperature-p2-switch",
                ESonoff3GangSwitchLocation.L1, zigbeeDriverConfigurationUp));

        addDevice(new SwitchDevice("temperature-livingroom-switch", "temperature-livingroom-switch", floor1Driver0Local, 12, ESwitchType.HIGH_ON));
        addDevice(new SwitchDevice("temperature-office-switch", "temperature-office-switch", floor1Driver0Local, 13, ESwitchType.HIGH_ON));
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
