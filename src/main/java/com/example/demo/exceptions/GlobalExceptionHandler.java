package com.example.demo.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.errors.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(EmployeeNotFoundException e) {
		ErrorResponse err=new ErrorResponse();
		err.setErrorMessage(e.getMessage());
		err.setStatusCode(HttpStatus.BAD_REQUEST.value());
		err.setDate(LocalDate.now());
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.BAD_REQUEST);
	}

}
