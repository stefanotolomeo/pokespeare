package com.company.pokespeare.pokemon.exception;

public class ResponseValidationException extends RuntimeException {

	private ResponseType responseType;

	public ResponseValidationException(String message) {
		super(message);
	}

	public ResponseValidationException(ResponseType responseType, String message, Throwable cause) {

		super(message, cause);
		this.responseType = responseType;
	}

	public ResponseType getResponseType() {
		return responseType;
	}
}
