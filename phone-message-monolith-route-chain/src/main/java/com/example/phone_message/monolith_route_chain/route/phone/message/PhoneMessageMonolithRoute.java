package com.example.phone_message.monolith_route_chain.route.phone.message;

import com.example.phone_message.monolith_route_chain.route.RouteUtils;
import com.example.phone_message.monolith_route_chain.route.phone.message.configuration.PhoneMessageRouteUriPath;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */

@Component
public class PhoneMessageMonolithRoute extends RouteBuilder {

    @Value("${application.broker.exchange}")
    private String exchangeName;

    @Value("${application.broker.rpc-routing-key}")
    private String rpcRoutingKey;

    @Value("${application.broker.default-routing-key}")
    private String defaultRoutingKey;

    private static final String PROCESS_REQUEST_ROUTE_ID = ".process";

    private static final String DEFAULT_REQUEST_ROUTE_ID = ".default";

    private static final String RPC_REQUEST_ROUTE_ID = ".rpc";

    private final Processor processor;

    public PhoneMessageMonolithRoute(@Qualifier("phoneMessageChangeProcessor") Processor processor) {
        this.processor = processor;
    }

    @Override
    public void configure() throws Exception {
        final String brokerRpcRequestUri = RouteUtils.generateBrokerRequestUri(exchangeName, rpcRoutingKey);
        final String brokerDefaultRequestUri = RouteUtils.generateBrokerRequestUri(exchangeName, defaultRoutingKey);

        // Listening endpoint for requests
        rest().post(PhoneMessageRouteUriPath.PHONE_MESSAGE_REST_URI.value)
                .consumes("application/json")
                .to(PhoneMessageRouteUriPath.DIRECT_OK_RESPONSE.value);

        // Sending 200 OK response
        from(PhoneMessageRouteUriPath.DIRECT_OK_RESPONSE.value)
                .to(PhoneMessageRouteUriPath.DIRECT_REQUEST.value)
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
                .process(exchange -> exchange.getMessage().setBody(null));

        // Sending RPC to broker, and receiving reply
        from(PhoneMessageRouteUriPath.DIRECT_REQUEST.value)
                .id(PhoneMessageMonolithRoute.class + RPC_REQUEST_ROUTE_ID)
                .marshal().json()
                .convertBodyTo(String.class)
                .log(LoggingLevel.INFO, "${body}")
                .to(ExchangePattern.InOut, brokerRpcRequestUri)
                .to(PhoneMessageRouteUriPath.DIRECT_PROCESS.value);

        // Processing RPC request reply
        from(PhoneMessageRouteUriPath.DIRECT_PROCESS.value)
                .id(PhoneMessageMonolithRoute.class + PROCESS_REQUEST_ROUTE_ID)
                .log(LoggingLevel.INFO, "${body}")
                .process(processor)
                .to(PhoneMessageRouteUriPath.DIRECT_REPLY.value);

        // Sending direct request with change message to broker
        from(PhoneMessageRouteUriPath.DIRECT_REPLY.value)
                .id(PhoneMessageMonolithRoute.class + DEFAULT_REQUEST_ROUTE_ID)
                .convertBodyTo(String.class)
                .to(ExchangePattern.InOnly, brokerDefaultRequestUri)
                .log(LoggingLevel.INFO, "${body}");
    }
}
