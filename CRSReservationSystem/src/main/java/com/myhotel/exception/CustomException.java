package com.myhotel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public CustomException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public CustomException(String message) {
		this.message = message;
		this.details = "Please Try Again";
	}
}