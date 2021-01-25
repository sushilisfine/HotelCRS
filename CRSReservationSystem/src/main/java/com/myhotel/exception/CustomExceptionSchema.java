package com.myhotel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomExceptionSchema {

	private String message;
	private String details;

	protected CustomExceptionSchema() {
	}

	public CustomExceptionSchema(String message, String details) {
		this.message = message;
		this.details = details;
	}
}
