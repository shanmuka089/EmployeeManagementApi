package com.example.demo.exceptions;

public class JwtTokenMalformedException extends RuntimeException{
	public JwtTokenMalformedException(String desc) {
		super(desc);
	}

}
