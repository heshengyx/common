package com.myself.common.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 4514891174875747350L;
	private String message;
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable e) {
		super(message, e);
		this.message = message; 
	}

	public String getMessage() {
		return message;
	}
}
