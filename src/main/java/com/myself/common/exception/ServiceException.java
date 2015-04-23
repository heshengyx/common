package com.myself.common.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 4514891174875747350L;
	
	public ServiceException(String message) {
		super(message);
	}
}
