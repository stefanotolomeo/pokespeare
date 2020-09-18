package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResponseValidator {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private ObjectMapper mapper = new ObjectMapper();

	public PokemonDTO validatePokemonResponse(BaseHttpResponse response) {
		log.debug("Validating pokemon response: {}", response);
		try{
			baseResponseValidation(response);
			return mapper.readValue(response.getPayload(), PokemonDTO.class);
			// Do parse with jackson
		} catch (Exception e){
			throw new RuntimeException("Cannot validate pokemon response", e);
		}

	}

	// TODO: manage maximum request per hour to shakespeare API
	/*
	"error": {
 --->         "code": 429,
 --->         "message": "Too Many Requests: Rate limit of 5 requests per hour exceeded. Please wait for 59 minutes and 1 second."
 --->     }
 ---> }'}
	 */
	public ShakespeareDTO validateShakespeareResponse(BaseHttpResponse response) {
		log.debug("Validating shakespeare response: {}", response);

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
