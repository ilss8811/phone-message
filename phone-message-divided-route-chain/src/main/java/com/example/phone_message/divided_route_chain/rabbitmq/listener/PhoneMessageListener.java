package com.example.phone_message.divided_route_chain.rabbitmq.listener;

import com.example.phone_message.divided_route_chain.dto.PhoneMessageDto;
import com.example.phone_message.divided_route_chain.route.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */

@EnableRabbit
@Component
public class PhoneMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(PhoneMessageListener.class);

    @RabbitListener(
            id = "rpc",
            queues = "${application.broker.request-rpc-queue}"
    )
    public Message processPhoneMessageRpcRequests(Message message) {
        final String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        // Logging original received message
        logger.info(MessageFormat.format("Message at {0}: {1}", Instant.now(), messageBody));

        // Change the message to make sure the RPC connection is working
        final PhoneMessageDto phoneMessage = JsonUtils.convertJsonToObject(messageBody, PhoneMessageDto.class);
        final List<String> props = Optional.ofNullable(phoneMessage.getProps())
                .orElse(new ArrayList<>());
        props.add("CHANGED_BY_RPC:" + Instant.now().toString());
        phoneMessage.setProps(props);

        return new Message(
                JsonUtils.convertObjectToJson(phoneMessage).getBytes(StandardCharsets.UTF_8),
                message.getMessageProperties()
        );
    }

    @RabbitListener(
            id = "default",
            queues = "${application.broker.request-default-queue}"
    )
    public void processPhoneMessageDefaultRequests(Message message) {
        final String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

        logger.info(MessageFormat.format("Message at {0}: {1}", Instant.now(), messageBody));
    }
}
