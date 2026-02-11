
package com.agent.service;

import com.agent.config.AgentConfig;
import com.agent.model.HealthPayload;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ReporterService {

    private static final Logger logger = LogManager.getLogger(ReporterService.class);

    private final AgentConfig config;
    private final RestTemplate restTemplate;

    public void report(HealthPayload payload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", config.getApiKey());

        HttpEntity<HealthPayload> request =
                new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForEntity(
                    config.getBackendUrl() + "/api/v1/health/report",
                    request,
                    Void.class
            );
        } catch (Exception ex) {
            logger.warn(
                    "Failed to report health: service={} instance={}",
                    payload.getServiceName(),
                    payload.getServiceInstanceId(),
                    ex
            );
        }
    }
}
