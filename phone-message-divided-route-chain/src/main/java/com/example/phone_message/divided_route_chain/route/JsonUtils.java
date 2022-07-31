package com.example.phone_message.divided_route_chain.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message-divided-route-chain
 *
 * Wrapper class with static methods for simple use json encode/decode
 */
public final class JsonUtils {

    private JsonUtils() {}

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static <T> T convertJsonToObject(String value, Class<T> type) {
        return objectMapper.readValue(value, type);
    }

    @SneakyThrows
    public static String convertObjectToJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
