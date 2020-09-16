package com.company.pokespeare.pokemon.model;

import com.company.pokespeare.http.model.GetResponse;

public class PokemonResponse extends GetResponse {

	public PokemonResponse(int statusCode, String payload) {
		super(statusCode, payload);
	}

}
