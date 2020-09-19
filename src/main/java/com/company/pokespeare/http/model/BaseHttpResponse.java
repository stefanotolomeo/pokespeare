package com.company.pokespeare.http.model;

public class BaseHttpResponse {

	private int statusCode;
	private String payload;

	public BaseHttpResponse(int statusCode, String payload) {
		this.statusCode = statusCode;
		this.payload = payload;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getPayload() {
		return payload;
	}

	@Override
	public String toString() {
		return "BaseHttpResponse{" + "statusCode=" + statusCode + ", payload='" + payload + '\'' + '}';
	}
}
