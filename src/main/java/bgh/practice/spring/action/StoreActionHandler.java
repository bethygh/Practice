package bgh.practice.spring.action;

import bgh.practice.spring.message.ResponseMessage;
import bgh.practice.spring.message.Store;
import bgh.practice.spring.models.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Objects;

@Component
public class StoreActionHandler extends ActionHandler<Store> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreActionHandler.class);

    @Override
    public ResponseMessage handle(Store store) throws Exception {

        if (Objects.isNull(store)) {
            LOGGER.error("received message in Store is null");
            throw new Exception("received.message.is.null");
        }
        LOGGER.debug("handle received message - Store - received message: {}", store);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Fruit> entity = new HttpEntity<>(store.getFruit(), headers);

        ResponseEntity<Integer> responseEntity
                = restTemplate.exchange(super.restServerUrl + "/fruitstore/store/fruit",
                HttpMethod.POST,
                entity,
                Integer.class
        );
        LOGGER.debug("response entity  - Store - {}", responseEntity);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setBody(responseEntity.getBody());
        //responseMessage.setPayloadClass(responseEntity.getBody().getClass().toString());
        responseMessage.setStatus(responseEntity.getStatusCode().toString());
        responseMessage.setRequestPayloadClass(Store.class.toString());
        return responseMessage;
    }
}
