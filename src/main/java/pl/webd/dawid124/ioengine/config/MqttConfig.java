package pl.webd.dawid124.ioengine.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import pl.webd.dawid124.ioengine.config.settings.MqttSettings;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.MqttDriver;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.driversync.DriverSyncService;
import pl.webd.dawid124.ioengine.module.automation.trigger.TriggerService;
import pl.webd.dawid124.ioengine.module.voice.VoiceController;
import pl.webd.dawid124.ioengine.module.zigbee.ZigbeeService;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MqttConfig {

    private final MqttSettings settings;
    private final TriggerService triggerService;
    private final ZigbeeService zigbeeService;
    private final DeviceService deviceService;
    private final VoiceController voiceController;
    private final DriverSyncService driverSyncService;

    public MqttConfig(MqttSettings settings, TriggerService triggerService, ZigbeeService zigbeeService,
                      DeviceService deviceService, DriverSyncService driverSyncService, VoiceController voiceController) {
        this.settings = settings;
        this.triggerService = triggerService;
        this.zigbeeService = zigbeeService;
        this.deviceService = deviceService;
        this.driverSyncService = driverSyncService;
        this.voiceController = voiceController;
    }

    @Bean
    public MessageChannel mqttTriggerChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttZigbeeChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttHermesChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer triggers() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                settings.getClientId() + "Trigger", mqttClientFactory(), settings.getTriggerTopic());

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttTriggerChannel());

        return adapter;
    }

    @Bean
    public MessageProducer hermes() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                settings.getClientId() + "Hermes", mqttClientFactory(), settings.getHermesIntentTopic());

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttHermesChannel());

        return adapter;
    }

    @Bean
    public MessageProducer zigbee() {
        Set<String> zigbeeTopics = deviceService.fetchAll().values().stream()
                .filter(d -> EIoDriverType.ZIGBEE_MQTT.equals(d.getDriverConfiguration().getDriver().getType()))
                .filter(d -> d.getDriverConfiguration().getDriver() instanceof MqttDriver)
                .map(device -> ((MqttDriver) device.getDriverConfiguration().getDriver()).getTopic() + "/" + ((ZigbeeDevice) device).getMqttAddress())
                .collect(Collectors.toSet());

        if (zigbeeTopics.isEmpty()) return null;

        String first = zigbeeTopics.iterator().next();
        zigbeeTopics.remove(first);

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                settings.getClientId() + "Zigbee", mqttClientFactory(), first);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttZigbeeChannel());

        if (zigbeeTopics.isEmpty()) return adapter;

        zigbeeTopics.forEach(adapter::addTopic);

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttTriggerChannel")
    public MessageHandler triggerHandler() {
        return triggerService;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttZigbeeChannel")
    public MessageHandler zigbeeHandler() {
        return zigbeeService;
    }


    @Bean
    public MessageChannel mqttDriverSyncChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer driverSync() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                settings.getClientId() + "DriverSync", mqttClientFactory(), settings.getDriverSyncTopic());

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttDriverSyncChannel());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttDriverSyncChannel")
    public MessageHandler driverSyncHandler() {
        return driverSyncService;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                settings.getClientId() + "Actions", mqttClientFactory());

        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(settings.getTopic());
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttGateway {
        void sendToMqtt(Message<String> message);
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getReceiverMqttConnectOptions());
        return factory;
    }

    @Bean
    public MqttConnectOptions getReceiverMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(30);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setServerURIs(new String[] { settings.getHost() });
        return mqttConnectOptions;
    }
}
