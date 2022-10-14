package com.example.demo.exceptions;

public class JwtTokenMissingException extends RuntimeException{
	public JwtTokenMissingException(String desc) {
		super(desc);
	}

}
