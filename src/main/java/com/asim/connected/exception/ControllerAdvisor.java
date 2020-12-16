package com.asim.connected.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NullPointerException.class) 
	public ResponseEntity<?> cityError() { 
		return new ResponseEntity<String>("Either destination or origin city does not exist or invalid", HttpStatus.BAD_REQUEST);
	}
}
