package com.delivery.gateway;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.delivery.util.GatewayName;
import com.delivery.util.Msg;
import com.delivery.util.ServiceException;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class CustomizeGateway {
	
	@Value("${default.gateway.online.voucher}")
	private String onlineVoucher;
	
	@Value("${default.gateway.offline.voucher}")
	private String offlineVoucher;
	
	private final WebClient webClient;

    public CustomizeGateway(@Qualifier("gatewayWebClient") WebClient webClient) {
        this.webClient = webClient;
    }
    
    public String doCommunicate(Msg msg, GatewayName name) {
        String offlineName = null;
        try {
            if (name.getMessageId().equalsIgnoreCase(GatewayName.online_voucher.getMessageId())) {
                offlineName = GatewayName.offline_voucher.getMessageId();
                log.info("Communicate to : {} ", onlineVoucher.replace("{voucher}", msg.getDATA_1()));
                return webClient.get()
                        .uri(onlineVoucher.replace("{voucher}", msg.getDATA_1()))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block(); // Blocking call, handle asynchronously in a reactive flow
            }
        } catch (Exception e) {
            if (String.valueOf(offlineName).equalsIgnoreCase(GatewayName.offline_voucher.getMessageId())) {
                log.info("Communicate to : {} ", offlineVoucher.replace("{voucher}", msg.getDATA_1()));
                return webClient.get()
                        .uri(offlineVoucher.replace("{voucher}", msg.getDATA_1()))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block(); // Blocking call, handle asynchronously in a reactive flow
            }
            throw new ServiceException("Unknown gateway communication.", e);
        }
        return null;
    }
}
