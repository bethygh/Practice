package bgh.practice.spring.broker;

import bgh.practice.spring.action.ActionHandler;
import bgh.practice.spring.message.ResponseMessage;
import bgh.practice.spring.repo.FruitRepo;
import bgh.practice.spring.util.ObjectSerializer;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Component
public class MqttSubscriber implements MqttCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriber.class);

    private String topic = "new/fruit";
    @Autowired
    FruitRepo fruitRepo;
    @Autowired
    private Map<String, ActionHandler> allHandlers;
    ResponseMessage responseMessage;
    MqttClient consumer;
    private Map<Class, ActionHandler> handlers
            = new TreeMap<>((c1, c2) -> c1.getSimpleName().compareTo(c2.getSimpleName()));

    public MqttSubscriber() throws Exception {

        try {
            this.consumer = new org.eclipse.paho.client.mqttv3.MqttClient("tcp://127.0.0.1:1883",
                    org.eclipse.paho.client.mqttv3.MqttClient.generateClientId());
            consumer.setCallback(this);
            consumer.connect();
            consumer.subscribe(topic);
        }catch (MqttException e) {
            LOGGER.error("error in initializing the subscriber: {}", e);
            throw new Exception("mqtt.subscriber.client.initialize.error");
        }
    }

	@Override
	public void connectionLost(Throwable cause) {
        LOGGER.error("subscriber connection lost. cause: {}", cause);
        //TODO reconnect?
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {

	    try {
            if (Objects.isNull(message)) {
                LOGGER.error("received mqtt message in subscriber is null");
                return;
            }
            LOGGER.debug("receive mqtt message in {}. received message: {}", topic, message);

            allHandlers.values().forEach(handler ->
                    handlers.put(handler.requestType(), handler));

            ActionHandler handler = null;
            Object payload = ObjectSerializer.fromBytes(message.getPayload());
            handler = handlers.get(payload.getClass());
            if (handler == null) {
                LOGGER.error("action handler for received message not found");
                return;
            }
            responseMessage = handler.handle(payload);
            handler.sendResponse(responseMessage);
        }catch (Exception e) {
            LOGGER.error("Exception:::: {}", e);
            return;
        }

        LOGGER.debug("handling received message is done");

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

    //@Override
    public void connectComplete(boolean reconnect, String serverURI) throws Exception {
        System.out.println("Re-Connection Attempt " + reconnect);
        if(reconnect) {
            try {
                this.consumer.subscribe(topic, 1);
            }catch (MqttException e) {
                LOGGER.error("error in initializing the subscriber: {}", e);
                throw new Exception("mqtt.subscriber.client.initialize.error");
            }
        }
    }
}
