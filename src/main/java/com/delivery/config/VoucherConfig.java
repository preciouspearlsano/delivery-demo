package com.delivery.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "voucher")
@Data
public class VoucherConfig {
	private Map<String, Double> discounts;
    private Map<String, String> expiries;
}
