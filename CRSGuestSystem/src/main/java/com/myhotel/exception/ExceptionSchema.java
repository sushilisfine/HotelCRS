package com.myhotel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionSchema {

	private String message;
	private String details;

	protected ExceptionSchema() {
	}

	public ExceptionSchema(String message, String details) {
		this.message = message;
		this.details = details;
	}
}
