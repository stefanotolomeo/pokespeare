package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseValidator {

	public void validatePokemonResponse(BaseHttpResponse response) {
		// TODO: implement logic
		if(response == null){
			throw new RuntimeException("Cannot validate pokemon response");
		}

	}

	public void validateShakespeareResponse(BaseHttpResponse response) {
		// TODO: implement logic
		if(response == null){
			throw new RuntimeException("Cannot validate shakespeare response");
		}

	}
}
