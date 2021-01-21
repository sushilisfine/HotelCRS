package com.myhotel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public InternalServerException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public InternalServerException() {
		this("Internal Server", "Please Try Again");
	}

	public InternalServerException(String message) {
		this(message, "Please Try Again");
	}
}