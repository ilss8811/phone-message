package com.example.phone_message.divided_route_chain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */

@Builder
@Getter
@Setter
@ToString
@JsonDeserialize(builder = PhoneMessageDto.PhoneMessageDtoBuilder.class)
public class PhoneMessageDto implements Serializable {

    private String message;

    private List<String> props;

    @JsonPOJOBuilder(withPrefix="")
    public static class PhoneMessageDtoBuilder {
    }

}

