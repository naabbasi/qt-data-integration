package com.qterminals.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.StringJoiner;

@Getter
@Setter
@NoArgsConstructor
public class IntegrationParams {
    private String integrationId;
    private Date paymentDate;

    @Override
    public String toString() {
        return new StringJoiner(", ", IntegrationParams.class.getSimpleName() + "[", "]")
                .add("integrationId='" + integrationId + "'")
                .add("paymentDate=" + paymentDate)
                .toString();
    }
}
