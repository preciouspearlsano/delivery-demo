package com.delivery.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryResponse extends ResponseDefault {

	private String deliveryCost;
	private boolean isValid;
}
