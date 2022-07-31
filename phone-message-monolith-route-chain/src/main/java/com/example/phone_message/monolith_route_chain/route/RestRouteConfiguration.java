package com.example.phone_message.monolith_route_chain.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */
@Component
public class RestRouteConfiguration extends RouteBuilder {

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private String port;

    @Value("${application.rest.component-id}")
    private String componentId;

    @Override
    public void configure() {
        restConfiguration()
                .component(componentId)
                .host(address)
                .port(port)
                .bindingMode(RestBindingMode.json)
                .skipBindingOnErrorCode(false);
    }
}
