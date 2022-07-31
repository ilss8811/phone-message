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
public class PhoneMessageRpcRequestRoute extends RouteBuilder {

    @Value("${application.broker.exchange}")
    private String exchangeName;

    @Value("${application.broker.rpc-routing-key}")
    private String rpcRoutingKey;

    private static final String RPC_REQUEST_ROUTE_ID = ".rpc";

    @Override
    public void configure() throws Exception {
        final String brokerRpcRequestUri = RouteUtils.generateBrokerRequestUri(exchangeName, rpcRoutingKey);

        from(PhoneMessageRouteUriPath.DIRECT_REQUEST.value)
                .routeId(PhoneMessageRpcRequestRoute.class + RPC_REQUEST_ROUTE_ID)
                .marshal().json()
                .convertBodyTo(String.class)
                .log(LoggingLevel.INFO, "${body}")
                .to(ExchangePattern.InOut, brokerRpcRequestUri)
                .to(PhoneMessageRouteUriPath.DIRECT_PROCESS.value);
    }
}
