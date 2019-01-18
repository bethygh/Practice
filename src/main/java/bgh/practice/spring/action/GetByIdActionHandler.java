package bgh.practice.spring.action;

import bgh.practice.spring.message.GetById;
import bgh.practice.spring.message.ResponseMessage;
import bgh.practice.spring.models.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;

@Component
public class GetByIdActionHandler extends ActionHandler<GetById> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetByIdActionHandler.class);

    @Override
    public ResponseMessage handle(GetById getById) throws Exception {

        if (Objects.isNull(getById)) {
            LOGGER.error("received message in GetById is null");
            throw new Exception("received.message.is.null");
        }
        LOGGER.debug("handle received message - GetById - received message: {}", getById);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);


        ResponseEntity<Fruit> responseEntity
                = restTemplate.exchange(super.restServerUrl + "/fruitstore/getFruit/{id}",
                HttpMethod.GET,
                entity,
                Fruit.class,
                getById.getRequestedId()
        );
        LOGGER.debug("response entity  - GetById - {}", responseEntity);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setBody(responseEntity.getBody());
        //responseMessage.setPayloadClass(responseEntity.getBody().getClass().toString());
        responseMessage.setStatus(responseEntity.getStatusCode().toString());
        responseMessage.setRequestPayloadClass(GetById.class.toString());
        return responseMessage;
    }
}
