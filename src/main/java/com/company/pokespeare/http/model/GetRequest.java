package com.company.pokespeare.http.model;

import java.util.TreeMap;

public class GetRequest extends BaseHttpRequest {

	TreeMap<String, String> params;

	/**
	 * Used for request without parameters
	 */
	public GetRequest(String url) {
		super(url);
	}

	/**
	 * Used for request with params
	 */
	public GetRequest(String url, TreeMap params) {
		super(url);
		this.params = params;
	}

	public TreeMap<String, String> getParams() {
		return params;
	}
}
