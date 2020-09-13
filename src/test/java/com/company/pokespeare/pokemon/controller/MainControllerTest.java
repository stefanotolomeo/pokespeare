package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.config.Constants;
import com.company.pokespeare.testconfig.BaseWebTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class MainControllerTest extends BaseWebTest {

	// No logic into MainController (it simply returns a String). No needs to be mocked

	@Inject
	private MockMvc mockMvc;

	@Test
	void getWelcome_Test() throws Exception {
		//@formatter:off
		MvcResult result = mockMvc.perform(get(Constants.BASE_URL))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		//@formatter:on

		String content = result.getResponse().getContentAsString();

		Assertions.assertEquals(Constants.WELCOME_MSG, content);

	}

}
