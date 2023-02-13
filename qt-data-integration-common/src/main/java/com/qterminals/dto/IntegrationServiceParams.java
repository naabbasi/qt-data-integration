package com.qterminals.dto;

import com.qterminals.dto.params.GeneratePaymentParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class IntegrationServiceParams implements Serializable {
    private GeneratePaymentParams var_sp_last_successful_runtime;
}
