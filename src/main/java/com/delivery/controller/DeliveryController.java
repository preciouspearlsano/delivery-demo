package com.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.rest.DeliveryRequest;
import com.delivery.rest.DeliveryResponse;
import com.delivery.rest.VoucherResponse;
import com.delivery.service.DeliveryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/delivery/v1")
@Tag(name = "Demo (Mynt Exam) Delivery API Sample", description = "Delivery Services for Mynt Exam Demo")
public class DeliveryController {
	
	private final DeliveryService deliveryService;

	@PostMapping(path = "/total-cost", consumes = "application/json", produces = "application/json")
	@Operation(summary = "GET DELIVERY TOTAL COST PROCESS", description = "Get Delivery Cost")
	public ResponseEntity<DeliveryResponse> getTotalCost(@RequestParam("voucher") String voucher, @Valid @RequestBody(required = true) DeliveryRequest request) {
		return new ResponseEntity<>(deliveryService.getTotalCost(request, voucher), HttpStatus.OK);
	}

	@GetMapping(path = "/voucher/{voucher}", produces = "application/json")
	@Operation(summary = "GET VOUCHER COST PROCESS FOR ROUND ROBIN OFFLINE SCENARIO", description = "Get Voucher Cost For Round Robin Offline Scenario")
	public ResponseEntity<VoucherResponse> getVoucher(@PathVariable("voucher") String voucher, @RequestParam("key") String key) {
		return new ResponseEntity<>(deliveryService.getVoucher(voucher, key), HttpStatus.OK);
	}
}
