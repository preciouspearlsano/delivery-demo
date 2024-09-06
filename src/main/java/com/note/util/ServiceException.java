package com.note.util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ResponseEntity<Object> toResponseEntity() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value()+"", getMessage()));
    }

    public static class ErrorResponse {
        private String respCode;
        private String respDesc;

        public ErrorResponse(String respCode, String respDesc) {
            this.respCode = respCode;
            this.respDesc = respDesc;
        }

        public String getRespCode() {
            return respCode;
        }

        public String getRespDesc() {
            return respDesc;
        }
    }
}