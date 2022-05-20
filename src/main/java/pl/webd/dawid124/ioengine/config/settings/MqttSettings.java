package pl.webd.dawid124.ioengine.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mqtt")
public class MqttSettings {

    private String host;
    private String username;
    private String password;
    private String topic;
    private String triggerTopic;
    private String driverSyncTopic;
    private String actionTopic;
    private String clientId;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTriggerTopic() {
        return triggerTopic;
    }

    public void setTriggerTopic(String triggerTopic) {
        this.triggerTopic = triggerTopic;
    }

    public String getActionTopic() {
        return actionTopic;
    }

    public void setActionTopic(String actionTopic) {
        this.actionTopic = actionTopic;
    }

    public String getDriverSyncTopic() {
        return driverSyncTopic;
    }

    public void setDriverSyncTopic(String driverSyncTopic) {
        this.driverSyncTopic = driverSyncTopic;
    }
}
