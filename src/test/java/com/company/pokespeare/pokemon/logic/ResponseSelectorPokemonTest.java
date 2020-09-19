package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.testconfig.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ResponseSelectorPokemonTest extends BaseTest {

	@Inject
	private ResponseSelector responseSelector;

	private static ObjectMapper mapper = new ObjectMapper();

	private final static String pokemonResponse1_path = "/fixture/pokemon/pokemon_response_1.json";
	private final static String pokemonResponse2_path = "/fixture/pokemon/pokemon_response_null.json";
	private final static String pokemonResponse3_path = "/fixture/pokemon/pokemon_response_empty.json";
	private final static String pokemonResponse4_path = "/fixture/pokemon/pokemon_response_no_english.json";

	private static String pokemonResponse_1;    // Valid JSON
	private static String pokemonResponse_2;    // No field "Flavor_text_entries"
	private static String pokemonResponse_3;    // Empty List for field Flavor_text_entries
	private static String pokemonResponse_4;    // No Flavor_text in english

	private static PokemonDTO pokemonDTO_1;
	private static PokemonDTO pokemonDTO_2;
	private static PokemonDTO pokemonDTO_3;
	private static PokemonDTO pokemonDTO_4;

	@BeforeAll
	static void setuo() throws Exception {
		pokemonResponse_1 = IOUtils.toString(ResponseSelectorPokemonTest.class.getResourceAsStream(pokemonResponse1_path), UTF_8);
		pokemonResponse_2 = IOUtils.toString(ResponseSelectorPokemonTest.class.getResourceAsStream(pokemonResponse2_path), UTF_8);
		pokemonResponse_3 = IOUtils.toString(ResponseSelectorPokemonTest.class.getResourceAsStream(pokemonResponse3_path), UTF_8);
		pokemonResponse_4 = IOUtils.toString(ResponseSelectorPokemonTest.class.getResourceAsStream(pokemonResponse4_path), UTF_8);

		pokemonDTO_1 = mapper.readValue(pokemonResponse_1, PokemonDTO.class);
		pokemonDTO_2 = mapper.readValue(pokemonResponse_2, PokemonDTO.class);
		pokemonDTO_3 = mapper.readValue(pokemonResponse_3, PokemonDTO.class);
		pokemonDTO_4 = mapper.readValue(pokemonResponse_4, PokemonDTO.class);
	}

	@Test
	void selectPokemonDescription_Input_Test() {

		// Test_1: Null Input
		RuntimeException exp_1 = Assertions.assertThrows(RuntimeException.class, () -> responseSelector.selectPokemonDescription(null));
		Assertions.assertEquals("Cannot select a pokemon description from PokemonDTO", exp_1.getMessage());
		Assertions.assertEquals("PokemonDTO is null", exp_1.getCause().getMessage());

		// Test_2: Flavor_Text_Entries not present in JSON
		RuntimeException exp_2 = Assertions
				.assertThrows(RuntimeException.class, () -> responseSelector.selectPokemonDescription(pokemonDTO_2));
		Assertions.assertEquals("Cannot select a pokemon description from PokemonDTO", exp_2.getMessage());
		Assertions.assertEquals("PokemonDTO is null", exp_2.getCause().getMessage());

		// Test_3: Flavor_Text_Entries not present in JSON
		RuntimeException exp_3 = Assertions
				.assertThrows(RuntimeException.class, () -> responseSelector.selectPokemonDescription(pokemonDTO_3));
		Assertions.assertEquals("Cannot select a pokemon description from PokemonDTO", exp_3.getMessage());
		Assertions.assertEquals("No value present", exp_3.getCause().getMessage());
	}

	@Test
	void selectPokemonDescription_Test() {
		String description = responseSelector.selectPokemonDescription(pokemonDTO_1);
		Assertions.assertNotNull(description);
		Assertions.assertEquals("Spits fire that\nis hot enough to\nmelt boulders.\fKnown to cause\nforest fires\nunintentionally.",
				description);
	}
}
