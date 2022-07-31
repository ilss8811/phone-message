package com.example.phone_message.divided_route_chain.route.phone.message;

import com.example.phone_message.divided_route_chain.route.phone.message.configuration.PhoneMessageRouteUriPath;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
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
public class PhoneMessageOkResponseRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(PhoneMessageRouteUriPath.DIRECT_OK_RESPONSE.value)
                .to(PhoneMessageRouteUriPath.DIRECT_REQUEST.value)
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
                .process(exchange -> exchange.getMessage().setBody(null));
    }
}
