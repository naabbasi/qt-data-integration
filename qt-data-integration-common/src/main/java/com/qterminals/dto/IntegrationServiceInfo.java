package com.qterminals.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class IntegrationServiceInfo implements Serializable {
    private String name;
    private String code;
    private Date created;
    private String createdBy;
    private String endPointURI;
    private String pattern;
    private IntegrationServiceParams scheduleParams;
}
