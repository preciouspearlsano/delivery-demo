package com.delivery.util;

public enum GatewayName {
	
	offline_voucher,
	online_voucher;
	
	public String getMessageId() {
        return this.name();
    }
}
