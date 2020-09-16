package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.config.Constants;
import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.testconfig.BaseWebTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class PokemonControllerTest extends BaseWebTest {

	// This Controller contains some logics and needs to be mocked
	@Inject
	private PokemonController pokemonController;

	@Inject
	private MockMvc mockMvc;

	@Test
	void getShakespearizedPokemonByName_Test() throws Exception {

		String dittoName = "ditto";
		String dittoPayload = "Hello " + dittoName;

		BaseHttpResponse dittoResponse = new BaseHttpResponse(200, dittoPayload);
		Mockito.when(pokemonController.getPokemonByName(dittoName)).thenReturn(dittoResponse);

		String dittoUrl = Constants.POKEMON_URL + dittoName;

		//@formatter:off
		MvcResult result = mockMvc.perform(get(dittoUrl))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		//@formatter:on

		String content = result.getResponse().getContentAsString();
		String expectedContent = "{\"statusCode\":200,\"payload\":\"Hello ditto\"}";
		Assertions.assertEquals(expectedContent, content);

	}

}