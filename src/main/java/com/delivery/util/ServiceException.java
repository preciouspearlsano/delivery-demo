package com.delivery.util;
public class ServiceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4500899299040111692L;

	public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}