package com.qterminals.rest;

import com.qterminals.constant.ModuleName;
import com.qterminals.dto.IntegrationParams;
import com.qterminals.entities.DataIntegrationJob;
import com.qterminals.entities.GeneratePayment;
import com.qterminals.ftp.FTPUtils;
import com.qterminals.generic.rest.GenericREST;
import com.qterminals.services.DataIntegrationJobService;
import com.qterminals.services.GeneratePaymentService;
import com.qterminals.util.AppHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class GeneratePaymentREST extends GenericREST {
    private DataIntegrationJobService dataIntegrationJobService;
    private GeneratePaymentService generatePaymentService;
    private AppHelper appHelper;
    private FTPUtils ftpUtils;

    public GeneratePaymentREST(DataIntegrationJobService dataIntegrationJobService, GeneratePaymentService generatePaymentService,
                               AppHelper appHelper, FTPUtils ftpUtils) {
        this.dataIntegrationJobService = dataIntegrationJobService;
        this.generatePaymentService = generatePaymentService;
        this.appHelper = appHelper;
        this.ftpUtils = ftpUtils;
    }

    @GetMapping(value = "/last-run-date")
    public ResponseEntity getLastRunDate() {
        Date lastRunDate = this.generatePaymentService.getLastRunDate();
        Map<String, Object> response = new HashMap<>();
        //response.put("lastRunDate", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(lastRunDate));
        response.put("lastRunDate", lastRunDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generatePaymentFiles(@RequestBody IntegrationParams integrationParams, @Value("${oci.instance.url}") String instanceUrl) {
        final Map<String, Object> responseMap = new HashMap<>();

        String url = String.format("%s/ic/api/integration/v1/integrations/%s/schedule/jobs", instanceUrl, integrationParams.getIntegrationId());

        /*try {
            Map<String, Object> payload = new HashMap<>();
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            payload.put("var_sp_last_successful_runtime", integrationParams.getPaymentDate() == null ? "" : dateTimeFormat.format(integrationParams.getPaymentDate()));
            String params = appHelper.getObjectMapper().writeValueAsString(payload);
            log.info("integrationServiceParams: {}", params);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(new String(url.getBytes(), StandardCharsets.UTF_8)))
                    .header("Authorization", "Basic cXRpY3NpbnRlZ3JhdGlvbjpPckBjbGUtMjAyMDIw")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();

            var client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseMap.put("paymentDate", integrationParams.getPaymentDate());
            responseMap.put("ociServiceStatus", response.statusCode());
            responseMap.put("ociServiceBody", response.body());
            log.info("generatePaymentFiles - status code {}", response.statusCode());
            log.info("generatePaymentFiles - body {}", response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        GeneratePayment generatePayment = new GeneratePayment();
        generatePayment.setLastRunDate(integrationParams.getPaymentDate() == null ? new Date() : integrationParams.getPaymentDate());
        this.generatePaymentService.save(generatePayment);

        DataIntegrationJob dataIntegrationJob = new DataIntegrationJob();
        dataIntegrationJob.setJobRunDate(new Date());
        dataIntegrationJob.setModuleName(ModuleName.PAYMENT);
        this.dataIntegrationJobService.save(dataIntegrationJob);

        return ResponseEntity.ok(responseMap);
    }

    @GetMapping(value = "/payment-file-info")
    public ResponseEntity downloadPaymentFiles() {
        FTPFile[] ftpFiles = this.ftpUtils.list("/FISTest/New");
        List<String> recentFiles = new ArrayList<>();
        Date lastGeneratedPaymentFileDate = this.dataIntegrationJobService.getLatestJobRunDate(ModuleName.PAYMENT);

        Date lastFileDateTime = null;
        for(FTPFile ftpFile : ftpFiles){
            Calendar calendar = ftpFile.getTimestamp();
            LocalDate fileDate = this.appHelper.convertDateToLocalDate(calendar.getTime());
            LocalDate lastGeneratedPaymentFileLocalDate = this.appHelper.convertDateToLocalDate(lastGeneratedPaymentFileDate);
            if(!fileDate.isBefore(lastGeneratedPaymentFileLocalDate)){
                if(ftpFile.getName().startsWith("Payments")){
                    if(lastFileDateTime != null){
                        LocalDateTime localDateTime = this.appHelper.convertDateToLocalDateTime(lastFileDateTime);
                        LocalDateTime currentFileDateTime = this.appHelper.convertDateToLocalDateTime(calendar.getTime());
                        if(localDateTime.isBefore(currentFileDateTime)){
                            recentFiles.clear();
                            recentFiles.add(ftpFile.getName());
                            log.info("Today's files: {}", ftpFile.getName());
                        }
                    } else if(lastFileDateTime == null){
                        recentFiles.add(ftpFile.getName());
                        lastFileDateTime = calendar.getTime();
                    }
                }
            }
        }

        return ResponseEntity.ok(recentFiles);
    }

    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadPaymentFile(@PathVariable("fileName") String fileName) {
        byte[] fileAsByteArray = this.ftpUtils.downloadFile(fileName, "/FISTest/New/");

        ByteArrayResource resource = new ByteArrayResource(fileAsByteArray);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(value = "/upload-to-bank")
    public ResponseEntity uploadToBank(@RequestBody IntegrationParams integrationParams) {
        GeneratePayment generatePayment = new GeneratePayment();
        generatePayment.setLastRunDate(integrationParams.getPaymentDate());

        this.generatePaymentService.save(generatePayment);

        Map<String, Object> response = new HashMap<>();
        response.put("uploadStatus", "File has been uploaded to Bank");
        return ResponseEntity.ok(response);
    }
}
