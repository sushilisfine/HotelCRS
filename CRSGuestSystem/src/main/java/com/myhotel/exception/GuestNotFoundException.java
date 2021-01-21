package com.myhotel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public GuestNotFoundException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public GuestNotFoundException(String message) {
		this(message, "Please Try with other id");
	}

	public GuestNotFoundException(Long id) {
		this("Guest Not Found for id: " + id, "Please Try Again");
	}

	public GuestNotFoundException() {
		this("Guest Not Found", "Please Try Again");
	}
}