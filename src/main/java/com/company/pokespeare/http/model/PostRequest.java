package com.company.pokespeare.http.model;

import java.util.TreeMap;

// Currently not used
public class PostRequest extends BaseHttpRequest {

	TreeMap<String, String> params;

	public PostRequest(String url, TreeMap params) {
		super(url);
		this.params = params;
	}

	public TreeMap<String, String> getParams() {
		return params;
	}
}
