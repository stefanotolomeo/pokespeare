package com.company.pokespeare.pokemon.model;

import com.company.pokespeare.http.model.GetRequest;

public class PokemonRequest extends GetRequest {

	public PokemonRequest(String requestUri) {
		super(requestUri);
	}
}
