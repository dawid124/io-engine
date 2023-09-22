package pl.webd.dawid124.ioengine.module.structure.model;

import java.util.List;
import java.util.Map;

public class Temperature {

    private List<String> sensors;
    private String switchId;
    private String pumpSwitchId;
    private Map<String, TemperatureScenes> scenes;

    public List<String> getSensors() {
        return sensors;
    }

    public void setSensors(List<String> sensors) {
        this.sensors = sensors;
    }

    public Map<String, TemperatureScenes> getScenes() {
        return scenes;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public String getPumpSwitchId() {
        return pumpSwitchId;
    }

    public void setPumpSwitchId(String pumpSwitchId) {
        this.pumpSwitchId = pumpSwitchId;
    }
}
