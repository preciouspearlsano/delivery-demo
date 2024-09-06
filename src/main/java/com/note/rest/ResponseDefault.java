package com.note.rest;

import lombok.Data;

@Data
public class ResponseDefault {
    private String statusCode;
    private String statusDescription;
    private String errorCode;
    private String errorDescription;
}