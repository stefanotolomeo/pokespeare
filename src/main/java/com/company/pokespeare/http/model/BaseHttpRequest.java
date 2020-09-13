package com.company.pokespeare.http.model;

import org.apache.http.client.methods.HttpGet;

public class BaseHttpRequest {

	private String requestUri;
	private HttpGet httpGetRequest;

	public BaseHttpRequest(String requestUri) {
		this.requestUri = requestUri;
		this.httpGetRequest = new HttpGet(requestUri);
	}

	public String getRequestUri() {
		return requestUri;
	}

	public HttpGet getHttpGetRequest() {
		return httpGetRequest;
	}

	@Override
	public String toString() {
		return "BaseHttpRequest{" + "requestUri='" + requestUri + '\'' + ", httpGetRequest=" + httpGetRequest + '}';
	}
}
