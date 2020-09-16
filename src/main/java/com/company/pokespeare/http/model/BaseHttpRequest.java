package com.company.pokespeare.http.model;

public abstract class BaseHttpRequest {

	private String url;

	public BaseHttpRequest(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
