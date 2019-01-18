package bgh.practice.spring.action;

import bgh.practice.spring.broker.MqttPublisher;
import bgh.practice.spring.message.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class ActionHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionHandler.class);

    protected final String restServerUrl = "http://localhost:8080";

    public abstract ResponseMessage handle(T payload) throws Exception;

    public void sendResponse(ResponseMessage responseMessage) throws Exception {
        if (Objects.isNull(responseMessage)) {
            LOGGER.error("response message in action handler is null");
            throw new Exception("response.message.is.null");
        }
        LOGGER.debug("send message response to sender - response message: {}", responseMessage);

        MqttPublisher mqttPublisher = new MqttPublisher();
        mqttPublisher.publishMessage(responseMessage);

    }


    @SuppressWarnings("unchecked")
    public Class<T> requestType() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
