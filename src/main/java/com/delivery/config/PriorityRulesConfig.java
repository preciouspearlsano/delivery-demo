package com.delivery.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "priority-rules")
@Data
public class PriorityRulesConfig {

    private List<Rule> rules;

    @Data
    public static class Rule {
        private int priority;
        private String ruleName;
        private List<Condition> conditions;
    }

    @Data
    public static class Condition {
        private String name;
        private String description;
        private String type;
        private String symbol;
        private String minValue;
        private String maxValue;
        private Double fee;
    }
}