package com.example.phone_message.divided_route_chain.route.phone.message;

import com.example.phone_message.divided_route_chain.route.phone.message.configuration.PhoneMessageRouteUriPath;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 *
 * Starting route endpoint of Phone Message route path.
 *
 * Routing schema  (in order by list)
 * @see RestPhoneMessageRoute - listening for http request
 * @see PhoneMessageOkResponseRoute - if body not empty, response 200 OK
 * @see PhoneMessageRpcRequestRoute - sending rpc request to broker
 * @see PhoneMessageProcessRoute - receiving rpc reply from broker, and processing message
 * @see PhoneMessageDefaultRequestRoute - sending direct request to broker, with changed message
 */

@Component
public class RestPhoneMessageRoute extends RouteBuilder {

    private static final String REST_ROUTE_ID = "PhoneMessagesRestRouteId";

    @Override
    public void configure() throws Exception {
        rest().post(PhoneMessageRouteUriPath.PHONE_MESSAGE_REST_URI.value)
                .id(REST_ROUTE_ID)
                .consumes("application/json")
                .to(PhoneMessageRouteUriPath.DIRECT_OK_RESPONSE.value);
    }
}
