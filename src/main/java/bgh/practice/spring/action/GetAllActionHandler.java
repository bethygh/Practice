package bgh.practice.spring.action;

import bgh.practice.spring.message.GetAll;
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
public class GetAllActionHandler extends ActionHandler<GetAll> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllActionHandler.class);

    @Override
    public ResponseMessage handle(GetAll getAll) throws Exception {

        if (Objects.isNull(getAll)) {
            LOGGER.error("received message in GetAll is null");
            throw new Exception("received.message.is.null");
        }
        LOGGER.debug("handle received message - GetAll - received message: {}", getAll);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Fruit[]> responseEntity
                = restTemplate.exchange(super.restServerUrl + "/fruitstore/getFruits",
                HttpMethod.GET,
                entity,
                Fruit[].class
        );
        LOGGER.debug("response entity  - GetAll - {}", responseEntity);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setBody(responseEntity.getBody());
        //responseMessage.setPayloadClass(responseEntity.getBody().getClass().toString());
        responseMessage.setStatus(responseEntity.getStatusCode().toString());
        responseMessage.setRequestPayloadClass(GetAll.class.toString());
        return responseMessage;
    }
}
