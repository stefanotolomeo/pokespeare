package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
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
	void baseResponseValidation_Test(){
		// Test_1: Null Input
		NullPointerException exp_1 = Assertions.assertThrows(
				NullPointerException.class,
				() -> responseValidator.baseResponseValidation(null));
		Assertions.assertEquals("Response from PokemonDTO is null", exp_1.getMessage());

		// Test_2: Null Payload
		NullPointerException exp_2 = Assertions.assertThrows(
				NullPointerException.class,
				() -> responseValidator.baseResponseValidation(new BaseHttpResponse(12345, null)));
		Assertions.assertEquals("Response-Payload from Pokemon is null", exp_2.getMessage());

		// Test_3: Bad response status
		IllegalArgumentException exp_3 = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> responseValidator.baseResponseValidation(new BaseHttpResponse(12345, "Just a payload")));
		Assertions.assertEquals("Response-Status is NOT OK: 12345", exp_3.getMessage());
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
