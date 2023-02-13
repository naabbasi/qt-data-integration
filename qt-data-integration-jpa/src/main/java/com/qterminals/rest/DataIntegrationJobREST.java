package com.qterminals.rest;

import com.qterminals.constant.ModuleName;
import com.qterminals.ftp.FTPUtils;
import com.qterminals.generic.rest.GenericREST;
import com.qterminals.services.DataIntegrationJobService;
import com.qterminals.util.AppHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/data-integration-job")
public class DataIntegrationJobREST extends GenericREST {
    private DataIntegrationJobService dataIntegrationJobService;
    private AppHelper appHelper;
    private FTPUtils ftpUtils;

    public DataIntegrationJobREST(DataIntegrationJobService dataIntegrationJobService, AppHelper appHelper, FTPUtils ftpUtils) {
        this.dataIntegrationJobService = dataIntegrationJobService;
        this.appHelper = appHelper;
        this.ftpUtils = ftpUtils;
    }

    @GetMapping(value = "/latest-job-run-date/{moduleName}")
    public ResponseEntity getLastRunDate(@PathVariable("moduleName") ModuleName moduleName) {
        Date latestJobRunDate = this.dataIntegrationJobService.getLatestJobRunDate(moduleName);

        Map<String, Object> response = new HashMap<>();
        response.put("latestJobRunDate", latestJobRunDate);
        return ResponseEntity.ok(response);
    }
}
