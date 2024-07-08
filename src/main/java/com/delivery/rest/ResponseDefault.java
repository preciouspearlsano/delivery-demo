package com.delivery.rest;

import lombok.Data;

@Data
public class ResponseDefault {
	private String status;
	private String statusCode;
	private String description;
}
