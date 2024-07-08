package com.delivery.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryRequest {

	@NotNull(message = "Weight cannot be null")
    @JsonProperty("Weight")
    private Double weight;

    @NotNull(message = "Height cannot be null")
    @JsonProperty("Height")
    private Double height;

    @NotNull(message = "Width cannot be null")
    @JsonProperty("Width")
    private Double width;

    @NotNull(message = "Length cannot be null")
    @JsonProperty("Length")
    private Double length;
}
