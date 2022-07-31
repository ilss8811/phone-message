package com.example.phone_message.monolith_route_chain.processor;

import com.example.phone_message.monolith_route_chain.dto.PhoneMessageDto;
import com.example.phone_message.monolith_route_chain.route.JsonUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */

@Component
public class PhoneMessageChangeProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message message = exchange.getMessage();

        final PhoneMessageDto phoneMessage = JsonUtils.convertJsonToObject(message.getBody(String.class), PhoneMessageDto.class);
        List<String> props = Optional.ofNullable(phoneMessage.getProps())
                .orElse(new ArrayList<>());
        props.add("CHANGED_BY_PROCESSOR:" + Instant.now().toString());
        phoneMessage.setProps(props);

        message.setBody(JsonUtils.convertObjectToJson(phoneMessage));
    }
}
