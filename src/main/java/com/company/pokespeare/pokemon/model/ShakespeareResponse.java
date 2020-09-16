package com.company.pokespeare.pokemon.model;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.http.model.PostResponse;

public class ShakespeareResponse extends PostResponse {

	public ShakespeareResponse(BaseHttpResponse baseHttpResponse){
		super(baseHttpResponse.getStatusCode(), baseHttpResponse.getPayload());
	}

	public ShakespeareResponse(int statusCode, String payload) {
		super(statusCode, payload);
	}
}
