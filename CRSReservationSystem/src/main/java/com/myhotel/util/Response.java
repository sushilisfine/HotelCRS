package com.myhotel.util;

import org.apache.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

	private HttpStatus status;
	private String message;
	private T data;

	public Response(HttpStatus status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

}