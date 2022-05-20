package pl.webd.dawid124.ioengine.mqtt.config;

import java.util.List;

public class IoConfigRequest {

    private final List<IoConfig> configs;

    public IoConfigRequest(List<IoConfig> configs) {
        this.configs = configs;
    }

    public List<IoConfig> getConfigs() {
        return configs;
    }
}
