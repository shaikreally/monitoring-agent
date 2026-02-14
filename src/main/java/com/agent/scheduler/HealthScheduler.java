package com.agent.scheduler;

import com.agent.config.AgentConfig;
import com.agent.model.HealthPayload;
import com.agent.service.HealthCollector;
import com.agent.service.ReporterService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HealthScheduler {

    private static final Logger logger = LogManager.getLogger(HealthScheduler.class);

    private final AgentConfig config;
    private final HealthCollector collector;
    private final ReporterService reporter;

    @Scheduled(fixedDelayString = "${agent.health-interval-ms:2000}")
    public void run() {

        HealthPayload payload = collector.collect(
                config.getTenantId(),
                config.getAgentId(),
                config.getServiceName(),
                config.getEnvironment(),
                config.getServiceInstanceId(),
                config.getHealthUrl()
        );

        reporter.report(payload);

        logger.debug(
                "Health sent: service={} status={} responseTimeMs={}",
                payload.getServiceName(),
                payload.getStatus(),
                payload.getResponseTimeMs()
        );
    }
}
