package com.example.phone_message.divided_route_chain.route.phone.message;

import com.example.phone_message.divided_route_chain.route.phone.message.configuration.PhoneMessageRouteUriPath;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class PhoneMessageProcessRoute extends RouteBuilder {

    private static final String PROCESS_REQUEST_ROUTE_ID = ".process";

    private final Processor processor;

    public PhoneMessageProcessRoute(@Qualifier("phoneMessageChangeProcessor") Processor processor) {
        this.processor = processor;
    }

    @Override
    public void configure() throws Exception {
        from(PhoneMessageRouteUriPath.DIRECT_PROCESS.value)
                .routeId(PhoneMessageProcessRoute.class + PROCESS_REQUEST_ROUTE_ID)
                .log(LoggingLevel.INFO, "${body}")
                .process(processor)
                .to(PhoneMessageRouteUriPath.DIRECT_REPLY.value);
    }
}
