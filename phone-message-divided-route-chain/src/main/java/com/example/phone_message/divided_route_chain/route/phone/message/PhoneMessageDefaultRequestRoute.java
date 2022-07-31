package com.example.phone_message.divided_route_chain.route.phone.message;

import com.example.phone_message.divided_route_chain.route.phone.message.configuration.PhoneMessageRouteUriPath;
import com.example.phone_message.divided_route_chain.route.RouteUtils;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 *
 * Part of Phone Message route chain.
 *
 * For explanation
 * @see RestPhoneMessageRoute
 */

@Component
public class PhoneMessageDefaultRequestRoute extends RouteBuilder {

    @Value("${application.broker.exchange}")
    private String exchangeName;

    @Value("${application.broker.default-routing-key}")
    private String defaultRoutingKey;

    private static final String DEFAULT_REQUEST_ROUTE_ID = ".default";

    @Override
    public void configure() throws Exception {
        final String brokerDefaultRequestUri = RouteUtils.generateBrokerRequestUri(exchangeName, defaultRoutingKey);

        from(PhoneMessageRouteUriPath.DIRECT_REPLY.value)
                .id(PhoneMessageDefaultRequestRoute.class + DEFAULT_REQUEST_ROUTE_ID)
                .convertBodyTo(String.class)
                .to(ExchangePattern.InOnly, brokerDefaultRequestUri)
                .log(LoggingLevel.INFO, "${body}");
    }
}
