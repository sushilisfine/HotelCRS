package com.myhotel.managment.util;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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