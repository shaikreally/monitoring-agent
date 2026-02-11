package com.agent.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
public class HealthPayload {

    private String tenantId;
    private String agentId;
    private String serviceName;
    private String environment;
    private String serviceInstanceId;
    private String status; // UP / DOWN
    private long responseTimeMs;
    private Instant timestamp;
    private Map<String, String> details;
}