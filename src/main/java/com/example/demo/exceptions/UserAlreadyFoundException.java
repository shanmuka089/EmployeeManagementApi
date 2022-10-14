package com.example.demo.exceptions;

public class UserAlreadyFoundException extends RuntimeException{
	public UserAlreadyFoundException(String desc) {
		super(desc);
	}
}
