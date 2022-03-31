package pl.webd.dawid124.ioengine.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.json.DeviceStateJsonAdapter;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.structure.Home;
import pl.webd.dawid124.ioengine.home.structure.Scene;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service public class StructureService {

    private final Gson gson;

    private Home home;


    public StructureService() {
        this.home = new Home();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(DeviceState.class, new DeviceStateJsonAdapter())
                .create();
    }

    @PostConstruct
    public void init() {
        try {
            String structure = ResourceUtils.getResourceFileAsString("classpath:structure.json");
            home = gson.fromJson(structure, Home.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Home fetchStructure() {
        return home;
    }

    public Scene fetchScene(String zoneId, String sceneId) {
        return home.getZones().get(zoneId).getScenes().get(sceneId);
    }
}
