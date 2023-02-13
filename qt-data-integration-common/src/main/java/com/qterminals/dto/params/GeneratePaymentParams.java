package com.qterminals.dto.params;

import lombok.ToString;

/**
 * Dont create setter and getter otherwise params value will not
 * be set in class properties
 */
@ToString
public class GeneratePaymentParams {
    public String RunNow;
    public String Default;
}
