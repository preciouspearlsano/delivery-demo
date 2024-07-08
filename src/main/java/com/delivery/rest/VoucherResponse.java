package com.delivery.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class VoucherResponse extends ResponseDefault {
	
	private String code;
    private Double discount;
    private String expiry;

}
