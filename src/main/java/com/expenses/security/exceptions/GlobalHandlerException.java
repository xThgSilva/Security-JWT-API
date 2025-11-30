package com.expenses.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(ExpenseNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ExpenseNotFoundException ex, HttpServletRequest request){
		ErrorResponse error = new ErrorResponse (
			HttpStatus.NOT_FOUND.value(),
			"Not Found.",
			ex.getMessage()
		);

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NotPermissionException.class)
	public ResponseEntity<ErrorResponse> handleNotPermission(NotPermissionException ex, HttpServletRequest request){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.FORBIDDEN.value(),
				"Forbidden.",
				ex.getMessage()
		);
		
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(EmailAlreadyExists.class)
	public ResponseEntity<ErrorResponse> handleNotPermission(EmailAlreadyExists ex, HttpServletRequest request){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				"Conflict.",
				ex.getMessage()
		);
		
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleNotPermission(InvalidCredentialsException ex, HttpServletRequest request){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.UNAUTHORIZED.value(),
				"Unauthorized.",
				ex.getMessage()
		);
		
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
}
