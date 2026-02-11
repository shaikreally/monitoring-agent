package com.agent.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "agent")
public class AgentConfig {

    /* Agent identity */
    private String tenantId;
    private String agentId;
    private String serviceName;
    private String serviceInstanceId;
    private String environment;

    /* Health */
    private String healthUrl;

    /* Backend */
    private String backendUrl;
    private String apiKey;

    /* Scheduler */
    private long healthIntervalMs = 10000;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
