package pl.webd.dawid124.ioengine.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.json.DeviceStateJsonAdapter;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.structure.Home;
import pl.webd.dawid124.ioengine.home.structure.Scene;
import pl.webd.dawid124.ioengine.home.structure.Zone;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service public class StructureService {

    private Home home;

    private Gson gson;

    public StructureService() {
        this.home = new Home();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(DeviceState.class, new DeviceStateJsonAdapter())
                .create();
    }

    @PostConstruct public void init() {


        try {

            String structure = ResourceUtils.getResourceFileAsString("classpath:structure.json");
            home = gson.fromJson(structure, Home.class);
            System.out.println(structure);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Zone floor1 = buildFloor1Zone();
        home.getZones().put(floor1.getId(), floor1);

        Zone floor2 = buildFloor2Zone();
        home.getZones().put(floor2.getId(), floor2);

        Zone office = buildOfficeZone();
        home.getZones().put(office.getId(), office);
    }

    private Zone buildFloor1Zone() {
        Zone floor1 = new Zone("floor1", "Salon", 0);

        floor1.getDeviceIds().add("rgbw-tv");
        floor1.getDeviceIds().add("rgbw-kitchen");
        floor1.getDeviceIds().add("rgbw-dinning1");
        floor1.getDeviceIds().add("rgbw-dinning2");

        floor1.addScene(new Scene("auto", "Auto", 0));
        floor1.addScene(new Scene("standard", "Standard", 1));
        floor1.addScene(new Scene("night", "Noc", 2));

        return floor1;
    }

    private Zone buildFloor2Zone() {
        Zone floor2 = new Zone("floor2", "Góra", 1);

        floor2.addScene(new Scene("auto", "Auto", 0));
        floor2.addScene(new Scene("standard", "Standard", 1));

        floor2.getDeviceIds().add("led-bathroom");

        return floor2;
    }

    private Zone buildOfficeZone() {
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
