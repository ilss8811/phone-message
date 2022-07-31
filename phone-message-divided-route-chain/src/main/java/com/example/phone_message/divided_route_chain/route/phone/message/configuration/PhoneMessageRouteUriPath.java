package com.example.phone_message.divided_route_chain.route.phone.message.configuration;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */

public enum PhoneMessageRouteUriPath {

    PHONE_MESSAGE_REST_URI("/phone-message"),

    DIRECT_REQUEST("direct:phone.message.request"),

    DIRECT_REPLY("direct:phone.message.reply"),

    DIRECT_PROCESS("direct:phone.message.process"),

    DIRECT_OK_RESPONSE("direct:ok.response");

    public final String value;

    PhoneMessageRouteUriPath(String value) {
        this.value = value;
    }
}
