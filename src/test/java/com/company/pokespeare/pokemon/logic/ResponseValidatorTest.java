package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.company.pokespeare.pokemon.exception.ResponseType;
import com.company.pokespeare.pokemon.exception.ResponseValidationException;
import com.company.pokespeare.testconfig.BaseTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ResponseValidatorTest extends BaseTest {

	private final static String shakespeareResponse1_path = "/fixture/shakespeare_response_1.json";
	private final static String pokemonResponse1_path = "/fixture/pokemon_response_1.json";

	private static String shakespeareResponse_1;
	private static String pokemonResponse_1;

	@Inject
	private ResponseValidator responseValidator;

	@BeforeAll
	static void setuo() throws Exception{

		shakespeareResponse_1 = IOUtils.toString(ResponseValidatorTest.class.getResourceAsStream(shakespeareResponse1_path), UTF_8);
		pokemonResponse_1 = IOUtils.toString(ResponseValidatorTest.class.getResourceAsStream(pokemonResponse1_path), UTF_8);
	}

	@Test
	void validatePokemonResponse_Input_Test(){
		// Test_1: Null Input
		ResponseValidationException exp_1 = Assertions.assertThrows(
				ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(null));
		Assertions.assertEquals(ResponseType.POKEMON, exp_1.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_1.getMessage());
		Assertions.assertEquals("Response from Pokemon-API is null", exp_1.getCause().getMessage());

		// Test_2: Null Payload
		ResponseValidationException exp_2 = Assertions.assertThrows(
				ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(12345, null)));
		Assertions.assertEquals(ResponseType.POKEMON, exp_2.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_2.getMessage());
		Assertions.assertEquals("Response from Pokemon-API is null", exp_2.getCause().getMessage());

		// Test_3: Response Status is 400 (Bad request)
		ResponseValidationException exp_3 = Assertions.assertThrows(
				ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(400, "Just a payload")));
		Assertions.assertEquals(ResponseType.POKEMON, exp_3.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_3.getMessage());
		Assertions.assertEquals("Bad request to Pokemon-API", exp_3.getCause().getMessage());

		// Test_4: Bad response status
		ResponseValidationException exp_4 = Assertions.assertThrows(
				ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(12345, "Just a payload")));
		Assertions.assertEquals(ResponseType.POKEMON, exp_4.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_4.getMessage());
		Assertions.assertEquals("Response-status=12345 is NOT OK", exp_4.getCause().getMessage());
	}

	@Test
	void validatePokemonResponse_Test(){
		// TODO
		BaseHttpResponse resp = new BaseHttpResponse(200, pokemonResponse_1);
		PokemonDTO pokemonDTO = responseValidator.validatePokemonResponse(resp);

		Assertions.assertNotNull(pokemonDTO);
		Assertions.assertNotNull(pokemonDTO.getFlavorTextEntries());
		Assertions.assertFalse(pokemonDTO.getFlavorTextEntries().isEmpty());

	}

	@Test
	void validateShakespeareResponse_Test(){
		// TODO

		BaseHttpResponse resp = new BaseHttpResponse(200, shakespeareResponse_1);
		ShakespeareDTO shakespeareDTO = responseValidator.validateShakespeareResponse(resp);

		Assertions.assertNotNull(shakespeareDTO);
		Assertions.assertNotNull(shakespeareDTO.getSuccess());
		Assertions.assertNotNull(shakespeareDTO.getContents());
	}

}
