package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.exception.ResponseType;
import com.company.pokespeare.pokemon.exception.ResponseValidationException;
import com.company.pokespeare.testconfig.BaseTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ResponseValidatorPokemonTest extends BaseTest {

	private final static String pokemonResponse1_path = "/fixture/pokemon/pokemon_response_1.json";

	private static String pokemonResponse_1;

	@Inject
	private ResponseValidator responseValidator;

	@BeforeAll
	static void setup() throws Exception {
		pokemonResponse_1 = IOUtils.toString(ResponseValidatorPokemonTest.class.getResourceAsStream(pokemonResponse1_path), UTF_8);
	}

	@Test
	void validatePokemonResponse_Input_Test() {
		// Test_1: Null Input
		ResponseValidationException exp_1 = Assertions
				.assertThrows(ResponseValidationException.class, () -> responseValidator.validatePokemonResponse(null));
		Assertions.assertEquals(ResponseType.POKEMON, exp_1.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_1.getMessage());
		Assertions.assertEquals("Response from Pokemon-API is null", exp_1.getCause().getMessage());

		// Test_2: Null Payload
		ResponseValidationException exp_2 = Assertions.assertThrows(ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(12345, null)));
		Assertions.assertEquals(ResponseType.POKEMON, exp_2.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_2.getMessage());
		Assertions.assertEquals("Response from Pokemon-API is null", exp_2.getCause().getMessage());

		// Test_3: Response Status is 400 (Bad request)
		ResponseValidationException exp_3 = Assertions.assertThrows(ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(400, "Just a payload")));
		Assertions.assertEquals(ResponseType.POKEMON, exp_3.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_3.getMessage());
		Assertions.assertEquals("Bad request to Pokemon-API", exp_3.getCause().getMessage());

		// Test_4: Response Status is 404 (Not found)
		ResponseValidationException exp_4 = Assertions.assertThrows(ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(404, "Just a payload")));
		Assertions.assertEquals(ResponseType.POKEMON, exp_4.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_4.getMessage());
		Assertions.assertEquals("Bad request to Pokemon-API", exp_4.getCause().getMessage());

		// Test_5: Other NOT_OK response status
		ResponseValidationException exp_5 = Assertions.assertThrows(ResponseValidationException.class,
				() -> responseValidator.validatePokemonResponse(new BaseHttpResponse(12345, "Just a payload")));
		Assertions.assertEquals(ResponseType.POKEMON, exp_5.getResponseType());
		Assertions.assertEquals("Validation failed for Pokemon-API response", exp_5.getMessage());
		Assertions.assertEquals("Response-status=12345 is NOT OK", exp_5.getCause().getMessage());
	}

	@Test
	void validatePokemonResponse_Test() {
		BaseHttpResponse resp = new BaseHttpResponse(200, pokemonResponse_1);
		PokemonDTO pokemonDTO = responseValidator.validatePokemonResponse(resp);

		Assertions.assertNotNull(pokemonDTO);
		Assertions.assertNotNull(pokemonDTO.getFlavorTextEntries());
		Assertions.assertEquals(154, pokemonDTO.getFlavorTextEntries().size());
		Assertions.assertEquals("Spits fire that\nis hot enough to\nmelt boulders.\fKnown to cause\nforest fires\nunintentionally.",
				pokemonDTO.getFlavorTextEntries().get(0).getFlavorText());

	}

}
