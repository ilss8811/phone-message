package com.example.phone_message.divided_route_chain.route;

import java.text.MessageFormat;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message-divided-route-chain
 */
public final class RouteUtils {

    private RouteUtils() {}

    /**
     * Broker request URI generation method
     *
     * @param exchangeName Required parameter
     * @param routingKey Required parameter
     * @param queueName Optional parameter to specify queue for request
     *
     * @return Generated Java DSL URI in string format
     */
    public static String generateBrokerRequestUri(String exchangeName, String routingKey, String queueName) {
        return MessageFormat.format(
                "spring-rabbitmq:{0}?queues={1}&routingKey={2}",
                exchangeName,
                queueName,
                routingKey
        );
    }

    public static String generateBrokerRequestUri(String exchangeName, String routingKEy) {
        return MessageFormat.format(
                "spring-rabbitmq:{0}?routingKey={1}",
                exchangeName,
                routingKEy
        );
    }

}
