package com.qterminals.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qterminals.dto.IntegrationServiceActivationError;
import com.qterminals.dto.IntegrationServiceActivationStatus;
import com.qterminals.dto.IntegrationServiceInfo;
import com.qterminals.dto.IntegrationServiceResponse;
import com.qterminals.generic.rest.GenericREST;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class AppHelper<T> extends GenericREST<T> {
    private ObjectMapper objectMapper;

    @Resource
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public boolean createDirectory(String directoryName, String directoryPath) {
        Path path = Path.of(directoryPath + "/" + directoryName);

        try {
            if (Files.exists(path)) {
                File[] allContents = path.toFile().listFiles();
                for (File file : allContents) {
                    Files.deleteIfExists(Path.of(file.toURI()));
                }
            } else {
                Files.createDirectory(path);
            }

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T mapJsonToEntity(String json, Class claxx){
        try {
            return (T) objectMapper.readValue(json, claxx);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequestWithServerSentEvents(String instanceUrl, String integrationId) {
        String integrationUrl = String.format("%s/ic/api/integration/v1/integrations/%s", instanceUrl, integrationId);
        IntegrationServiceResponse integrationServiceResponse = sendRequest(integrationUrl);
        sendEventToClient((T) integrationServiceResponse);
    }

    public void sendRequestWithServerSentEvents(String instanceUrl, String integrationId, Object claxx) {
        String integrationUrl = String.format("%s/ic/api/integration/v1/integrations/%s", instanceUrl, integrationId);
        IntegrationServiceResponse integrationServiceResponse = sendRequest(integrationUrl);

        if(claxx instanceof IntegrationServiceInfo){
            IntegrationServiceInfo integrationServiceParams = (IntegrationServiceInfo) this.mapJsonToEntity(integrationServiceResponse.getBody(), claxx.getClass());
            sendEventToClient((T) integrationServiceParams);
            return;
        }

        sendEventToClient((T) integrationServiceResponse);
    }

    public void sendRequestToCheckActivationErrors(String instanceUrl, String integrationId) {
        String activationErrorsURL = String.format("%s/ic/api/integration/v1/integrations/%s/activationErrors", instanceUrl, integrationId);
        IntegrationServiceResponse integrationServiceResponse = sendRequest(activationErrorsURL);
        String responseBody = integrationServiceResponse.getBody();
        IntegrationServiceActivationError integrationServiceActivationError = (IntegrationServiceActivationError) this.mapJsonToEntity(responseBody, IntegrationServiceActivationError.class);

        sendEventToClient((T) integrationServiceActivationError);
    }

    public void sendRequestToCheckActivationStatus(String instanceUrl, String integrationId) {
        String activationStatusUrl = String.format("%s/ic/api/integration/v1/integrations/%s/activationStatus", instanceUrl, integrationId);
        IntegrationServiceResponse integrationServiceResponse = sendRequest(activationStatusUrl);
        String responseBody = integrationServiceResponse.getBody();
        IntegrationServiceActivationStatus integrationServiceActivationStatus = (IntegrationServiceActivationStatus) this.mapJsonToEntity(responseBody, IntegrationServiceActivationStatus.class);

        sendEventToClient((T) integrationServiceActivationStatus);
    }

    public IntegrationServiceResponse sendRequest(String integrationUrl) {
        IntegrationServiceResponse integrationServiceResponse = new IntegrationServiceResponse();

        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(new String(integrationUrl.getBytes(), StandardCharsets.UTF_8)))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .header("Authorization", "Basic cXRpY3NpbnRlZ3JhdGlvbjpPckBjbGUtMjAyMDIw")
                    .GET()
                    .build();

            var client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            integrationServiceResponse.setStatusCode(response.statusCode());
            integrationServiceResponse.setBody(response.body());
            log.info(String.valueOf(integrationServiceResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return integrationServiceResponse;
    }

    public void allIntegrations(String instanceUrl) {
        try {
            String integration = String.format("%s/ic/api/integration/v1/integrations", instanceUrl);

            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(new String(integration.getBytes(), StandardCharsets.UTF_8)))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .header("Authorization", "Basic cXRpY3NpbnRlZ3JhdGlvbjpPckBjbGUtMjAyMDIw")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.valueOf(response.statusCode()));
            log.info(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
