package com.myhotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GuestNotFoundException.class)
	public final ResponseEntity<Object> handleGuestExceptions(GuestNotFoundException ex) {
		ExceptionSchema exceptionResponse = new ExceptionSchema(ex.getMessage(), ex.getDetails());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InternalServerException.class)
	public final ResponseEntity<Object> handleOtherExceptions(InternalServerException ex) {
		ExceptionSchema exceptionResponse = new ExceptionSchema(ex.getMessage(), ex.getDetails());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}