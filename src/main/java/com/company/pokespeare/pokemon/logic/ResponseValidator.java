package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class ResponseValidator {

	private ObjectMapper mapper = new ObjectMapper();

	public void validatePokemonResponse(BaseHttpResponse response) {
		// TODO: implement logic
		try{
			baseResponseValidation(response);
			PokemonDTO pokemonResponse = mapper.readValue(response.getPayload(), PokemonDTO.class);
			// Do parse with jackson
		} catch (Exception e){
			throw new RuntimeException("Cannot validate pokemon response", e);
		}

	}

	public ShakespeareDTO validateShakespeareResponse(BaseHttpResponse response) {
		// TODO: implement logic
		try{
			baseResponseValidation(response);
			return mapper.readValue(response.getPayload(), ShakespeareDTO.class);
			// Do parse with jackson
		} catch (Exception e){
			throw new RuntimeException("Cannot validate shakespeare response", e);
		}

	}

	void baseResponseValidation(BaseHttpResponse response){
		Preconditions.checkNotNull(response, "Response from PokemonDTO is null");
		Preconditions.checkNotNull(response.getPayload(), "Response-Payload from Pokemon is null");
		Preconditions.checkArgument(response.getStatusCode() == 200, "Response-Status is NOT OK: "+response.getStatusCode());
	}
}
