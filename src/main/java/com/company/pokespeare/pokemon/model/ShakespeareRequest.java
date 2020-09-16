package com.company.pokespeare.pokemon.model;

import com.company.pokespeare.http.model.PostRequest;

import java.util.TreeMap;

public class ShakespeareRequest extends PostRequest {

	private final String PARAM_TEXT = "text";

	public ShakespeareRequest(String url, String textValue) {
		// TreeMap<String, String> params = new TreeMap<>();
		// params.put(PARAM_TEXT, textValue);
		super(url, new TreeMap<String, String>(){{put("text", textValue);}});
	}
}
