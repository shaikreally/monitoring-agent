package com.agent.service;

import com.agent.model.HealthPayload;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HealthCollector {

    private static final Logger logger = LogManager.getLogger(HealthCollector.class);

    private final RestTemplate restTemplate;

    public HealthPayload collect(
            String tenantId,
            String agentId,
            String serviceName,
            String environment,
            String serviceInstanceId,
            String healthUrl
    ) {

        long start = System.currentTimeMillis();
        String status = "DOWN";
        Map<String, String> details = new HashMap<>();

        try {
            Map<String, Object> response =
                    restTemplate.getForObject(healthUrl, Map.class);

            if (response != null && response.get("status") != null) {
                status = response.get("status").toString();
            }
        } catch (Exception ex) {
            details.put("error", ex.getClass().getSimpleName());
            logger.warn("Health check failed for {}", serviceName, ex);
        }

        return HealthPayload.builder()
                .tenantId(tenantId)
                .agentId(agentId)
                .serviceName(serviceName)
                .environment(environment)
                .serviceInstanceId(serviceInstanceId)
                .status(status)
                .responseTimeMs(System.currentTimeMillis() - start)
                .timestamp(Instant.now())
                .details(details)
                .build();
    }
}
