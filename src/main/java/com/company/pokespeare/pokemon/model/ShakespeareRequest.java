package com.company.pokespeare.pokemon.model;

import com.company.pokespeare.config.Constants;
import com.company.pokespeare.http.model.GetRequest;

import java.util.TreeMap;

public class ShakespeareRequest extends GetRequest {

	public ShakespeareRequest(String url, String textValue) {
		super(url, new TreeMap<String, String>() {{
			put(Constants.PARAM_TEXT, textValue);
		}});
	}
}
