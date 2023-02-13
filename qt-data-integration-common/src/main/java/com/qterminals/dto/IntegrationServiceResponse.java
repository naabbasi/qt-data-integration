package com.qterminals.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class IntegrationServiceResponse {
    private Integer statusCode;
    private String body;

    @Override
    public String toString() {
        return new StringJoiner(", ", IntegrationServiceResponse.class.getSimpleName() + "[", "]")
                .add("statusCode=" + statusCode)
                .add("body='" + body + "'")
                .toString();
    }
}
