package com.qterminals.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
@NoArgsConstructor
public class IntegrationServiceActivationError {
    private String code;
    private Boolean hasErrors;
    private String version;

    @Override
    public String toString() {
        return new StringJoiner(", ", IntegrationServiceActivationError.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .add("hasErrors=" + hasErrors)
                .add("version='" + version + "'")
                .toString();
    }
}
