package pl.webd.dawid124.ioengine.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
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
import pl.webd.dawid124.ioengine.module.trigger.TriggerService;

@Configuration
public class MqttConfig {

    private final MqttSettings settings;
    private final TriggerService triggerService;

    public MqttConfig(MqttSettings settings, TriggerService triggerService) {
        this.settings = settings;
        this.triggerService = triggerService;
    }

    @Bean
    public MessageChannel mqttTriggerChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer triggers() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(settings.getClientId() + "Trigger", mqttClientFactory(), settings.getTriggerTopic());

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttTriggerChannel());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttTriggerChannel")
    public MessageHandler handler() {
        return triggerService;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(settings.getClientId() + "Actions", mqttClientFactory());
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
