package com.delivery.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.delivery.config.PriorityRulesConfig;
import com.delivery.config.VoucherConfig;
import com.delivery.gateway.CustomizeGateway;
import com.delivery.rest.DeliveryRequest;
import com.delivery.rest.DeliveryResponse;
import com.delivery.rest.VoucherResponse;
import com.delivery.util.GatewayName;
import com.delivery.util.Msg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeliveryService {

	private final PriorityRulesConfig rulesConfig;
	private final VoucherConfig voucherConfig;
	private final CustomizeGateway customGateway;

	@Value("${default.gateway.voucherApikey:apikey}")
	public String apikey;
	
	private String toPretty(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

	public DeliveryResponse getTotalCost(DeliveryRequest request, String voucher) {
		log.info("Inside getTotalCost : {} {}", toPretty(request), voucher);
		DeliveryResponse response = new DeliveryResponse();
		VoucherResponse voucherResponse = new VoucherResponse();
		response.setStatus("Success");
		response.setStatusCode("00");

		// Include voucher details if voucher code is provided
		if (voucher != null && !voucher.isBlank()) {
			Msg msg = new Msg();
			msg.setDATA_1(voucher);
			String gatewayResponse = customGateway.doCommunicate(msg, GatewayName.online_voucher);
			voucherResponse = new Gson().fromJson(gatewayResponse, VoucherResponse.class);

		}

		// Apply priority rules based on DeliveryRequest
		double weight = request.getWeight();
		double volume = calculateVolume(request.getHeight(), request.getWidth(), request.getLength());

		double deliveryCost = applyPriorityRules(weight, volume);

		// If no matching rule found, set status to failed
		if (deliveryCost == 0.0) {
			response.setStatus("Failed");
			response.setStatusCode("01");
			response.setDescription("No matching rule found");
		} else {
			// Apply voucher discount if applicable
			if (voucherResponse.getDiscount() != null && voucherResponse.getDiscount() > 0) {
				deliveryCost -= voucherResponse.getDiscount();
				// Ensure delivery cost doesn't go negative
				if (deliveryCost < 0) {
					deliveryCost = 0;
				}
			}
			response.setDeliveryCost(String.valueOf(deliveryCost));
		}

		return response;
	}

    // Helper method to calculate volume from dimensions
    private double calculateVolume(Double height, Double width, Double length) {
        double h = height;
        double w = width;
        double l = length;
        return h * w * l;
    }

    // Method to apply priority rules based on weight and volume
    private double applyPriorityRules(double weight, double volume) {
        double deliveryCost = 0.0;

        // Iterate through rules based on priority from rulesConfig
        for (PriorityRulesConfig.Rule rule : rulesConfig.getRules()) {
            switch (rule.getRuleName()) {
                case "Reject":
                    if (weight > Double.parseDouble(rule.getConditions().get(0).getMaxValue())) {
                        return 0.0; // Return 0 cost for rejected parcels
                    }
                    break;
                case "Heavy Parcel":
                    if (weight > Double.parseDouble(rule.getConditions().get(0).getMinValue()) &&
                            weight <= Double.parseDouble(rule.getConditions().get(0).getMaxValue())) {
                        deliveryCost += rule.getConditions().get(0).getFee() * weight;
                    }
                    break;
                case "Small Parcel":
                case "Medium Parcel":
                case "Large Parcel":
                    if (volume > Double.parseDouble(rule.getConditions().get(0).getMinValue()) &&
                            volume <= Double.parseDouble(rule.getConditions().get(0).getMaxValue())) {
                        deliveryCost += rule.getConditions().get(0).getFee() * volume;
                    }
                    break;
            }
        }

        return deliveryCost;
    }
    
    
    
	public VoucherResponse getVoucher(String voucherCode, String key) {
		var response = new VoucherResponse();
		if (key.equalsIgnoreCase(apikey)) {
			Map<String, Double> voucherDiscount = voucherConfig.getDiscounts();
			Map<String, String> voucherExpiry = voucherConfig.getExpiries();
			if (voucherDiscount.containsKey(voucherCode)) {
				response.setCode(voucherCode);
				response.setDiscount(voucherDiscount.get(voucherCode));
				response.setExpiry(voucherExpiry.get(voucherCode));
				response.setStatusCode("00");
				response.setStatusCode("Success");
			} else {
				response.setStatusCode("01");
				response.setStatusCode("Failed");
				response.setDescription("Voucher Code Not Found");
			}
		}
		return response;
	}

}
