package pl.webd.dawid124.ioengine.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.state.variable.StringVariable;
import pl.webd.dawid124.ioengine.home.structure.Home;
import pl.webd.dawid124.ioengine.home.structure.Scene;
import pl.webd.dawid124.ioengine.home.structure.Zone;

import javax.annotation.PostConstruct;

@Service
public class StructureService {

    private final Home home;

    public StructureService() {
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
        Zone floor1 = new Zone("floor1", "Salon", 0);

        floor1.getDeviceIds().add("rgbw-tv");
        floor1.getDeviceIds().add("rgbw-kitchen");
        floor1.getDeviceIds().add("rgbw-dinning1");
        floor1.getDeviceIds().add("rgbw-dinning2");

        floor1.addScene(new Scene("auto", "Auto", 0));
        floor1.addScene(new Scene("standard", "Standard", 1));
        floor1.addScene(new Scene("music", "Muzyka", 2));

        return floor1;
    }

    public Zone buildFloor2Zone() {
        Zone floor2 = new Zone("floor2", "Góra", 1);

        floor2.getDeviceIds().add("led-bathroom");

        return floor2;
    }

    public Zone buildOfficeZone() {
        Zone office = new Zone("office", "Biuro", 2);

        office.getDeviceIds().add("rgbw-office");

        office.addScene(new Scene("auto", "Auto", 0));
        office.addScene(new Scene("standard", "Standard", 1));

        return office;
    }

    public Home fetchStructure() {
        return home;
    }
}
