package bgh.practice.spring.broker;

import bgh.practice.spring.message.ResponseMessage;
import bgh.practice.spring.util.ObjectSerializer;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

public class MqttPublisher implements MqttCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttPublisher.class);

    MqttConnectOptions connectOptions;
    String broker = "tcp://127.0.0.1:1883";
    String topicStr = "FruitSubscriber";
    String clientId = "new/fruit2";


    public void publishMessage(ResponseMessage responseMessage) throws Exception {
        if (Objects.isNull(responseMessage)) {
            LOGGER.error("response message in publisher is null");
            throw new Exception("response.message.is.null");
        }
        LOGGER.debug("publish message by: {} - received message: {}", clientId, responseMessage);

        MqttClient mqttClient = null;

        connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setKeepAliveInterval(30);

        try {
            mqttClient = new MqttClient(broker, clientId);
            mqttClient.setCallback(this);
            mqttClient.connect(connectOptions);
        } catch (MqttException e) {
            LOGGER.error("mqtt publisher client connect error", e);
            throw new Exception("mqtt.publisher.client.connect.failed");
        }

        LOGGER.debug("Connected to {}" + broker);

        MqttTopic topic = mqttClient.getTopic(topicStr);

        int pubQoS = 0;
        MqttMessage message = new MqttMessage(ObjectSerializer.toBytes(responseMessage));
        message.setQos(pubQoS);
        message.setRetained(false);

        LOGGER.debug("Publishing with qos {} to topic: {} ", pubQoS, topic);
        MqttDeliveryToken token = null;

        try {
            token = topic.publish(message);
            token.waitForCompletion();
            Thread.sleep(100);
        } catch (Exception e) {
            LOGGER.error("mqtt publisher client publish error", e);
            throw new Exception("mqtt.publisher.client.publish.failed");
        }

        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            LOGGER.error("mqtt publisher client disconnect error", e);
            throw new Exception("mqtt.publisher.client.disconnect.failed");
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
