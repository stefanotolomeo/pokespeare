package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.company.pokespeare.pokemon.exception.ResponseType;
import com.company.pokespeare.pokemon.exception.ResponseValidationException;
import com.company.pokespeare.pokemon.exception.ShakespeareLimitException;
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
		try {
			Preconditions.checkArgument(response != null && response.getPayload() != null, "Response from Pokemon-API is null");

			switch (response.getStatusCode()) {
			case 200:
				return mapper.readValue(response.getPayload(), PokemonDTO.class);
			case 400:
			case 404:
				throw new Exception("Bad request to Pokemon-API");
			default:
				String msg = String.format("Response-status=%s is NOT OK", response.getStatusCode());
				throw new Exception(msg);
			}

		} catch (Exception e) {
			throw new ResponseValidationException(ResponseType.POKEMON, "Validation failed for Pokemon-API response", e);
		}

	}

	public ShakespeareDTO validateShakespeareResponse(BaseHttpResponse response) {
		log.debug("Validating shakespeare response: {}", response);

		try {

			Preconditions.checkArgument(response != null && response.getPayload() != null, "Response from Shakespeare-API is null");

			switch (response.getStatusCode()) {
			case 200:
				return mapper.readValue(response.getPayload(), ShakespeareDTO.class);
			case 400:
				throw new Exception("Bad request to Shakespeare-API");
			case 429:
				/*
					"code": 429,
					"message": "Too Many Requests: Rate limit of 5 requests per hour exceeded. Please wait for 59 minutes and 1 second."
				*/
				throw new ShakespeareLimitException(
						"Too Many Requests: Rate limit of 5 requests per hour exceeded. Please wait for 59 minutes and 1 second.");
			default:
				String msg = String.format("Response-status=%s is NOT OK", response.getStatusCode());
				throw new Exception(msg);
			}

		} catch (Exception e) {
			throw new ResponseValidationException(ResponseType.SHAKESPEARE, "Validation failed for Shakespeare-API response", e);
		}

	}
}
