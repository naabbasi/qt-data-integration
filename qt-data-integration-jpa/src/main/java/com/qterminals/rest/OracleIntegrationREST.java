package com.qterminals.rest;

import com.qterminals.dto.IntegrationParams;
import com.qterminals.dto.IntegrationServiceInfo;
import com.qterminals.generic.rest.GenericREST;
import com.qterminals.util.AppHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/oci")
public class OracleIntegrationREST extends GenericREST {

    private AppHelper appHelper;

    public OracleIntegrationREST(AppHelper appHelper) {
        this.appHelper = appHelper;
    }

    @PostMapping("/integration/info")
    public ResponseEntity integrationInfo(@RequestBody IntegrationParams integrationParams, @Value("${oci.instance.url}") String instanceUrl) {
        this.appHelper.sendRequestWithServerSentEvents(instanceUrl, integrationParams.getIntegrationId(), new IntegrationServiceInfo());
        this.appHelper.sendRequestToCheckActivationStatus(instanceUrl, integrationParams.getIntegrationId());
        this.appHelper.sendRequestToCheckActivationErrors(instanceUrl, integrationParams.getIntegrationId());

        return ResponseEntity.ok("Completed...");
    }
}
