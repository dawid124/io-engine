package pl.webd.dawid124.ioengine.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.state.variable.StringVariable;
import pl.webd.dawid124.ioengine.home.structure.Home;
import pl.webd.dawid124.ioengine.home.structure.Scene;
import pl.webd.dawid124.ioengine.home.structure.Zone;

import javax.annotation.PostConstruct;

@Service
public class StructureService {

    private final DeviceService deviceService;
    private final StateService stateService;

    private Home home;

    public StructureService(DeviceService deviceService, StateService stateService) {
        this.deviceService = deviceService;
        this.stateService = stateService;

        this.home = new Home();
    }

    @PostConstruct
    public void init() {

        home.getVariables().put("test-global-variable", new StringVariable("default"));

        Zone floor1 = buildFloor1Zone();
        home.getZones().put(floor1.getId(), floor1);

        Zone floor2 = buildFloor2Zone();
        home.getZones().put(floor2.getId(), floor2);

        Zone office = buildOfficeZone();
        home.getZones().put(office.getId(), office);
    }

    public Zone buildFloor1Zone() {
        Zone floor1 = new Zone("floor1", "Salon");

        floor1.getDeviceIds().add("rgbw-tv");
        floor1.getDeviceIds().add("rgbw-kitchen");
        floor1.getDeviceIds().add("rgbw-dinning1");
        floor1.getDeviceIds().add("rgbw-dinning2");

        floor1.addScene(new Scene("auto", "Auto"));
        floor1.addScene(new Scene("standard", "Standard"));
        floor1.addScene(new Scene("music", "Muzyka"));

        return floor1;
    }

    public Zone buildFloor2Zone() {
        Zone floor2 = new Zone("floor2", "Góra");

        floor2.getDeviceIds().add("led-bathroom");

        return floor2;
    }

    public Zone buildOfficeZone() {
        Zone office = new Zone("office", "Biuro");

        office.getDeviceIds().add("rgbw-office");

        office.addScene(new Scene("auto", "Auto"));
        office.addScene(new Scene("standard", "Standard"));

        return office;
    }

    public Home fetchStructure() {

        home.getZones().forEach((zoneId, zone) -> zone.setDevices(deviceService.fetchSelected(zone.getDeviceIds())));
        home.getZones().forEach((zoneId, zone) -> zone.setDeviceStates(stateService.fetchSelected(zone.getDeviceIds())));

        return home;
    }
}
