package pl.webd.dawid124.ioengine.model;

import java.io.Serializable;

public class ZoneResponse implements Serializable {

    private String activeScene;

    public ZoneResponse() {}

    public ZoneResponse(String activeScene) {
        this.activeScene = activeScene;
    }

    public String getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(String activeScene) {
        this.activeScene = activeScene;
    }
}
