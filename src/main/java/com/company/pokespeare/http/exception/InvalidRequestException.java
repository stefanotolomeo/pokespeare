package com.company.pokespeare.http.exception;

public class InvalidRequestException extends Exception {

	public InvalidRequestException(String message) {
		super(message);
	}

	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
