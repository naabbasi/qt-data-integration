package com.qterminals.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
@NoArgsConstructor
public class IntegrationServiceActivationStatus {
    private String activationStatus;

    @Override
    public String toString() {
        return new StringJoiner(", ", IntegrationServiceActivationStatus.class.getSimpleName() + "[", "]")
                .add("activationStatus='" + activationStatus + "'")
                .toString();
    }
}
